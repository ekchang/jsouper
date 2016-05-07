package com.ekchang.jsouper.sample.models;

import com.ekchang.jsouper.SoupAdapter;
import com.ekchang.jsouper.SoupQuery;
import com.ekchang.jsouper.sample.adapter.CoverAdapter;
import com.ekchang.jsouper.sample.adapter.DetailAdapter;
import com.ekchang.jsouper.sample.adapter.RatingAdapter;

@SoupQuery("div.card.no-rationale.tall-cover.movies.small")
public class Movie {
  @SoupAdapter(CoverAdapter.class) public final Cover cover;
  @SoupAdapter(DetailAdapter.class) public final Detail detail;
  @SoupAdapter(RatingAdapter.class) public final Rating rating;

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