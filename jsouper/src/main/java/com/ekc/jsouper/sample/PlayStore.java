package com.ekc.jsouper.sample;

import com.ekc.jsouper.Jsouper;
import com.ekc.jsouper.adapter.MovieAdapter;
import com.ekc.jsouper.models.Movie;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PlayStore {
  public static void main(String... args) {
    try {
      Document document = Jsoup.connect("https://play.google.com/store").get();
      final Elements elements = document.select("div.card.no-rationale.tall-cover.movies.small");
      //System.out.println(elements.toString());

      //Jsouper jsouper = new Jsouper.Builder().add(Movie.class, new MovieAdapter()).build();
      final MovieAdapter movieAdapter = new MovieAdapter();
      final Element element = elements.select(movieAdapter.query()).first();
      Movie movie = movieAdapter.fromElement(element);
      System.out.println(movie);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
