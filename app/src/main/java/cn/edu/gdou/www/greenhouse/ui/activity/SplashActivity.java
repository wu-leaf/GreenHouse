package cn.edu.gdou.httpwww.greenhouse.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.gdou.httpwww.greenhouse.R;


public class SplashActivity extends AppCompatActivity implements View.OnClickListener {



    int[] imgs = new int[]{
            R.mipmap.bryant,
            R.mipmap.curry,
            R.mipmap.harden,
            R.mipmap.irving,
            R.mipmap.james};


    ImageView ivBg;

    TextView tvSkip;

    private Context mContext;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        //ButterKnife.bind(this);
        mContext = this;
        setContentView(R.layout.activity_splash);
        initViewsAndEvents();
    }





    protected void initViewsAndEvents() {
        ivBg = (ImageView) findViewById(R.id.ivBg);
        tvSkip = (TextView) findViewById(R.id.tvSkip);

        tvSkip.setOnClickListener(this);
        int index = (int) (Math.random() * imgs.length);

        ivBg.setImageResource(imgs[index]);

        timer = new CountDownTimer(3500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSkip.setText(String.format(getResources().getString(R.string.skip), (int) (millisUntilFinished / 1000 + 0.1)));
            }

            @Override
            public void onFinish() {
                tvSkip.setText(String.format(getResources().getString(R.string.skip), 0));
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        };
        timer.start();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null) {
            timer.cancel();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tvSkip){
            if (timer != null)
                timer.cancel();

            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
    }


}
