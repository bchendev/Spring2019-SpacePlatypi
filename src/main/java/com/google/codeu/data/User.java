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

/** Keeps track of user information. */
public class User {
  private String email;
  private String aboutMe;
  private String location;
  private String imageUrl;

  /** An immutable user object that corresponds with Datastore. */
  private User(String email) {
    this.email = email;
  }

  /** Returns the user's email. */
  public String getEmail() {
    return email;
  }

  /** Returns the user's about me text. */
  public String getAboutMe() {
    return aboutMe;
  }

  /** Returns the user's location. */
  public String getLocation() {
    return location;
  }

  /** Returns the user's image url. */
  public String getImageUrl() {
    return imageUrl;
  }

  /** Builder pattern used to make sure User is immutable. */
  public static class Builder {
    public String email;
    public String aboutMe;
    public String location;
    public String imageUrl;

    /** Private constructor. */
    private Builder() {}

    public static Builder withEmail(String userEmail) {
      Builder builder = new Builder();
      builder.email = userEmail;
      return builder;
    }

    /** Clones a user into a builder for modifying. */
    public static Builder fromUser(User user) {
      return withEmail(user.getEmail())
          .setAboutMe(user.getAboutMe())
          .setLocation(user.getLocation())
          .setImageUrl(user.getImageUrl());
    }

    /** Sets the about me field in the builder. */
    public Builder setAboutMe(String aboutMe) {
      this.aboutMe = aboutMe;
      return this;
    }

    /** Sets the location field in the builder. */
    public Builder setLocation(String location) {
      this.location = location;
      return this;
    }

    /** Sets the image url in the builder. */
    public Builder setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    /** Constructs a user given. */
    public User build() {
      User user = new User(this.email);
      user.aboutMe = this.aboutMe;
      user.location = this.location;
      user.imageUrl = this.imageUrl;
      return user;
    }
  }
}
