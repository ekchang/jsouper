package com.ekc.jsouper.adapter;

import com.ekc.jsouper.ElementAdapter;
import com.ekc.jsouper.models.Rating;
import java.io.IOException;
import org.jsoup.nodes.Element;

public class RatingAdapter extends ElementAdapter<Rating> {
  @Override
  public String query() {
    return "div.reason-set";
  }

  @Override
  public Rating fromElement(Element element) throws IOException {
    String starsDescription = element.select("div.tiny-star.star-rating-non-editable-container")
        .first()
        .attr("aria-label");
    String price = element.select("span.display-price").text();
    return new Rating(starsDescription, price);
  }
}
