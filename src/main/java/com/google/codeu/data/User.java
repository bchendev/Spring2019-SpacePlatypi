package com.google.codeu.data;

/** Keeps track of user information. */
public class User {

  private final String email;
  private final String aboutMe;
  private final String location;

  /** Represents a user entity's constructor. */
  public User(String email, String aboutMe, String location) {
    this.email = email;
    this.aboutMe = aboutMe;
    this.location = location;
  }

  public String getEmail() {
    return email;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public String getLocation() {
    return location;
  }
}
