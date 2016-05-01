package com.ekc.jsouper.adapter;

import com.ekc.jsouper.ElementAdapter;
import com.ekc.jsouper.models.Cover;
import com.ekc.jsouper.models.Movie;
import java.io.IOException;
import org.jsoup.nodes.Element;

public class MovieAdapter extends ElementAdapter<Movie> {
  @Override
  public String query() {
    return "div.card.no-rationale.tall-cover.movies.small";
  }

  @Override
  public Movie fromElement(Element element) throws IOException {
    final String imageUrl =
        element.select("div.cover-inner-align").select("img").first().attr("src");
    final String targetUrl = element.select("a.card-click-target").attr("href");
    Cover cover = new Cover(imageUrl, targetUrl);
    Movie movie = new Movie(cover);
    return movie;
  }
}
