package com.ekc.jsouper.models;

public class Cover {
  public final String imageUrl;
  public final String targetUrl;

  public Cover(String imageUrl, String targetUrl) {
    this.imageUrl = imageUrl;
    this.targetUrl = targetUrl;
  }

  @Override
  public String toString() {
    return String.format("image: %s target: %s", imageUrl, targetUrl);
  }
}
