package com.example.lin_sir.helper.Activity.manager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.lin_sir.helper.Activity.event.ChatMessageEvent;
import com.example.lin_sir.helper.Activity.notification.NotificationBroadcastReceiver;
import com.example.lin_sir.helper.Activity.notification.NotificationUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by lin_sir on 2016/2/17.
 */
public class IMMessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage>
{

    private Context mContext;

    public IMMessageHandler(Context context) {
        mContext = context;
    }


    /**
     * 收到消息时调用
     */
    @Override
    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
        super.onMessage(message, conversation, client);

        String clientObjId = IMClientManager.getInstance().getClientObjId();

        Log.i("sys", "--tc-->onMessage " + clientObjId);

        //如果收到的消息id等于当前用户的id,也就是说如果消息是发送给当前用户的
        if (clientObjId != null && clientObjId.equals(client.getClientId())) {
            //过滤掉自己发的消息
            if (!clientObjId.equals(message.getFrom())) {

                Log.i("sys", "--tc-->onMessage messageFrom clientId is" + message.getFrom());
                //对接收到的消息进行处理,，这里是将消息发送给聊天界面，稍后应该将其存入DB
                sendMessageEvent(message, conversation);

                if (NotificationUtils.isShowNotification(conversation.getConversationId())) {
                    //发送通知
                    sendMessageNotification(message, conversation);
                }
            }
        } else {
            client.close(null);
        }
    }

    /**
     * 将收到的消息发送给聊天界面
     *
     * @param message      消息
     * @param conversation 消息所在的对话
     */
    private void sendMessageEvent(AVIMTypedMessage message, AVIMConversation conversation) {

        ChatMessageEvent event = new ChatMessageEvent();
        event.message = message;
        event.conversation = conversation;
        EventBus.getDefault().post(event);
    }


    /**
     * 发送收到消息的通知
     */
    private void sendMessageNotification(AVIMTypedMessage message, AVIMConversation conversation) {

        //目前仅支持文本消息
        String notifyContent = message instanceof AVIMTextMessage ?
                ((AVIMTextMessage) message).getText() : "暂不支持此消息类型";

        Intent intent = new Intent(mContext, NotificationBroadcastReceiver.class);
        intent.putExtra("conversationId", conversation.getConversationId());
        intent.putExtra("fromUserObjId", message.getFrom());
        NotificationUtils.showNotification(mContext, "新消息", notifyContent, null, intent);
    }

}
