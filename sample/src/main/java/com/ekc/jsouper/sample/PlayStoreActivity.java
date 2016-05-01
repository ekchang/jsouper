package com.ekc.jsouper.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.ekc.jsouper.ElementAdapter;
import com.ekc.jsouper.Jsouper;
import com.ekc.jsouper.R;
import com.ekc.jsouper.Types;
import com.ekc.jsouper.sample.adapter.CoverAdapter;
import com.ekc.jsouper.sample.adapter.DetailAdapter;
import com.ekc.jsouper.sample.adapter.RatingAdapter;
import com.ekc.jsouper.sample.api.PlayStoreApi;
import com.ekc.jsouper.sample.models.Cover;
import com.ekc.jsouper.sample.models.Detail;
import com.ekc.jsouper.sample.models.Movie;
import com.ekc.jsouper.sample.models.Rating;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jsoup.JsoupConverterFactory;

public class PlayStoreActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final Jsouper jsouper = new Jsouper.Builder().add(Cover.class, new CoverAdapter())
        .add(Detail.class, new DetailAdapter())
        .add(Rating.class, new RatingAdapter())
        .build();

    Retrofit retrofit = new Retrofit.Builder().baseUrl(PlayStoreApi.BASE_URL)
        .addConverterFactory(JsoupConverterFactory.create(jsouper))
        .build();

    PlayStoreApi playStore = retrofit.create(PlayStoreApi.class);

    playStore.getMovies().enqueue(new Callback<List<Movie>>() {
      @Override
      public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
        for (Movie movie : response.body()) {
          Log.d("TAG", movie.toString());
        }
      }

      @Override
      public void onFailure(Call<List<Movie>> call, Throwable t) {

      }
    });
  }
}
