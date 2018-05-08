package com.yin.recycleviewgather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.yin.recycleviewgather.adapter.CommentAdapter;
import com.yin.recycleviewgather.adapter.HomeAdapter;
import com.yin.recycleviewgather.decoration.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 你想要控制其显示的方式，请通过布局管理器LayoutManager
 * 你想要控制Item间的间隔（可绘制），请通过ItemDecoration
 * 你想要控制Item增删的动画，请通过ItemAnimator
 * 你想要控制点击、长按事件，请自己写（擦，这点尼玛。）
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
//    private CommentAdapter commentAdapter;
    private HomeAdapter homeAdapter;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRecycleView = findViewById(R.id.base_recycleView);
        //1.設置容器,可以是多種形式，如網格
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);//網格
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, RecyclerView.VERTICAL);//瀑布流
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        //2.創建adapter
//        commentAdapter = new CommentAdapter(this,mData);
        homeAdapter = new HomeAdapter(this,mData);
        //回调接口
        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

        });
        homeAdapter.setOnItemLongClickListener(new HomeAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                    Toast.makeText(MainActivity.this, position + " long click",
                            Toast.LENGTH_SHORT).show();
            }
        });
        //我们可以通过该方法添加分割线：  mRecyclerView.addItemDecoration()
        //要自定義請參照DividerItemDecoration

//        mRecycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            mRecycleView.addItemDecoration(new DividerGridItemDecoration(this));
        //3.加載adapter
//        mRecycleView.setAdapter(commentAdapter);
        mRecycleView.setAdapter(homeAdapter);
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 'A';i < 'z' ;i++){
            mData.add(""+(char)i);
        }
    }
}
