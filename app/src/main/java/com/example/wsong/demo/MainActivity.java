package com.example.wsong.demo;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsong.demo.activity.BaseActivity;
import com.example.wsong.demo.activity.BroadcastActivity;
import com.example.wsong.demo.activity.ContentProviderActivity;
import com.example.wsong.demo.activity.LayoutActivity;
import com.example.wsong.demo.activity.PassValueActivity;
import com.example.wsong.demo.activity.ServiceActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity_Debug";

    /* PassValueActivity 请求码 */
    private static final int REQUEST_CODE_PASS_VALUE_ACTIVITY = 1;

    @BindView(R.id.demo_list)
    RecyclerView mDemoList;
    DemoListAdapter mAdapter;
    ArrayList<MainItemBean> mDataList;

    private interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    // 视图
    private class DemoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnItemClickListener mListener;
        TextView mTextView;

        DemoListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            mListener = listener;
            itemView.setOnClickListener(this);
            mTextView = itemView.findViewById(R.id.view_recycle_item_title);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    // 数据源
    private class DemoListAdapter extends RecyclerView.Adapter<DemoListViewHolder> {

        OnItemClickListener mListener;

        DemoListAdapter(OnItemClickListener listener) {
            super();
            mListener = listener;
        }

        @NonNull
        @Override
        public DemoListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_recycle_demo, viewGroup, false);
            return new DemoListViewHolder(view, mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull DemoListViewHolder demoListViewHolder, int i) {
            demoListViewHolder.mTextView.setText(mDataList.get(i).getTitle());
        }

        @Override
        public int getItemCount() {
            return getDataList().size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setActionBarTitle("演示项目");

        mDemoList.setLayoutManager(new LinearLayoutManager(this));
        mDemoList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new DemoListAdapter(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                getDataList().get(i).executeCallBack();
            }
        });
        mDemoList.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {

            case REQUEST_CODE_PASS_VALUE_ACTIVITY: {
                Toast.makeText(this, PassValueActivity.getReturnValue(data, ""), Toast.LENGTH_LONG).show();
                break;
            }

            default: {
                break;
            }
        }
    }

    private ArrayList<MainItemBean> getDataList() {

        if (mDataList == null) {

            mDataList = new ArrayList<>();

            mDataList.add(new MainItemBean("Activity正向传值与反向传值", new MainItemBean.MainItemBeanAction() {
                @Override
                public void onAction(MainItemBean itemBean) {
                    PassValueActivity.launch(MainActivity.this, itemBean.getTitle(),"从MainActivity传入的值", REQUEST_CODE_PASS_VALUE_ACTIVITY);
                }
            }));

            mDataList.add(new MainItemBean("后台服务播放音乐", new MainItemBean.MainItemBeanAction() {
                @Override
                public void onAction(MainItemBean itemBean) {
                    ServiceActivity.launch(MainActivity.this, itemBean.getTitle());
                }
            }));

            mDataList.add(new MainItemBean("同步、异步广播", new MainItemBean.MainItemBeanAction() {
                @Override
                public void onAction(MainItemBean itemBean) {
                    BroadcastActivity.launch(MainActivity.this, itemBean.getTitle());
                }
            }));

            mDataList.add(new MainItemBean("访问系统相册", new MainItemBean.MainItemBeanAction() {
                @Override
                public void onAction(MainItemBean itemBean) {
                    ContentProviderActivity.launch(MainActivity.this, itemBean.getTitle());
                }
            }));

            mDataList.add(new MainItemBean("线性布局", new MainItemBean.MainItemBeanAction() {
                @Override
                public void onAction(MainItemBean itemBean) {
                    LayoutActivity.launch(MainActivity.this, itemBean.getTitle(), R.layout.activity_linear_layout);
                }
            }));

            mDataList.add(new MainItemBean("表格布局", new MainItemBean.MainItemBeanAction() {
                @Override
                public void onAction(MainItemBean itemBean) {
                    LayoutActivity.launch(MainActivity.this, itemBean.getTitle(), R.layout.activity_table_layout);
                }
            }));

            mDataList.add(new MainItemBean("帧布局", new MainItemBean.MainItemBeanAction() {
                @Override
                public void onAction(MainItemBean itemBean) {
                    LayoutActivity.launch(MainActivity.this, itemBean.getTitle(), R.layout.activity_frame_layout);
                }
            }));

            mDataList.add(new MainItemBean("相对布局", new MainItemBean.MainItemBeanAction() {
                @Override
                public void onAction(MainItemBean itemBean) {
                    LayoutActivity.launch(MainActivity.this, itemBean.getTitle(), R.layout.activity_relative_layout);
                }
            }));

            mDataList.add(new MainItemBean("绝对布局", new MainItemBean.MainItemBeanAction() {
                @Override
                public void onAction(MainItemBean itemBean) {
                    LayoutActivity.launch(MainActivity.this, itemBean.getTitle(), R.layout.activity_absolute_layout);
                }
            }));
        }
        return mDataList;
    }
}
