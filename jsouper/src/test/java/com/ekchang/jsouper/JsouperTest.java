package com.ekchang.jsouper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsouperTest {
  static final String MOVIE_HTML = "<div class=\"movie\">"
      + "<a class=\"title\" title=\"Deadpool\"/>"
      + "<div class=\"description\">Sample description</div>"
      + "<a class=\"rating\">4</a>"
      + "</div>";

  final Document document = Jsoup.parse(MOVIE_HTML);

  @Test
  public void customAdapter() throws Exception {
    Jsouper jsouper = new Jsouper.Builder()
        .add(Movie.class, new MovieAdapter())
        .build();

    ElementAdapter<Movie> elementAdapter = jsouper.adapter(Movie.class);
    Movie movie = elementAdapter.fromElement(document);

    assertThat(movie).isEqualTo(new Movie("Deadpool", "Sample description", 4));
  }

  @Test
  public void registerAdapterWithAnnotation() throws Exception {
    Jsouper jsouper = new Jsouper.Builder().build();
    ElementAdapter<Movie> elementAdapter = jsouper.adapter(AnnotatedMovie.class);
    Movie movie = elementAdapter.fromElement(document);
    assertThat(movie).isEqualTo(new Movie("Deadpool", "Sample description", 4));
  }

  static class Movie {
    String title;
    String detail;
    int rating;

    public Movie(String title, String detail, int rating) {
      this.title = title;
      this.detail = detail;
      this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Movie movie = (Movie) o;

      if (rating != movie.rating) return false;
      if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
      return detail != null ? detail.equals(movie.detail) : movie.detail == null;
    }

    @Override
    public int hashCode() {
      int result = title != null ? title.hashCode() : 0;
      result = 31 * result + (detail != null ? detail.hashCode() : 0);
      result = 31 * result + rating;
      return result;
    }
  }

  @SoupAdapter(MovieAdapter.class)
  static class AnnotatedMovie extends Movie {
    public AnnotatedMovie(String title, String detail, int rating) {
      super(title, detail, rating);
    }
  }

  static class MovieAdapter extends ElementAdapter<Movie> {
    @Override
    public Movie fromElement(Element element) throws IOException {
      String title = element.select("a.title").first().attr("title");
      String detail = element.select("div.description").first().text();
      int rating = Integer.parseInt(element.select("a.rating").first().text());
      return new Movie(title, detail, rating);
    }

    @Override
    public String query() {
      return "div.movie";
    }
  }
}