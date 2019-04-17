package com.google.codeu.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Provides access to a URL that allows a user to upload an image to Blobstore. */
@WebServlet("/image-upload-url")
public class ImageUploadUrlServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Gets the file upload URL
    // BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    // UserService userService = UserServiceFactory.getUserService();
    // String userEmail = userService.getCurrentUser().getEmail();
    // String uploadUrl = blobstoreService.createUploadUrl("/user-page.html?user=" + userEmail);

    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("image");
    // response.setContentType("text/html");
    // response.getOutputStream().println(uploadUrl);
    // response.sendRedirect("/user-page.html?user=" + userEmail);

  }
}
