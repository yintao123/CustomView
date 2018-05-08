package com.yin.recycleviewgather.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * 針對網格佈局和瀑布流
 * 瀑布流還有一點問題
 * Created by glh on 2018/5/2.
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;

    public DividerGridItemDecoration(Context context){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this "
                    + "DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
       drawHorizontal(c,parent);
       drawVertical(c,parent);
    }

    /**
     * 獲取recycleView的列數
     * @param parent
     * @return
     */
   private int getSpanCount(RecyclerView parent){
       int spanCount = -1;
       RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
       if (layoutManager instanceof GridLayoutManager){
           spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
       }else if (layoutManager instanceof StaggeredGridLayoutManager){
           spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
       }
        return spanCount;
   }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
       //get count of item
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount;i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final  View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private boolean isLastColum(RecyclerView parent,int pos,int spanCount,int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            if ((pos+1) % spanCount == 0){//如果是最後一列，則不需要繪製右邊
                return true;
            }else if (layoutManager instanceof StaggeredGridLayoutManager){
                int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
                if (orientation == StaggeredGridLayoutManager.VERTICAL){
                    if ((pos + 1)%spanCount == 0){
                        return true;
                    }
                }else {
                    childCount = childCount - childCount % spanCount;
                    if (pos >= childCount)
                        return true;
                }
            }
        }

       return false;
    }

    private boolean isLastRaw(RecyclerView parent,int pos,int spanCount,int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)//如果是最後一行，則不需要繪製底部
                return true;
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            //StaggeredGridLayoutManager 且縱向滾動
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                childCount = childCount - childCount % spanCount;
                //如果是最後一行，則不需要繪製底部
                if (pos >= childCount)
                    return true;
            }else {
                //StaggeredGridLayoutManager 且橫向滾動
                if ((pos + 1) % spanCount == 0){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int position = parent.getChildAdapterPosition(view);

        if (isLastRaw(parent,position,spanCount,childCount)){//如果是最後一行，則不需要繪製底部
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }else if (isLastColum(parent,position,spanCount,childCount)){//如果是最後一列，則不需要繪製右邊
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else {
            outRect.set(0,0,mDivider.getIntrinsicWidth(),mDivider.getIntrinsicHeight());
        }
    }
}
