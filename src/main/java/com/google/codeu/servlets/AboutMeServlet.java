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

    JsonObject returnObject = new JsonObject();

    if (userData == null) {
      response.setContentType("application/json");
      response.getWriter().println(returnObject.toString());
      return;
    }

    // Store about me in json.
    String aboutMe = userData.getAboutMe();
    if (aboutMe != null && !aboutMe.isEmpty()) {
      returnObject.addProperty("aboutMe", aboutMe);
    }

    // Store the location in json.
    String location = userData.getLocation();
    if (location != null && !location.isEmpty()) {
      returnObject.addProperty("location", location);
    }

    // Store the profile picture url in json.
    String profilePicUrl = userData.getImageUrl();
    if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
      returnObject.addProperty("profilePic", profilePicUrl);
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

    if (user == null) {
      user = new User(userEmail, "", "");
    }

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

    // Update the existing user
    user.setAboutMe(about);
    user.setLocation(location);

    // Store the user
    datastore.storeUser(user);

    response.sendRedirect("/user-page.html?user=" + userEmail);
  }
}
