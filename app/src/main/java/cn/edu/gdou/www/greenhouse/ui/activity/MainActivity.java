package cn.edu.gdou.httpwww.greenhouse.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdou.httpwww.greenhouse.R;
import cn.edu.gdou.www.greenhouse.ui.fragments.BaseFragment;
import cn.edu.gdou.www.greenhouse.ui.fragments.FragmentAbout;
import cn.edu.gdou.www.greenhouse.ui.fragments.FragmentMain;
import cn.edu.gdou.www.greenhouse.ui.fragments.FragmentNews;
import cn.edu.gdou.www.greenhouse.ui.fragments.FragmentVideo;


public class MainActivity extends AppCompatActivity {
        long ExitTime;

        private RadioGroup mRg_main;
        private List<BaseFragment> mBaseFragment;

        /**
         * 选中的Fragment的对应的位置
         */
        private int position;
        /**
         * 上次切换的Fragment
         */
        private Fragment mContent;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //初始化View
            initView();
            //初始化Fragment
            initFragment();
            //设置RadioGroup的监听
            setListener();
        }

        private void setListener() {
            mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
            //设置默认选中常用框架
            mRg_main.check(R.id.rb_main_frame);
        }
        class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_main_frame://
                        position = 0;
                        break;
                    case R.id.rb_video_frame://
                        position = 1;
                        break;
                    case R.id.rb_search_frame://
                        position = 2;
                        break;
                    case R.id.rb_about_frame://
                        position = 3;
                        break;
                    default:
                        position = 0;
                        break;
                }

                //根据位置得到对应的Fragment
                BaseFragment to = getFragment();
                //替换
                switchFrament(mContent,to);

            }
        }
        /**
         *
         * @param from 刚显示的Fragment,马上就要被隐藏了
         * @param to 马上要切换到的Fragment，一会要显示
         */
        private void switchFrament(Fragment from,Fragment to) {
            if(from != to){
                mContent = to;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //才切换
                //判断有没有被添加
                if(!to.isAdded()){
                    //to没有被添加
                    //from隐藏
                    if(from != null){
                        ft.hide(from);
                    }
                    //添加to
                    if(to != null){
                        ft.add(R.id.fl_content,to).commit();
                    }
                }else{
                    //to已经被添加
                    // from隐藏
                    if(from != null){
                        ft.hide(from);
                    }
                    //显示to
                    if(to != null){
                        ft.show(to).commit();
                    }
                }
            }

        }
        /**
         * 根据位置得到对应的Fragment
         * @return
         */
        private BaseFragment getFragment() {
            BaseFragment fragment = mBaseFragment.get(position);
            return fragment;
        }

        private void initFragment() {
            mBaseFragment = new ArrayList<>();
            mBaseFragment.add(new FragmentMain());
            mBaseFragment.add(new FragmentVideo());
            mBaseFragment.add(new FragmentNews());
            mBaseFragment.add(new FragmentAbout());
        }

        private void initView() {
            setContentView(R.layout.activity_main);
            mRg_main = (RadioGroup) findViewById(R.id.rg_main);
        }





        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode != 4 || event.getRepeatCount() != 0) {
                return super.onKeyDown(keyCode, event);
            }
            if (System.currentTimeMillis() - this.ExitTime > 2000) {


                this.ExitTime = System.currentTimeMillis();
                Toast.makeText(this,"再按一次退出应用",Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
            return true;
        }
    }

