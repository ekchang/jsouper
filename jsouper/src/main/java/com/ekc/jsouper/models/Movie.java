package com.ekc.jsouper.models;

public class Movie {
  public final Cover cover;
  //public final Details details;

  public Movie(Cover cover) {
    this.cover = cover;
    //this.details = details;
  }

  @Override
  public String toString() {
    return cover.toString();
  }
}
