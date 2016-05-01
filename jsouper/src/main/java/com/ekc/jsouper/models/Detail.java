package com.ekc.jsouper.models;

public class Detail {
  public final String targetUrl;
  private final String title;
  public final String description;
  //public final Stars stars;

  public Detail(String targetUrl, String title, String description
      //Stars stars
  ) {
    this.targetUrl = targetUrl;
    this.title = title;
    this.description = description;
    //this.stars = stars;
  }

  @Override
  public String toString() {
    return String.format("target: %s title: %s desc: %s", targetUrl, title, description);
  }
}
