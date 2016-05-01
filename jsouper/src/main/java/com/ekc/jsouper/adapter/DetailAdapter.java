package com.ekc.jsouper.adapter;

import com.ekc.jsouper.ElementAdapter;
import com.ekc.jsouper.models.Detail;
import java.io.IOException;
import org.jsoup.nodes.Element;

public class DetailAdapter extends ElementAdapter<Detail> {
  @Override
  public String query() {
    return "div.details";
  }

  @Override
  public Detail fromElement(Element element) throws IOException {
    String detailsTargetUrl = element.select("a.card-click-target").first().attr("href");
    String title = element.select("a.title").first().attr("title");
    String description = element.select("div.description").text();
    return new Detail(detailsTargetUrl, title, description);
  }
}
