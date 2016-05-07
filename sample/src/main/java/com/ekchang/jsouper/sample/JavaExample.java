package com.ekchang.jsouper.sample;

import com.ekchang.jsouper.ElementAdapter;
import com.ekchang.jsouper.Jsouper;
import com.ekchang.jsouper.sample.models.Movie;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import org.jsoup.Jsoup;

public class JavaExample {
  public static void main(String... args) {
    final Jsouper jsouper = new Jsouper.Builder().build();

    Type listOfMoviesType = com.ekchang.jsouper.Types.newParameterizedType(List.class, Movie.class);
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
