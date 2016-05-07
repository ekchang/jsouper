package com.ekchang.jsouper.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;
import com.ekchang.jsouper.R;
import com.ekchang.jsouper.databinding.ActivityMainBinding;
import com.ekchang.jsouper.sample.api.PlayStoreApi;
import com.ekchang.jsouper.sample.models.Movie;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jsoup.JsoupConverterFactory;

public class PlayStoreActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
  private MoviesAdapter adapter;
  private PlayStoreApi playStoreApi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    setSupportActionBar(binding.toolbar);

    adapter = new MoviesAdapter();
    binding.listMovies.setLayoutManager(new GridLayoutManager(this, 2));
    binding.listMovies.addItemDecoration(new GridSpacingDecoration(
        getResources().getDimensionPixelSize(R.dimen.movie_card_spacing)));
    binding.listMovies.setAdapter(adapter);
    binding.listMovies.setItemAnimator(new GridItemAnimator());

    Retrofit retrofit = new Retrofit.Builder().baseUrl(PlayStoreApi.BASE_URL)
        .addConverterFactory(JsoupConverterFactory.create())
        .build();

    playStoreApi = retrofit.create(PlayStoreApi.class);
    loadMovies();
  }

  public void loadMovies() {
    showLoading();
    playStoreApi.getMovies().enqueue(new Callback<List<Movie>>() {
      @Override
      public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
        hideLoading();
        adapter.loadData(response.body());
      }

      @Override
      public void onFailure(Call<List<Movie>> call, Throwable t) {
        hideLoading();
        Toast.makeText(PlayStoreActivity.this, "Error loading movies from Play Store",
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  public void showLoading() {
    binding.loading.show();
  }

  public void hideLoading() {
    binding.loading.hide();
  }
}
