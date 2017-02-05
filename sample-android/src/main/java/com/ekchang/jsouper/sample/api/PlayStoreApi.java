package com.ekchang.jsouper.sample.api;

import com.ekchang.jsouper.sample.models.Movie;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlayStoreApi {
  String BASE_URL = "https://play.google.com/store/";

  @GET("movies/collection/promotion_4000a71_newrelease_mo_eg_ac")
  Call<List<Movie>> getMovies();
}
