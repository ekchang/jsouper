package com.ekc.jsouper.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;
import com.ekc.jsouper.Jsouper;
import com.ekc.jsouper.R;
import com.ekc.jsouper.databinding.ActivityMainBinding;
import com.ekc.jsouper.sample.adapter.CoverAdapter;
import com.ekc.jsouper.sample.adapter.DetailAdapter;
import com.ekc.jsouper.sample.adapter.RatingAdapter;
import com.ekc.jsouper.sample.api.PlayStoreApi;
import com.ekc.jsouper.sample.models.Cover;
import com.ekc.jsouper.sample.models.Detail;
import com.ekc.jsouper.sample.models.Movie;
import com.ekc.jsouper.sample.models.Rating;
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

    final Jsouper jsouper = new Jsouper.Builder().add(Cover.class, new CoverAdapter())
        .add(Detail.class, new DetailAdapter())
        .add(Rating.class, new RatingAdapter())
        .build();

    Retrofit retrofit = new Retrofit.Builder().baseUrl(PlayStoreApi.BASE_URL)
        .addConverterFactory(JsoupConverterFactory.create(jsouper))
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
