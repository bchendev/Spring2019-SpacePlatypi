package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** A servlet that handles the input of a search query and the redirection to the results page. */
@WebServlet("/search")
public class SearchBarServlet extends HttpServlet {
  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // first, take the request and compare it to users stored in datastore
    // whitelist is important to make sure nothing can be written to break out code
    // then, whichever users match the input, send them to /search-results.html
    // i think it would be a good idea to use a loop here because we want to look at multiple users
    // in the long run
    // how are users stored in datastore? what would be the best search algorithim

    String user = request.getParameter("user");
    String query = request.getParameter("input");
    User userData = datastore.getUser(user);
    response.setContentType("text/html");

    if (query == null || query.isEmpty()) {
      // No-op if the request is invalid.
      return;
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // gonna redirect the user the the results page
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    // sendRedirect to search-results.html

    response.sendRedirect("/search-results.html");
  }
}
