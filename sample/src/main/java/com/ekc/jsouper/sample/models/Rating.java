package com.ekc.jsouper.sample.models;

public class Rating {
  public final String starsDescription;
  public final double ratingPercent;
  public final String price;

  public Rating(String starsDescription, double ratingPercent, String price) {
    this.starsDescription = starsDescription;
    this.ratingPercent = ratingPercent;
    this.price = price;
  }

  @Override
  public String toString() {
    return String.format("%s\n%f\n%s\n", starsDescription, ratingPercent, price);
  }
}
