package com.example.lin_sir.helper.Activity.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.example.lin_sir.helper.Activity.common.BaseActivity;
import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/12.
 */
public class FeedBackActivity extends BaseActivity
{
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        settitle("意见反馈");
        editText = (EditText) findViewById(R.id.editext_feedback);
        button = (Button) findViewById(R.id.button_feedback);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AVObject post = new AVObject("feedBack");
                post.put("feedback", editText.getText().toString());
                post.saveInBackground();
                Toast.makeText(FeedBackActivity.this, "反馈成功", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_feedback;
    }
}
