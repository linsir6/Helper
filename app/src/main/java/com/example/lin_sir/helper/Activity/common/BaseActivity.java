package com.example.lin_sir.helper.Activity.common;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/7.
 * 大部分activity继承自此类，整合了actionbar
 */
public abstract class BaseActivity extends FragmentActivity
{
    private TextView textView;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        textView = (TextView) findViewById(R.id.tvTitle);
        relativeLayout = (RelativeLayout) findViewById(R.id.rlContent);


        View v = getLayoutInflater().inflate(getLayoutId(), relativeLayout, false);//IOC,控制反转；在父类中，调用子类的实现
        relativeLayout.addView(v);
    }

    public abstract int getLayoutId();

    public void settitle(String text)
    {
        textView.setText(text);
    }


    //可以在此添加activity启动的动画效果


}




















