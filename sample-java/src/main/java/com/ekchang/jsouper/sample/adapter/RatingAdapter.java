package com.ekchang.jsouper.sample.adapter;

import com.ekchang.jsouper.ElementAdapter;
import com.ekchang.jsouper.sample.models.Rating;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;

public class RatingAdapter extends ElementAdapter<Rating> {
  static final Pattern RATING_PATTERN = Pattern.compile("(\\d*)\\.(\\d*)");

  @Override
  public String query() {
    return "div.reason-set";
  }

  @Override
  public Rating fromElement(Element element) throws IOException {
    String starsDescription = element.select("div.tiny-star.star-rating-non-editable-container")
        .first()
        .attr("aria-label");

    double ratingPercent = 0d;
    final Matcher matcher =
        RATING_PATTERN.matcher(element.select("div.current-rating").attr("style"));
    if (matcher.find()) {
      ratingPercent = Double.valueOf(matcher.group());
    }

    String price = element.select("span.display-price").first().text();

    return new Rating(starsDescription, ratingPercent, price);
  }
}
