package com.example.lin_sir.helper.Activity.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.avos.avoscloud.AVUser;
import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/6.
 * 引导界面，有一个延时跳转,还要判断是否有缓存的对象，有缓存对象直接登陆，没有要输入密码
 */
public class GudieActivity extends Activity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null)
        {
            // 允许用户使用应用
            user();

        } else
        {
            //缓存用户对象为空时， 可打开用户注册界面…
            noUser();
        }


    }


    private void noUser()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(GudieActivity.this, SignInActivity.class);
                startActivity(intent);
                GudieActivity.this.finish();
            }
        }, 1500);
    }

    private void user()
    {
        Intent intent = new Intent(GudieActivity.this, MainActivity.class);
        startActivity(intent);
        GudieActivity.this.finish();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    protected void onPause()
    {
        super.onPause();
    }
}























