package com.example.lin_sir.helper.Activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.example.lin_sir.helper.Activity.manager.IMClientManager;
import com.example.lin_sir.helper.Activity.viewholder.LeftItemChatHolder;
import com.example.lin_sir.helper.Activity.viewholder.RightItemChatHolder;
import com.example.lin_sir.helper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin_sir on 2016/2/16.
 */
public class ChatMessageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    //消息类型，左边，也就是对方
    private static final int CHAT_ITEM_TEXT_LEFT = 0;
    //消息类型，右边，也就是当前用户
    private static final int CHAT_ITEM_TEXT_RIGHT = 1;

    //时间间隔，当超过这个时间没有回复，则会显示上次回复的时间，时间间隔为10分钟
    private static final long CHAT_ITEM_INTERVAL = 10 * 60 * 1000;

    private List<AVIMMessage> messageList = new ArrayList<>();

    public ChatMessageRecyclerAdapter()
    {

    }

    /**
     * 填充消息
     *
     * @param messages 对话中的消息集合
     */
    public void fillMessageList(List<AVIMMessage> messages)
    {
        messageList.clear();
        if (messages != null)
        {
            messageList.addAll(messages);
        }
    }

    /**
     * 填充更多的消息，一般为用户查看消息记录时用
     *
     * @param messages 对话中的消息集合
     */
    public void loadMoreMessageList(List<AVIMMessage> messages)
    {
        messageList.addAll(0, messages);
    }

    /**
     * 添加一条新的消息
     *
     * @param message 消息
     */
    public void addNewMessage(AVIMMessage message)
    {
        messageList.add(message);
    }

    /**
     * 获取第一条消息
     *
     * @return 消息列表中的第一条消息
     */
    public AVIMMessage getFirstMessage()
    {
        if (messageList != null && messageList.size() > 0)
        {
            return messageList.get(0);
        } else
        {
            return null;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        if (viewType == CHAT_ITEM_TEXT_LEFT)
        {
            Log.i("sys", "--tc-->Adapter now should return leftView");
            View leftView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_left, parent, false);
            return new LeftItemChatHolder(parent.getContext(), leftView);
        } else if (viewType == CHAT_ITEM_TEXT_RIGHT)
        {
            Log.i("sys", "--tc-->Adapter rightView");
            View rightView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_right, parent, false);
            return new RightItemChatHolder(parent.getContext(), rightView);
        } else
        {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof LeftItemChatHolder) {
            Log.i("sys", "--tc-->Adapter now is leftItemChatHolder");
            ((LeftItemChatHolder) holder).showTime(shouldShowTime(position));
            ((LeftItemChatHolder) holder).bindData(messageList.get(position));
        } else if (holder instanceof RightItemChatHolder) {
            Log.i("sys", "--tc-->Adapter rightItemChatHolder");
            ((RightItemChatHolder) holder).showTime(shouldShowTime(position));
            ((RightItemChatHolder) holder).bindData(messageList.get(position));
        }
    }


    @Override
    public int getItemViewType(int position)
    {

        AVIMMessage message = messageList.get(position);

        if (message.getFrom().equals(IMClientManager.getInstance().getClientObjId())) {
            Log.i("sys", "--tc-->Adapter left message from is " + message.getFrom());
            return CHAT_ITEM_TEXT_RIGHT;
        } else {
            Log.i("sys", "--tc-->Adapter right message from is " + message.getFrom());
            return CHAT_ITEM_TEXT_LEFT;
        }
    }


    @Override
    public int getItemCount()
    {
        return messageList.size();
    }




    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }
        long lastTime = messageList.get(position - 1).getTimestamp();
        long currentTime = messageList.get(position).getTimestamp();
        return currentTime - lastTime > CHAT_ITEM_INTERVAL;
    }

}















