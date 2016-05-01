package com.ekc.jsouper.sample.models;

public class Detail {
  public final String title;
  public final String description;

  public Detail(String title, String description) {
    this.title = title;
    this.description = description;
  }

  @Override
  public String toString() {
    return String.format("title: %s desc: %s", title, description);
  }
}
