package com.ekc.jsouper.models;

public class Detail {
  private final String title;
  public final String description;
  //public final Stars stars;

  public Detail(String title, String description
      //Stars stars
  ) {
    this.title = title;
    this.description = description;
    //this.stars = stars;
  }

  @Override
  public String toString() {
    return String.format("title: %s desc: %s", title, description);
  }
}
