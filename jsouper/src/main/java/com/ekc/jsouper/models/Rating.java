package com.ekc.jsouper.models;

public class Rating {
  public final String starsDescription;
  public final String price;

  public Rating(String starsDescription, String price) {
    this.starsDescription = starsDescription;
    this.price = price;
  }

  @Override
  public String toString() {
    return String.format("%s\n%s", starsDescription, price);
  }
}
