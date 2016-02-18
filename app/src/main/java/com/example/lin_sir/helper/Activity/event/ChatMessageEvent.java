package com.example.lin_sir.helper.Activity.event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by lin_sir on 2016/2/17.
 */
public class ChatMessageEvent
{
    public AVIMTypedMessage message;
    public AVIMConversation conversation;
}
