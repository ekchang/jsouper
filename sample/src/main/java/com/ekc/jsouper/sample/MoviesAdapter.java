package com.ekc.jsouper.sample;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ekc.jsouper.R;
import com.ekc.jsouper.databinding.ItemMoviesBinding;
import com.ekc.jsouper.sample.models.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
  private List<Movie> items = new ArrayList<>();

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        (ItemMoviesBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            viewType, parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Movie movie = items.get(position);
    Picasso.with(holder.binding.getRoot().getContext())
        .load(movie.cover.imageUrl)
        .into(holder.binding.cover);

    holder.binding.title.setText(movie.detail.title);
    holder.binding.price.setText(movie.rating.price);
  }

  @Override
  public int getItemViewType(int position) {
    return R.layout.item_movies;
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public void loadData(List<Movie> movies) {
    items.clear();
    items.addAll(movies);
    notifyDataSetChanged();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    public final ItemMoviesBinding binding;

    public ViewHolder(ItemMoviesBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
