package com.ekchang.jsouper.sample;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingDecoration extends RecyclerView.ItemDecoration {
  private final int spacing;
  private final int halfSpacing;

  public GridSpacingDecoration(int spacing) {
    this.spacing = spacing;
    this.halfSpacing = spacing / 2;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    int topLimit = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
    int bottomLimit = parent.getAdapter().getItemCount() - topLimit - 1;

    final int position = parent.getChildAdapterPosition(view);

    outRect.top = position < topLimit ? spacing : halfSpacing;
    outRect.left = position % 2 == 0 ? spacing : halfSpacing;
    outRect.right = position % 2 == 0 ? halfSpacing : spacing;
    outRect.bottom = position > bottomLimit ? spacing : halfSpacing;
  }
}
