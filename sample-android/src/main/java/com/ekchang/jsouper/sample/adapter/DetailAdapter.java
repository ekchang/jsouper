package com.ekchang.jsouper.sample.adapter;

import com.ekchang.jsouper.sample.models.Detail;
import java.io.IOException;
import org.jsoup.nodes.Element;

public class DetailAdapter extends com.ekchang.jsouper.ElementAdapter<Detail> {
  @Override
  public String query() {
    return "div.details";
  }

  @Override
  public Detail fromElement(Element element) throws IOException {
    String title = element.select("a.title").first().attr("title");
    String description = element.select("div.description").first().text();
    return new Detail(title, description);
  }
}
