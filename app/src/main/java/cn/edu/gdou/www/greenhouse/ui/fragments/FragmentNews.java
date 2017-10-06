package cn.edu.gdou.www.greenhouse.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.edu.gdou.httpwww.greenhouse.R;
import cn.edu.gdou.www.greenhouse.ui.adapter.MyViewPagerAdapter;


public class FragmentNews extends BaseFragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<MyFragment> fragments;
    MyViewPagerAdapter adapter;

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
        fragments.add(new MyFragment("农业潮流","1"));
        fragments.add(new MyFragment("天气预报","2"));
        fragments.add(new MyFragment("灾害预警","3"));

        //设置ViewPager的适配器

        //adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        adapter=new MyViewPagerAdapter(getChildFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        //关联ViewPager
        tabLayout.setupWithViewPager(viewPager);
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
