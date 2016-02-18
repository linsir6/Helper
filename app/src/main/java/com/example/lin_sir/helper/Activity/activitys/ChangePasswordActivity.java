package com.example.lin_sir.helper.Activity.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.example.lin_sir.helper.Activity.common.BaseActivity;
import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/12.
 */
public class ChangePasswordActivity extends BaseActivity
{
    private TextView textView1, textView2, textView3;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initviews();
        initvient();

    }

    private void initviews()
    {
        settitle("修改密码");
        textView1 = (TextView) findViewById(R.id.old_password);
        textView2 = (TextView) findViewById(R.id.new_password);
        textView3 = (TextView) findViewById(R.id.new_password2);
        button = (Button) findViewById(R.id.ch_password);
    }

    private void initvient()
    {
        button.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v)
            {
                String oldPasswordString = textView1.getText().toString().trim();
                String newPasswordString = textView2.getText().toString().trim();
                String newPassword2String = textView3.getText().toString().trim();
                if (TextUtils.isEmpty(oldPasswordString))
                {
                    Toast.makeText(ChangePasswordActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(newPasswordString))
                {
                    Toast.makeText(ChangePasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                } else if (newPasswordString.equals(newPassword2String))
                {
                    AVUser userA = AVUser.getCurrentUser();
                    userA.updatePasswordInBackground(oldPasswordString, newPasswordString, new UpdatePasswordCallback()
                    {

                        @Override
                        public void done(AVException e)
                        {
                            if (e == null)
                            {
                                Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else
                            {
                                Toast.makeText(ChangePasswordActivity.this, "修改失败，请检验原密码", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_change_password;
    }
}














