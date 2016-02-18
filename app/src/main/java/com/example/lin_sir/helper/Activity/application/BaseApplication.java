package com.example.lin_sir.helper.Activity.application;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.example.lin_sir.helper.Activity.activitys.MainActivity;
import com.example.lin_sir.helper.Activity.manager.IMMessageHandler;

/**
 * Created by lin_sir on 2016/2/8.
 */
public class BaseApplication extends Application
{


    @Override
    public void onCreate()
    {
        super.onCreate();
        AVOSCloud.initialize(this, "gDpARhub8jcfHz7vYKGsDhP9-gzGzoHsz", "gvbvf4DiEbIest3NLWocwT3s");

        //启动消息推送服务
        PushService.setDefaultPushCallback(this, MainActivity.class);
        //启动即时通信服务
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new IMMessageHandler(this));
    }
}
