package com.example.lin_sir.helper.Activity.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lin_sir.helper.Activity.common.BaseActivity;
import com.example.lin_sir.helper.Activity.fragments.MeFragment;
import com.example.lin_sir.helper.Activity.fragments.MessageFragment;
import com.example.lin_sir.helper.Activity.fragments.ReceiveFragment;
import com.example.lin_sir.helper.Activity.fragments.SendFragment;
import com.example.lin_sir.helper.R;

public class MainActivity extends BaseActivity implements OnClickListener
{
    private Fragment[] mFragments;
    private FragmentManager manager;
    private FragmentTransaction fragmentTransaction;

    private RadioButton receive, send, message, me;
    private RadioGroup rg;

    private FragmentActivity mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;

        initviews();
    }

    private void initviews()
    {
        receive = (RadioButton) findViewById(R.id.radiobutton1);
        send = (RadioButton) findViewById(R.id.radiobutton2);
        message = (RadioButton) findViewById(R.id.radiobutton3);
        me = (RadioButton) findViewById(R.id.radiobutton4);
        rg = (RadioGroup) findViewById(R.id.rgTab);

        receive.setOnClickListener(this);
        send.setOnClickListener(this);
        message.setOnClickListener(this);
        me.setOnClickListener(this);

        mFragments = new Fragment[4];
        mFragments[0] = new ReceiveFragment();// 页面一添加到集合中
        mFragments[1] = new SendFragment();// 页面二添加到集合中
        mFragments[2] = new MessageFragment();// 页面三添加到集合中
        mFragments[3] = new MeFragment();// 页面四添加到集合中

        manager = mContext.getSupportFragmentManager();// 获得FragmentManager
        fragmentTransaction = manager.beginTransaction();// 获得事务
        fragmentTransaction.add(R.id.flContainer, mFragments[0], mFragments[0].getClass().getName());// 添加到FragmentLayout中
        fragmentTransaction.add(R.id.flContainer, mFragments[1], mFragments[1].getClass().getName());// 添加到FragmentLayout中
        fragmentTransaction.add(R.id.flContainer, mFragments[2], mFragments[2].getClass().getName());// 添加到FragmentLayout中
        fragmentTransaction.add(R.id.flContainer, mFragments[3], mFragments[3].getClass().getName());// 添加到FragmentLayout中

        // 默认显示页面一，隐藏页面二
        settitle("接任务");
        fragmentTransaction.show(mFragments[0]);
        fragmentTransaction.hide(mFragments[1]);
        fragmentTransaction.hide(mFragments[2]);
        fragmentTransaction.hide(mFragments[3]);
        fragmentTransaction.commitAllowingStateLoss();// 提交


    }

    @Override
    public void onClick(View v)
    {
        FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
        switch (v.getId())
        {
            case R.id.radiobutton1:
                transaction.show(mFragments[0]);
                transaction.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]);
                transaction.commitAllowingStateLoss();
                settitle("请求");
                break;
            case R.id.radiobutton2:
                transaction.show(mFragments[1]);
                transaction.hide(mFragments[0]).hide(mFragments[2]).hide(mFragments[3]);
                transaction.commitAllowingStateLoss();
                settitle("相关");
                break;
            case R.id.radiobutton3:
                transaction.show(mFragments[2]);
                transaction.hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[3]);
                transaction.commitAllowingStateLoss();
                settitle("消息");
                break;
            case R.id.radiobutton4:
                transaction.show(mFragments[3]);
                transaction.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[0]);
                transaction.commitAllowingStateLoss();
                settitle("我");
                break;

            default:
                break;

        }
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_main;
    }


}
