package com.ekc.jsouper.sample;

import com.ekc.jsouper.ElementAdapter;
import com.ekc.jsouper.Jsouper;
import com.ekc.jsouper.Types;
import com.ekc.jsouper.sample.adapter.CoverAdapter;
import com.ekc.jsouper.sample.adapter.DetailAdapter;
import com.ekc.jsouper.sample.adapter.RatingAdapter;
import com.ekc.jsouper.sample.models.Cover;
import com.ekc.jsouper.sample.models.Detail;
import com.ekc.jsouper.sample.models.Movie;
import com.ekc.jsouper.sample.models.Rating;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import org.jsoup.Jsoup;

public class JavaExample {
  public static void main(String... args) {
    final Jsouper jsouper = new Jsouper.Builder().add(Cover.class, new CoverAdapter())
        .add(Detail.class, new DetailAdapter())
        .add(Rating.class, new RatingAdapter())
        .build();

    Type listOfMoviesType = Types.newParameterizedType(List.class, Movie.class);
    ElementAdapter<List<Movie>> moviesAdapter = jsouper.adapter(listOfMoviesType);

    try {
      List<Movie> movies =
          moviesAdapter.fromElement(Jsoup.connect("https://play.google.com/store").get());
      for (Movie movie : movies) {
        System.out.println(movie);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
