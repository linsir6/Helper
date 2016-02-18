package com.example.lin_sir.helper.Activity.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.lin_sir.helper.Activity.common.BaseActivity;
import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/7.
 * 单独的注册界面
 */
public class RegisterActivity extends BaseActivity
{

    private EditText username, password, code;
    private Button getCode, register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        settitle("注册界面");
        initviews();
    }

    private void initviews()
    {
        username = (EditText) findViewById(R.id.phone_registerActivity);
        password = (EditText) findViewById(R.id.password_registerActivity);
        code = (EditText) findViewById(R.id.code_registerActivity);
        getCode = (Button) findViewById(R.id.getCode_registerActivity);
        register = (Button) findViewById(R.id.register_registerActivity);

        getCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = username.getText().toString().trim();
                Boolean isbool = isPhoneNumber(username.getText().toString().trim());
                if (isbool)
                {

                    AVOSCloud.requestSMSCodeInBackground(name, new RequestMobileCodeCallback()
                    {
                        @Override
                        public void done(AVException e)
                        {
                            if (e == null)
                            {
                                timer.start();
                                Toast.makeText(RegisterActivity.this, "验证码正在路上~", Toast.LENGTH_SHORT).show();

                            } else
                            {
                                Toast.makeText(RegisterActivity.this, "服务器连接异常", Toast.LENGTH_SHORT).show();
                                Log.i("aaa", "----------------------------------->" + e);
                            }


                        }
                    });
                } else

                {
                    Toast.makeText(RegisterActivity.this, "您输入的手机号码有误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String codeString = code.getText().toString().trim();
                final String passwordString = password.getText().toString().trim();
                final String usernameString = username.getText().toString().trim();
                if (TextUtils.isEmpty(passwordString))
                {
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passwordString))
                {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(passwordString) && !TextUtils.isEmpty(passwordString))
                {

                    AVOSCloud.verifySMSCodeInBackground(codeString, usernameString, new AVMobilePhoneVerifyCallback()
                    {

                        @Override
                        public void done(AVException e)
                        {
                            //此处可以完成用户想要完成的操作
                            if (e == null)
                            {
                                //保存数据到云端
                                AVObject post = new AVObject("user");
                                post.put("phone", usernameString);
                                post.put("mima", passwordString);
                                post.saveInBackground();
                                //专门的用户保存登陆信息
                                AVUser user = new AVUser();
                                user.setUsername(usernameString);
                                user.setPassword(passwordString);
                                user.setMobilePhoneNumber(usernameString);
                                user.put("imageUrl", null);
                                user.signUpInBackground(new SignUpCallback()
                                {
                                    public void done(AVException e)
                                    {
                                        if (e == null)
                                        {
                                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterActivity.this, SignActivity.class);
                                            finish();
                                            startActivity(intent);
                                        } else
                                        {
                                            Toast.makeText(RegisterActivity.this, "此手机号已被注册", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else
                            {
                                Log.i("ddd", "------------------------->" + e);
                                Toast.makeText(RegisterActivity.this, "请核对验证码", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }


            }
        });

    }


    public boolean isPhoneNumber(String str)
    {
        String strRegex = "[1][34578]\\d{9}";
        boolean result = str.matches(strRegex);
        return result;
    }


    @Override
    public int getLayoutId()
    {
        return R.layout.activity_register;
    }


    CountDownTimer timer = new CountDownTimer(60000, 1000)
    {
        @Override
        public void onTick(long millisUntilFinished)
        {
            //getCode.setTextSize(18);
            getCode.setText("  " + millisUntilFinished / 1000 + "s");
            try
            {
                Thread.sleep(500);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish()
        {

            //code.setTextSize(15);
            getCode.setText("重新发送");
            //flag = true;

        }

    };

}















