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

package com.google.codeu.data;

/** Represents a user generated marker on a map. */
public class UserMarker {

  private final double lat;
  private final double lng;
  private final String content;

  /**
   * Constructs a map marker.
   *
   * @param lat latitude coordinate system
   * @param lng longitutde coordinate system
   * @param content text displayed on the map marker
   */
  public UserMarker(double lat, double lng, String content) {
    this.lat = lat;
    this.lng = lng;
    this.content = content;
  }

  /** Returns the latitude coordinates of the map marker. */
  public double getLat() {
    return lat;
  }

  /** Returns the longitude coordinates of the map marker. */
  public double getLng() {
    return lng;
  }

  /** Returns the content text displayed by the map marker. */
  public String getContent() {
    return content;
  }
}
