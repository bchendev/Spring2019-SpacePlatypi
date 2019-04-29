/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
      // If the user doesn't exist, then return.
      response.sendRedirect("/error404");
      return;
    }

    // Allows only basic text editing, image uploading, and linking functions
    Whitelist whitelist = Whitelist.basicWithImages().addTags("a").addAttributes("a", "href");

    User.Builder userBuilder = User.Builder.fromUser(user);

    // About me section
    String aboutMe = request.getParameter("about-me");
    if (aboutMe != null && !aboutMe.isEmpty()) {
      aboutMe = Jsoup.clean(aboutMe, whitelist);
      userBuilder.setAboutMe(aboutMe);
    }

    // Location
    String location = request.getParameter("location");
    if (location != null && !location.isEmpty()) {
      location = Jsoup.clean(location, whitelist);
      userBuilder.setLocation(location);
    }

    datastore.storeUser(userBuilder.build());
    response.sendRedirect("/user-page.html?user=" + userEmail);
  }
}
