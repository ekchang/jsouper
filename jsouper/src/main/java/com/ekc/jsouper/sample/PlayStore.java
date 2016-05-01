package com.ekc.jsouper.sample;

import com.ekc.jsouper.ElementAdapter;
import com.ekc.jsouper.Jsouper;
import com.ekc.jsouper.adapter.CoverAdapter;
import com.ekc.jsouper.adapter.DetailAdapter;
import com.ekc.jsouper.adapter.MovieAdapter;
import com.ekc.jsouper.models.Cover;
import com.ekc.jsouper.models.Detail;
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
      //System.out.println(elements.toString());

      Jsouper jsouper = new Jsouper.Builder().add(Cover.class, new CoverAdapter())
          .add(Detail.class, new DetailAdapter())
          //.add(Movie.class, new MovieAdapter())
          .build();
      final ElementAdapter<Movie> movieAdapter = jsouper.adapter(Movie.class);
      final Element element = document.select("div.card.no-rationale.tall-cover.movies.small").first();
      Movie movie = movieAdapter.fromElement(element);
      System.out.println(movie);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
