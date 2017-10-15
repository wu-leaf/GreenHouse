package cn.edu.gdou.www.greenhouse.ui.fragments.infragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gdou.httpwww.greenhouse.R;
import cn.edu.gdou.www.greenhouse.data.BigItem;
import cn.edu.gdou.www.greenhouse.data.NewsItem;
import cn.edu.gdou.www.greenhouse.http.Api;
import cn.edu.gdou.www.greenhouse.ui.activity.ContentActivity;
import cn.edu.gdou.www.greenhouse.ui.adapter.RecyclerviewAdapter;
import cn.edu.gdou.www.greenhouse.utils.RecycleViewDivider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Veyron on 2017/1/29.
 * Function：业界资讯栏目
 */
public class WeatherFragment extends Fragment {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private boolean isLoading = false;//判断是否加载更多，避免重复请求网络
    private int currentPage = 1;
    private List<NewsItem> mList = new ArrayList<NewsItem>();//装载的数据

    private RecyclerviewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.layout_weather,container,false);
      mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.srl_weather);
      mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_news);
      //添加分割线
        //mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), R.drawable.divider_mileage));

      //设置进度动画的颜色
         mSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1);
      //设置进度圈的大小：默认 或 大
         mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
      //设置下拉缩放动画效果以及下拉的高度
         mSwipeRefreshLayout.setProgressViewEndTarget(true, 200);

      //创建一个线性布局管理器
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //一加载页面就获取数据
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });

        //设置适配器
        adapter = new RecyclerviewAdapter(getContext(),mList);
        mRecyclerView.setAdapter(adapter);


        // 设置手势滑动监听
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉刷新操作
                getData();
            }
        });
        // 检测滑动事件
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            // dx:在x方向滑动的值，这个值有正负, dy:在y方向滑动的值，这个值有正负
            // dx>0:右滑, dx<0:左滑, dy<0:上滑, dy>0:下滑
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 当前屏幕所看到的子项个数
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    //已经滑动到最下面
                    boolean isRefreshing = mSwipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        // 这里使用postDelayed()方法模拟网络请求等延时操作,实际开发可去掉postDelayed()方法
                        mSwipeRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //开始加载更多
                                isLoading = true;
                               // loadMoreData();
                            }
                        }, 1000);
                    }
                }
            }
        });
        //添加点击事件
        adapter.setOnItemClickListener(new RecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("url", adapter.getList().get(position).getUrl());
                intent.putExtra("title","业界资讯");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        return view;




    }

    /**
     * 获取网络数据,改用 retrofit
     */
    private void getData( ) {
        Log.e("TAG","getData");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.76.205.241:8000/news/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        Map<String,String> map = new HashMap<>();
        map.put("kw","天气");
        map.put("site","sannong.cctv.com");
        map.put("apikey","orMn3pBBuxTDds4dB3wY6h4beWU3jCW5qgGCscEi0AP957Fx7T5LPFeyhe5wteVp");
        map.put("pageToken",String.valueOf(currentPage));

        final Call<BigItem> mBigitem = api.listNewsItem2(map);
        mBigitem.enqueue(new Callback<BigItem>() {
            @Override
            public void onResponse(Call<BigItem> call, Response<BigItem> response) {
                //Log.e("TAG",response.body().toString()+"成功");
                if (response != null){
                    if (response.body()!= null){
                        if (response.body().getData() != null){
                            mList = response.body().getData();
                            mHandler.sendEmptyMessage(1);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<BigItem> call, Throwable t) {
                if (call.isCanceled()){
                    Log.e("TAG",t.toString());
                }else{
                    Log.e("TAG",t.toString()+"失败原因");
                }
            }
        });



        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    mList = new NewsItemBiz().getNewsItems(URLUtil.NEWS_TYPE_NEWS, currentPage);
                    mHandler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }
   private Handler mHandler = new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           switch(msg.what){
               case 1:
                   adapter.addAll(mList);
                   adapter.notifyDataSetChanged();
                   isLoading = false;
                   //请求完成结束刷新状态
                   mSwipeRefreshLayout.setRefreshing(false);
                   break;
           }
       }
   };
    /**
     * 加载更多操作
     */
    private void loadMoreData() {
        currentPage++;
        mList.clear();
        getData();
    }
}
