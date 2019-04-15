package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.gson.Gson;
import com.google.codeu.data.User;
import java.io.IOException;
import java.util.List;
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

  /**
   * Responds with a JSON representation of {@link User} data for a search query. Responds with
   * an empty array if the no user found.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("applications/json");
    String searchQuery = request.getParameter("query");

    List<User> allUsers = datastore.getUsers(searchQuery);
    List<User> matchingUsers = new ArrayList<User>();

    for(int i =0; i <= allUsers.size(); i++){
      if (allUsers.get(i).equals(searchQuery)){
        matchingUsers.add(allUsers.get(i));
      }
    }
    Gson gson = new Gson();
    String json = gson.toJson(matchingUsers);

    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }
    String query = request.getParameter("query");
    response.sendRedirect("/search-results.html?query=" + query);
  }
}
