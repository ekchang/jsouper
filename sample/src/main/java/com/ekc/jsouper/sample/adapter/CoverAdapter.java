package com.ekc.jsouper.sample.adapter;

import com.ekc.jsouper.ElementAdapter;
import com.ekc.jsouper.sample.models.Cover;
import java.io.IOException;
import org.jsoup.nodes.Element;

public class CoverAdapter extends ElementAdapter<Cover> {
  @Override
  public String query() {
    return "cover";
  }

  @Override
  public Cover fromElement(Element element) throws IOException {
    final String imageUrl =
        element.select("div.cover-inner-align").select("img").first().attr("src");
    final String targetUrl = element.select("a.card-click-target").attr("href");
    return new Cover(imageUrl, targetUrl);
  }
}
