package com.ekchang.jsouper;

import com.ekchang.jsouper.annotations.SoupAdapter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
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
      + "<div class=\"review\">Ryan Reynolds is adorable!</div>"
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

  @Test
  public void composingAdapterFactory() throws Exception {
    Jsouper jsouper = new Jsouper.Builder().add(new MovieReviewAdapterFactory())
        .add(Movie.class, new MovieAdapter())
        .build();

    ElementAdapter<MovieReview> movieReviewAdapter = jsouper.adapter(MovieReview.class);
    MovieReview movieReview = movieReviewAdapter.fromElement(document);
    assertThat(movieReview.movie).isEqualTo(new Movie("Deadpool", "Sample description", 4));
    assertThat(movieReview.review).isEqualTo("Ryan Reynolds is adorable!");
  }

  @Test
  public void stringAdapter() throws Exception {
    Jsouper jsouper = new Jsouper.Builder().build();
    ElementAdapter<String> stringAdapter = jsouper.adapter(String.class);
    String text = "Lorem ipsum dolor sit amet.";
    Document document = Jsoup.parse(String.format("<div class=\"lorem\">%s</div>", text));
    assertThat(stringAdapter.fromElement(document)).isEqualTo(text);
    assertThat(stringAdapter.fromElement(document, ".lorem")).isEqualTo(text);
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

  static class MovieReview {
    Movie movie;
    String review;

    public MovieReview(Movie movie, String review) {
      this.movie = movie;
      this.review = review;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      MovieReview that = (MovieReview) o;

      if (movie != null ? !movie.equals(that.movie) : that.movie != null) return false;
      return review != null ? review.equals(that.review) : that.review == null;
    }

    @Override
    public int hashCode() {
      int result = movie != null ? movie.hashCode() : 0;
      result = 31 * result + (review != null ? review.hashCode() : 0);
      return result;
    }
  }

  static class MovieReviewAdapterFactory implements ElementAdapter.Factory {

    public static final String REVIEW_QUERY = "div.movie > div.review";

    @Override
    public ElementAdapter<?> create(Type type, Set<? extends Annotation> annotations,
        Jsouper jsouper) {
      if (!type.equals(MovieReview.class)) return null;
      final ElementAdapter<Movie> movieAdapter = jsouper.adapter(Movie.class);
      final ElementAdapter<String> reviewAdapter = jsouper.adapter(String.class);
      return new ElementAdapter<MovieReview>() {
        @Override
        public MovieReview fromElement(Element element) throws IOException {
          Movie movie = movieAdapter.fromElement(element);
          String review = reviewAdapter.fromElement(element, REVIEW_QUERY);
          return new MovieReview(movie, review);
        }

        @Override
        public String query() {
          return "div.movie";
        }
      };
    }
  }
}