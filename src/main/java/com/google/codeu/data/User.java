package com.google.codeu.data;

/** Keeps track of user information. */
public class User {

  private String email;
  private String aboutMe;
  private String location;
  private String imageUrl;

  /** Represents a user entity's constructor. */
  public User(String email, String aboutMe, String location) {
    this.email = email;
    this.aboutMe = aboutMe;
    this.location = location;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLocation() {
    return location;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

}

