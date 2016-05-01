package com.ekc.jsouper.models;

public class Movie {
  public final Cover cover;
  public final Detail detail;

  public Movie(Cover cover, Detail detail) {
    this.cover = cover;
    this.detail = detail;
  }

  @Override
  public String toString() {
    return String.format("%s %s", cover, detail);
  }
}
