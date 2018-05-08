package com.yin.recycleviewgather.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yin.recycleviewgather.R;

import java.util.List;

/**
 * Created by glh on 2018/5/7.
 *  RecyclerView没有给我们提供ClickListener 和LongClickListener
 *  这个需要我们自己添加，我们可以通过mRecyclerView.addOnItemTouchListener去监听然后去判断手势，
 *  当然我们页可以通过adapter中子提供回掉，这里我们选择后者
 *
 *  adapter中自己定义了个接口，然后在onBindViewHolder中去为holder.itemView去设置相应
 * 的监听最后回调我们设置的监听。
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<String> mDatas;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private Context mContext;
    private LayoutInflater layoutInflater;

    //设置点击事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
    //设置长按事件
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public HomeAdapter(Context context ,List<String> list){
        mContext = context;
        mDatas = list;
        layoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(layoutInflater.inflate(R.layout.layout_item,parent,false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,  int position) {
        holder.tv.setText(mDatas.get(position));

        //如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int pos = holder.getAdapterPosition();
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(v,pos);
                }
            });

        }
        if (mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(v, pos);
                    //返回true表示消费了事件，再次触发onClickListener
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);

    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view , int position);
    }



    class  MyViewHolder extends ViewHolder {

        public TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.id_num);
        }
    }


}
