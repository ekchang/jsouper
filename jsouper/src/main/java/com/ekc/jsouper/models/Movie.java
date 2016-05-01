package com.ekc.jsouper.models;

import com.ekc.jsouper.ElementQuery;

@ElementQuery(query = "div.card.no-rationale.tall-cover.movies.small")
public class Movie {
  public final Cover cover;
  public final Detail detail;
  public final Rating rating;

  public Movie(Cover cover, Detail detail, Rating rating) {
    this.cover = cover;
    this.detail = detail;
    this.rating = rating;
  }

  @Override
  public String toString() {
    return String.format("%s\n%s\n%s", cover, detail, rating);
  }
}
