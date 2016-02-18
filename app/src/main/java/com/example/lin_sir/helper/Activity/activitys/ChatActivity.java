package com.example.lin_sir.helper.Activity.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.lin_sir.helper.Activity.adapter.ChatMessageRecyclerAdapter;
import com.example.lin_sir.helper.Activity.common.BaseActivity;
import com.example.lin_sir.helper.Activity.event.ChatMessageEvent;
import com.example.lin_sir.helper.Activity.manager.IMClientManager;
import com.example.lin_sir.helper.Activity.notification.NotificationUtils;
import com.example.lin_sir.helper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by lin_sir on 2016/2/16.
 */
public class ChatActivity extends BaseActivity
{

    private Toolbar mToolbar;
    private RecyclerView rvChatList;
    private LinearLayoutManager layoutManager;
    private EditText etMessage;
    private Button btSend;


    private AVIMClient imClient;
    private List<String> members;
    private AVIMConversation mConversation;

    private ChatMessageRecyclerAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initviews();

        String fromUserObjId = getIntent().getStringExtra("fromUserObjId");

        queryFromUserInfo(fromUserObjId);
        queryConversation(fromUserObjId);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (mConversation != null)
        {
            NotificationUtils.addTag(mConversation.getConversationId());
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        NotificationUtils.removeTag(mConversation.getConversationId());
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initviews()
    {
        //mToolbar = (Toolbar) findViewById(R.id.toolbar_char);
        rvChatList = (RecyclerView) findViewById(R.id.rv_chat_chatList);
        etMessage = (EditText) findViewById(R.id.et_chat_message);
        btSend = (Button) findViewById(R.id.bt_chat_send);

        layoutManager = new LinearLayoutManager(ChatActivity.this);
        rvChatList.setLayoutManager(layoutManager);

        messageAdapter = new ChatMessageRecyclerAdapter();
        rvChatList.setAdapter(messageAdapter);

        btSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String content = etMessage.getText().toString();
                if (TextUtils.isEmpty(content))
                {
                    Toast.makeText(ChatActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else
                {
                    etMessage.setText("");
                    sendMessage(content);
                }
            }
        });
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_chat;
    }


    private void sendMessage(String content)
    {
        if (mConversation != null)
        {

            AVIMTextMessage message = new AVIMTextMessage();
            message.setText(content);
            messageAdapter.addNewMessage(message);
            messageAdapter.notifyDataSetChanged();
            scrollToListBottom();
            mConversation.sendMessage(message, new AVIMConversationCallback()
            {
                @Override
                public void done(AVIMException e)
                {
                    messageAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void queryFromUserInfo(String objId)
    {
        AVQuery<AVUser> query = new AVQuery<>("_User");
        query.whereEqualTo("objectId", objId);
        query.findInBackground(new mFindCallback());
    }

    private class mFindCallback extends FindCallback<AVUser>
    {
        @Override
        public void done(List<AVUser> list, AVException e)
        {
            if (e == null)
            {
                if (list.size() > 0)
                {
                    setTitle(list.get(0).getString("username"));

                } else
                {
                    Log.i("sys", "--tc-->Chat find fromUser  no such user");
                }
            } else
            {
                Log.i("sys", "--tc-->Chat find fromUser error");
            }
        }
    }

    private void queryConversation(String fromObjId)
    {

        //因为查询条件是要一个List，所以将对方的Id放入一个List中
        members = new ArrayList<>();
        members.add(fromObjId);

        //获取当前用户的Client实例
        imClient = IMClientManager.getInstance().getClient();

        AVIMConversationQuery query = imClient.getQuery();
        query.withMembers(members, true);

        query.findInBackground(new mAVIMConversationQueryCallback());
    }

    private class mAVIMConversationQueryCallback extends AVIMConversationQueryCallback
    {
        @Override
        public void done(List<AVIMConversation> list, AVIMException e)
        {
            if (e == null)
            {
                if (list != null && list.size() > 0)
                {
                    //获取当前用户的fromUser的对话,这里有个BUG，当获取到多个对话时，会默认使用第一个对话，后续再改
                    Log.i("sys", "--tc-->ChatActivity，findConversation " + list.size());
                    initConversation(list.get(0));
                } else
                {
                    //如果没有获取到，也就是没有创建这个对话，则新建当前用户和fromUser的对话
                    Map<String, Object> attr = new HashMap<>();
                    //属性暂定这样，后期再改
                    attr.put("type", 1);
                    imClient.createConversation(members, attr, new AVIMConversationCreatedCallback()
                    {
                        @Override
                        public void done(AVIMConversation avimConversation, AVIMException e)
                        {
                            if (e == null)
                            {
                                //对话创建成功
                                initConversation(avimConversation);
                            } else
                            {
                                Log.i("sys", "--tc-->Chat create conversation error");
                            }
                        }
                    });
                }
            } else
            {
                Log.i("sys", "--tc-->Chat find conversation error");
            }
        }
    }

    /**
     * 初始化对话
     *
     * @param conversation 对话
     */
    private void initConversation(AVIMConversation conversation)
    {
        mConversation = conversation;

        fillMessageToList();

        //给通知添加当前回话的tag，表示这个对话不需要通知了
        NotificationUtils.addTag(conversation.getConversationId());

    }

    private void fillMessageToList()
    {

        mConversation.queryMessages(new AVIMMessagesQueryCallback()
        {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e)
            {
                if (e == null)
                {
                    messageAdapter.fillMessageList(list);
                    rvChatList.setAdapter(messageAdapter);
                    messageAdapter.notifyDataSetChanged();
                    scrollToListBottom();
                }
            }
        });
    }


    public void onEvent(ChatMessageEvent event)
    {
        if (mConversation != null && event != null &&
                mConversation.getConversationId().equals(event.conversation.getConversationId()))
        {
            messageAdapter.addNewMessage(event.message);
            messageAdapter.notifyDataSetChanged();
            scrollToListBottom();
        }
    }


    private void scrollToListBottom()
    {
        layoutManager.scrollToPositionWithOffset(messageAdapter.getItemCount() - 1, 0);
    }
}


















