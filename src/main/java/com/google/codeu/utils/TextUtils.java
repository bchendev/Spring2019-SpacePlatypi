/*
package com.google.codeu.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

// import org.apache.commons.text.StringEscapeUtils;

public class TextUtils extends HttpServlet {
  public void filterDangerousText(HttpServletRequest request) throws ServletException, IOException {
    // String cleanedText = Jsoup.clean(request.getParameter("text"), Whitelist.basic());
    // response.sendRedirect(request.getRequestURI());
    // return cleanedText;
  }

  public void markdownToHtml(HttpServletRequest request) throws ServletException, IOException {
    // String enteredText = request.getParameter("text");

    // make sure user didn't enter HTML along with our markup
    // String escapedText = StringEscapeUtils.escapeHtml4(enteredText);

  }
  /*
    /**
     * Responds with a JSON representation of {@link Message} data 
     * for a specific user. Responds with
     * an empty array if the user is not provided.

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

      response.setContentType("application/json");

      String user = request.getParameter("user");

      if (user == null || user.equals("")) {
        // Request is invalid, return empty array
        response.getWriter().println("[]");
        return;
      }

      List<Message> messages = datastore.getMessages(user);
      Gson gson = new Gson();
      String json = gson.toJson(messages);

      response.getWriter().println(json);
    }

    /** Stores a new {@link Message}.
    @Override
    public void doPost(HttpServletRequest request,
    HttpServletResponse response) throws IOException {

      UserService userService = UserServiceFactory.getUserService();
      if (!userService.isUserLoggedIn()) {
        response.sendRedirect("/index.html");
        return;
      }

      String user = userService.getCurrentUser().getEmail();
      String text = Jsoup.clean(request.getParameter("text"), Whitelist.none());
      String recipient = request.getParameter("recipient");

      Message message = new Message(user, text, recipient);
      datastore.storeMessage(message);

      response.sendRedirect("/user-page.html?user=" + recipient);
    }
}*/
