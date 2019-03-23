package com.google.codeu.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Returns Starbucks Locations a JSON array, e.g. [{"lat": 38.4404675, "lng": -122.7144313}]
 */

@WebServlet("/starbucks-data")
public class MapsServlet extends HttpServlet {
  JsonArray starbucksLocationsArray;
  
  public SbLocation  parseFile(String line){
      String[] cells = line.split(",");
      String country = cells[0];
      double lat = Double.parseDouble(cells[1]);
      double lng = Double.parseDouble(cells[2]);

      SbLocation loc = new SbLocation(country, lat, lng);
      return loc;
  }

  @Override
  public void init() {
    starbucksLocationsArray = new JsonArray();
    new SbLocation();
    Gson gson = new Gson();
    String line = "";
    Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/starbucks-data.csv"));
      while (scanner.hasNextLine()) {
        line = scanner.nextLine();
        starbucksLocationsArray.add(gson.toJsonTree(parseFile(line)));
    }
    scanner.close();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.getOutputStream().println(starbucksLocationsArray.toString());
  }

  private static class SbLocation {
    String country;
    double lat;
    double lng;
    
   private SbLocation(){
     country = "";
     lat = 0;
     lng = 0;
    }
    
    private SbLocation(String country, double lat, double lng) {
      this.country = country;
      this.lat = lat;
      this.lng = lng;
    }
  }
}