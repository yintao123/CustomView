package com.yin.recycleviewgather.holder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.yin.recycleviewgather.R;

/**
 * Created by glh on 2018/5/1.
 * 這個類用來加載item的佈局對應adapter中的佈局文件，是一個容器
 */

public class MyViewHolder extends ViewHolder{
    public TextView textView;
    public MyViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.id_num);
    }
}
