package com.example.lin_sir.helper.Activity.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/6.
 * 选择注册和登陆的界面
 */

public class SignInActivity extends Activity
{
    private TextView mWord, mWord2;
    private Button register, signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mWord = (TextView) findViewById(R.id.app_name_guideActivity);
        mWord2 = (TextView) findViewById(R.id.app_summary_guideActivity);
        mWord.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));
        mWord2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));
        register = (Button) findViewById(R.id.register_guideActivity);
        signIn = (Button) findViewById(R.id.signIn_guideActivity);

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SignInActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });
    }
}























