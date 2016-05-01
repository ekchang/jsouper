package com.ekc.jsouper.sample.api;

import com.ekc.jsouper.sample.models.Movie;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlayStoreApi {
  String BASE_URL = "https://play.google.com/";

  @GET("store")
  Call<List<Movie>> getMovies();
}
