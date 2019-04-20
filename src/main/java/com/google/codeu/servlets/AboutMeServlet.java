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

    response.getOutputStream().println(userData.getAboutMe());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    // Allows only basic text editing, image uploading, and linking functions
    Whitelist whitelist = Whitelist.basicWithImages().addTags("a").addAttributes("a", "href");

    String aboutMe = Jsoup.clean(request.getParameter("about-me"), whitelist);
    String userEmail = userService.getCurrentUser().getEmail();
    User user = new User(userEmail, aboutMe, /* location= */ null);
    datastore.storeUser(user);

    response.sendRedirect("/user-page.html?user=" + userEmail);
  }
}
