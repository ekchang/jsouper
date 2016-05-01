package com.ekc.jsouper.sample;

import com.ekc.jsouper.ElementAdapter;
import com.ekc.jsouper.Jsouper;
import com.ekc.jsouper.Types;
import com.ekc.jsouper.adapter.CoverAdapter;
import com.ekc.jsouper.adapter.DetailAdapter;
import com.ekc.jsouper.adapter.RatingAdapter;
import com.ekc.jsouper.models.Cover;
import com.ekc.jsouper.models.Detail;
import com.ekc.jsouper.models.Movie;
import com.ekc.jsouper.models.Rating;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PlayStore {
  public static void main(String... args) {
    try {
      Document document = Jsoup.connect("https://play.google.com/store").get();

      Jsouper jsouper = new Jsouper.Builder().add(Cover.class, new CoverAdapter())
          .add(Detail.class, new DetailAdapter())
          .add(Rating.class, new RatingAdapter())
          .build();

      // Get a list of movies
      Type listOfMovieType = Types.newParameterizedType(List.class, Movie.class);
      final ElementAdapter<List<Movie>> moviesAdapter = jsouper.adapter(listOfMovieType);
      for (Movie movie : moviesAdapter.fromElement(document)) {
        System.out.println(movie);
      }

      // Get the first movie found in document
      ElementAdapter<Movie> movieAdapter = jsouper.adapter(Movie.class);
      System.out.println(movieAdapter.fromElement(document));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
