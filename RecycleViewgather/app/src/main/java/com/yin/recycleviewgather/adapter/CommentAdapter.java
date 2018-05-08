package com.yin.recycleviewgather.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yin.recycleviewgather.R;
import com.yin.recycleviewgather.holder.MyViewHolder;

import java.util.List;

/**
 * Created by glh on 2018/5/1.
 */

public class CommentAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<String> mDatas;

    public CommentAdapter(Context context, List<String> list) {
        mContext = context;
        mDatas = list;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder holder;
        holder = new MyViewHolder(layoutInflater.inflate(R.layout.layout_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
