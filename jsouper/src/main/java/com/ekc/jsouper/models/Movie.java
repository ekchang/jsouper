package com.ekc.jsouper.models;

import com.ekc.jsouper.ElementQuery;

@ElementQuery(query = "div.card.no-rationale.tall-cover.movies.small")
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
