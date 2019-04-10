package com.google.codeu.data;

/** Represents a user generated marker on a map. */
public class UserMarker {

  private final double lat;
  private final double lng;
  private final String content;

 /**
 * Constructs a map marker.
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