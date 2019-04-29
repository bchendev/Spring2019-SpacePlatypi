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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** A servlet that handles parsing JSON Starbucks data and serving in an HttpServlet Response. */
@WebServlet("/starbucks-data")
public class MapsServlet extends HttpServlet {
  JsonArray starbucksLocationsArray;

  /** Method to parse a comma separated string representing a Starbucks location. */
  public SbLocation getStarbucksLocation(String line) {
    String[] cells = line.split(",");
    String country = cells[0];
    double lat = Double.parseDouble(cells[1]);
    double lng = Double.parseDouble(cells[2]);

    return new SbLocation(country, lat, lng);
  }

  /** Method to parse a file and add it to the starbucksLocationArray. */
  private void parseFile(String fileName) {
    starbucksLocationsArray = new JsonArray();
    Gson gson = new Gson();
    String line;
    Scanner scanner = new Scanner(getServletContext().getResourceAsStream(fileName));
    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      starbucksLocationsArray.add(gson.toJsonTree(getStarbucksLocation(line)));
    }
    scanner.close();
  }

  @Override
  public void init() {
    parseFile("/WEB-INF/starbucks-data.csv");
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

    private SbLocation(String country, double lat, double lng) {
      this.country = country;
      this.lat = lat;
      this.lng = lng;
    }
  }
}
