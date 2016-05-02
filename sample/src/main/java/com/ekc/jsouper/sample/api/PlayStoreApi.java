package com.ekc.jsouper.sample.api;

import com.ekc.jsouper.sample.models.Movie;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlayStoreApi {
  String BASE_URL = "https://play.google.com/store/";

  @GET("movies/collection/promotion_400079b_most_popular_movies")
  Call<List<Movie>> getMovies();
}
