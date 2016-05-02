package com.ekc.jsouper.sample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class GridItemAnimator extends RecyclerView.ItemAnimator {
  @Override
  public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder,
      @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
    return false;
  }

  @Override
  public boolean animateAppearance(@NonNull final RecyclerView.ViewHolder viewHolder,
      @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
    ViewCompat.setAlpha(viewHolder.itemView, 0);
    ViewCompat.setTranslationY(viewHolder.itemView, viewHolder.itemView.getHeight());

    ViewCompat.animate(viewHolder.itemView)
        .alpha(1)
        .translationY(0)
        .setDuration(300)
        .setStartDelay((viewHolder.getAdapterPosition() / 2 + 1) * 100)
        .setInterpolator(new DecelerateInterpolator())
        .setListener(new ViewPropertyAnimatorListener() {
          @Override
          public void onAnimationStart(View view) { }

          @Override
          public void onAnimationEnd(View view) {
            dispatchAnimationFinished(viewHolder);
          }

          @Override
          public void onAnimationCancel(View view) {
            dispatchAnimationFinished(viewHolder);
          }
        })
        .start();
    return false;
  }

  @Override
  public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder,
      @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
    return false;
  }

  @Override
  public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
      @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo,
      @NonNull ItemHolderInfo postLayoutInfo) {
    return false;
  }

  @Override
  public void runPendingAnimations() {

  }

  @Override
  public void endAnimation(RecyclerView.ViewHolder item) {

  }

  @Override
  public void endAnimations() {

  }

  @Override
  public boolean isRunning() {
    return false;
  }
}
