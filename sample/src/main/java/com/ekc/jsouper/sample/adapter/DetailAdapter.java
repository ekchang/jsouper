package com.ekc.jsouper.sample.adapter;

import com.ekc.jsouper.ElementAdapter;
import com.ekc.jsouper.sample.models.Detail;
import java.io.IOException;
import org.jsoup.nodes.Element;

public class DetailAdapter extends ElementAdapter<Detail> {
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
