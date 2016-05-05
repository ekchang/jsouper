package com.ekchang.jsouper.sample.adapter;

import com.ekchang.jsouper.ElementAdapter;
import com.ekchang.jsouper.sample.models.Cover;
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
        element.select("div.cover-inner-align").select("img").first().attr("data-cover-large");
    final String targetUrl = element.select("a.card-click-target").attr("href");
    return new Cover(imageUrl, targetUrl);
  }
}
