package com.ekchang.jsouper.sample.models;

import com.ekchang.jsouper.SoupAdapter;
import com.ekchang.jsouper.sample.adapter.CoverAdapter;

@SoupAdapter(CoverAdapter.class)
public class Cover {
  public final String imageUrl;
  public final String targetUrl;

  public Cover(String imageUrl, String targetUrl) {
    this.imageUrl = imageUrl;
    this.targetUrl = targetUrl;
  }

  @Override
  public String toString() {
    return String.format("cover image: %s target: %s", imageUrl, targetUrl);
  }
}
