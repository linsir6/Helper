package com.example.lin_sir.helper.Activity.activitys;

/**
 * Created by lin_sir on 2016/2/7.
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.lin_sir.helper.Activity.common.BaseActivity;
import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/7.
 * 单独的注册界面
 */
public class SignActivity extends BaseActivity
{
    private EditText username, password;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initviews();
        initevents();
    }

    private void initviews()
    {
        settitle("登录界面");
        username = (EditText) findViewById(R.id.username_signActivity);
        password = (EditText) findViewById(R.id.password_signActivity);
        login = (Button) findViewById(R.id.login_SignActivity);
// /*
//        测试阶段
//         */
//        username.setText("18304523113");
//        password.setText("1");
    }

    private void initevents()
    {
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usernameString = username.getText().toString().trim();
                String passwordstring = password.getText().toString().trim();
                if (TextUtils.isEmpty(usernameString))
                {
                    Toast.makeText(SignActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passwordstring))
                {
                    Toast.makeText(SignActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(usernameString) && !TextUtils.isEmpty(passwordstring))
                    AVUser.logInInBackground(usernameString, passwordstring, new LogInCallback()
                    {
                        public void done(AVUser user, AVException e)
                        {


                            if (user != null)
                            {
                                Intent intent = new Intent(SignActivity.this, MainActivity.class);
                                finish();
                                //user = AVUser.getCurrentUser();
                                startActivity(intent);
                            } else
                            {
                                Toast.makeText(SignActivity.this, "输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }
        });
    }


    @Override
    public int getLayoutId()
    {
        return R.layout.activity_sign;
    }
}

















