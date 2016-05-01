package com.ekc.jsouper.models;

public class Details {
  public final String targetUrl;
  public final String description;
  public final Stars stars;

  public Details(String targetUrl, String description, Stars stars) {
    this.targetUrl = targetUrl;
    this.description = description;
    this.stars = stars;
  }
}
