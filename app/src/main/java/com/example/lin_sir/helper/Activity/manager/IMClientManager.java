package com.example.lin_sir.helper.Activity.manager;

import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

/**
 * Created by lin_sir on 2016/2/16.
 */
public class IMClientManager
{
    private static IMClientManager manager;

    private AVIMClient client;

    private String clientObjId;

    /**
     * 单例模式获取当前类的实例
     */
    public synchronized static IMClientManager getInstance() {
        if (manager == null) {
            manager = new IMClientManager();
        }
        return manager;
    }


    /**
     * 登录聊天服务器
     *
     * @param clientId 当前用户的ObjId
     * @param callback 回调
     */
    public void open(String clientId, AVIMClientCallback callback) {
        clientObjId = clientId;
        client = AVIMClient.getInstance(clientId);
        Log.i("sys", "--tc--> clientId is" + clientId);
        client.open(callback);
        Log.i("sys", "--tc--> ImChatManager open done");
    }

    /**
     * 获取登录的用户实例
     */
    public AVIMClient getClient() {
        return client;
    }

    /**
     * 获取登录的用户ObjId，如果在没有登录的情况下调用此方法，会返回null
     */
    public String getClientObjId() {
        if (TextUtils.isEmpty(clientObjId)) {
            Log.i("sys", "--tc-->请先登录聊天服务器");
            return null;
        } else {
            Log.i("sys", "--tc-->currentUser clientObjId is" + clientObjId);
            return clientObjId;
        }
    }
}
