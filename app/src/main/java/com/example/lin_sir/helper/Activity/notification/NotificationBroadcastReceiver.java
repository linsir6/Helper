package com.example.lin_sir.helper.Activity.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.lin_sir.helper.Activity.activitys.ChatActivity;
import com.example.lin_sir.helper.Activity.activitys.MainActivity;
import com.example.lin_sir.helper.Activity.manager.IMClientManager;

/**
 * Created by lin_sir on 2016/2/17.
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver
{


    @Override
    public void onReceive(Context context, Intent intent) {



        if (IMClientManager.getInstance().getClient() == null) {
            gotoMainActivity(context);
        } else {
            String conversationId = intent.getStringExtra("conversationId");
            String fromUserObjId = intent.getStringExtra("fromUserObjId");
            if (!TextUtils.isEmpty(conversationId)) {
                gotoChatActivity(context, fromUserObjId);
            }
        }
    }

    /**
     * 如果 app 上下文已经缺失，则跳转到主页面
     *
     * @param context 上下文
     */
    private void gotoMainActivity(Context context) {


        Intent startActivityIntent = new Intent(context, MainActivity.class);
        startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startActivityIntent);
    }

    /**
     * 跳转至单聊页面
     *
     * @param context       上下文
     * @param fromUserObjId 消息发送方的id
     */
    private void gotoChatActivity(Context context, String fromUserObjId) {

        Intent startActivityIntent = new Intent(context, ChatActivity.class);
        startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityIntent.putExtra("fromUserObjId", fromUserObjId);
        context.startActivity(startActivityIntent);
    }


}
