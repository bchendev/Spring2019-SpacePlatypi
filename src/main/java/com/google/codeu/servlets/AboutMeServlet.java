package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** A servlet that handles fetching and saving user data to display the About Me page. */
@WebServlet("/about")
public class AboutMeServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    String user = request.getParameter("user");

    if (user == null || user.isEmpty()) {
      // No-op if the request is invalid.
      return;
    }

    User userData = datastore.getUser(user);

    if (userData == null || userData.getAboutMe() == null) {
      return;
    }

    JsonObject returnObject = new JsonObject();

    if(!userData.getAboutMe().isEmpty()) {
      returnObject.addProperty("aboutMe", userData.getAboutMe());
    }
    if(!userData.getLocation().isEmpty()) {
      returnObject.addProperty("location", userData.getLocation());
    }

    response.setContentType("application/json");
    response.getWriter().println(returnObject.toString());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    String userEmail = userService.getCurrentUser().getEmail();
    User user = datastore.getUser(userEmail);

    // Allows only basic text editing, image uploading, and linking functions
    Whitelist whitelist = Whitelist.basicWithImages().addTags("a").addAttributes("a", "href");

    // About me section
    String about = request.getParameter("about-me");
    if (about == null || about.isEmpty()) {
      about = user.getAboutMe();
    } else {
      about = Jsoup.clean(about, whitelist);
    }

    // Location section
    String location = request.getParameter("location");
    if (location == null || location.isEmpty()) {
      location = user.getLocation();
    } else {
      location = Jsoup.clean(location, whitelist);
    }
    
    System.out.println("Storing user: " + userEmail + ", " + about + ", " + location);
    User updatedUser = new User(userEmail, about, location);
    datastore.storeUser(updatedUser);

    response.sendRedirect("/user-page.html?user=" + userEmail);
  }
}
