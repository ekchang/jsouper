package com.ekchang.jsouper.sample.models;

import com.ekchang.jsouper.SoupAdapter;
import com.ekchang.jsouper.sample.adapter.DetailAdapter;

@SoupAdapter(DetailAdapter.class)
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
