package cn.edu.gdou.www.greenhouse.ui.fragments;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import cn.edu.gdou.httpwww.greenhouse.R;
import cn.edu.gdou.www.greenhouse.ui.adapter.MyFragmentPagerAdapter;
import cn.edu.gdou.www.greenhouse.ui.adapter.MyViewPagerAdapter;
import cn.edu.gdou.www.greenhouse.ui.fragments.infragment.DisasterFragment;
import cn.edu.gdou.www.greenhouse.ui.fragments.infragment.NewsFragment;
import cn.edu.gdou.www.greenhouse.ui.fragments.infragment.WeatherFragment;


public class FragmentNews extends BaseFragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<Fragment> fragments;
    private List<String> mStringList;
    MyViewPagerAdapter adapter;
    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    @Override
    protected View initView() {

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View contentView = mInflater.inflate(R.layout.layout_news, null);

        viewPager = (ViewPager) contentView.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) contentView.findViewById(R.id.tabLayout);

        return contentView;
    }

    @Override
    protected void initData() {
        super.initData();
        //初始化数据
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new WeatherFragment());
        fragments.add(new DisasterFragment());

        mStringList = new ArrayList<>();
        mStringList.add("农业资讯");
        mStringList.add("自然气候");
        mStringList.add("自然灾害");

        //设置ViewPager的适配器
        //设置tab名称
        tabLayout.addTab(tabLayout.newTab().setText(mStringList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(mStringList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(mStringList.get(2)));


        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(),fragments,mStringList);

        viewPager.setAdapter(mFragmentPagerAdapter);

        //tablayout viewpage  绑定
        tabLayout.setupWithViewPager(viewPager);

       /* //adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        adapter=new MyViewPagerAdapter(getChildFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        //关联ViewPager
        tabLayout.setupWithViewPager(viewPager);*/
        //设置固定的
        //tabLayout.setTabMode(TabLayout.MODE_FIXED);
//设置滑动的。
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

}
