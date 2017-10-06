package cn.edu.gdou.www.greenhouse.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.gdou.httpwww.greenhouse.R;


/**
 * Created by Veyron on 2017/3/13.
 * Function：
 */

public class MyFragment extends Fragment {
    View view;
    /**
     * 标题
     */
    private final String title;
    /**
     * 内容
     */
    private final String content;
    Context mContext;
    TextView textView;

    /**
     * 得到内容
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 得到标题
     * @return
     */
    public String getTitle() {
        return title;
    }

    public MyFragment(String title, String content){
        super();
        this.title = title;
        this.content = content;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //上下文
        mContext = getActivity();
    }

    /**
     * 创建视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建视图
        switch(getContent()){
            case "1":
                view = inflater.inflate(R.layout.layout_agro,null);
                initClick(view);
                break;
            case "2":
                view = inflater.inflate(R.layout.layout_weather,null);
                initClick(view);
                break;
            case "3":
                view = inflater.inflate(R.layout.layout_disaster,null);
                initClick(view);
                break;

        }

        return view;
    }

    private void initClick(View view) {

    }

    /**
     * 绑定数据
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
