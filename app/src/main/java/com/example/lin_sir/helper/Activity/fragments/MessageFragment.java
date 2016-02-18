package com.example.lin_sir.helper.Activity.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.lin_sir.helper.Activity.adapter.MainRecyclerAdapter;
import com.example.lin_sir.helper.Activity.manager.IMClientManager;
import com.example.lin_sir.helper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lin_sir on 2016/2/7.
 */
public class MessageFragment extends Fragment
{

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MainRecyclerAdapter mAdapter;

    private List<Map<String, String>> dataList;

    private Handler msgHandler;
    private static final int MSG_QUERY_OK = 100;
    private static final int MSG_QUERY_NO_DATA = 101;
    private static final int MSG_QUERY_ERROR = 102;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_message, null);
        dataList = new ArrayList<>();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_main666);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_list666);

        refreshLayout.setColorSchemeResources(R.color.avoscloud_blue, R.color.colorAccent, R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(new OnListRefreshListener());

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MainRecyclerAdapter(getActivity(), dataList);
        recyclerView.setAdapter(mAdapter);
        initHandler();

        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null)
        {
            IMLogin(currentUser.getObjectId());
        } else
        {
            Log.i("sys", "--tc-->main currentUser is null");
        }
        queryData();
        return view;


    }


    private void initHandler()
    {
        msgHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case MSG_QUERY_OK:
                        mAdapter.notifyDataSetChanged();
                        break;
                    case MSG_QUERY_NO_DATA:
                        Log.i("sys", "--tc-->Main query no data");
                        break;
                    case MSG_QUERY_ERROR:
                        Log.i("sys", "--tc-->Main query error");
                        break;
                }
                refreshLayout.setRefreshing(false);
            }
        };
    }

    private class OnListRefreshListener implements SwipeRefreshLayout.OnRefreshListener
    {

        @Override
        public void onRefresh()
        {
            refreshLayout.setRefreshing(true);
            queryData();
        }
    }

    private void queryData()
    {
        AVQuery<AVUser> query = new AVQuery<>("_User");

        query.findInBackground(new mFindCallback());
    }

    private class mFindCallback extends FindCallback<AVUser>
    {
        @Override
        public void done(List<AVUser> list, AVException e)
        {
            Message msg = Message.obtain();
            if (e == null)
            {
                if (list.size() > 0)
                {
                    fillData(list);
                    msg.what = MSG_QUERY_OK;
                } else
                {

                    msg.what = MSG_QUERY_NO_DATA;
                }

            } else
            {
                Log.i("sys", "--tc-->Main query error:" + e.toString());
                msg.what = MSG_QUERY_ERROR;
            }
            msgHandler.sendMessage(msg);
        }
    }

    private void fillData(List<AVUser> userList)
    {
        dataList.clear();
        for (int i = 0; i < userList.size(); i++)
        {
            Map<String, String> dataItem = new HashMap<>();
            AVUser userItem = userList.get(i);
            dataItem.put("username", userItem.getString("username"));
            dataItem.put("userObjId", userItem.getObjectId());
            dataItem.put("nickname", userItem.getString("nickname"));
            dataItem.put("imageUrl", userItem.getString("imageUrl"));

            dataList.add(dataItem);
        }
    }


    //-------------------------以下是即时通信相关--------------------------------------

    /**
     * 登录聊天服务器
     */
    private void IMLogin(String clientId)
    {
        IMClientManager.getInstance().open(clientId, new AVIMClientCallback()
        {
            @Override
            public void done(AVIMClient avimClient, AVIMException e)
            {
                Log.i("sys", "--tc-->main im callback");
                if (e == null)
                {
                    Log.i("sys", "--tc-->main im login success");
                } else
                {
                    Log.i("sys", "--tc-->main IM login error:" + e.toString());
                }
            }
        });
    }


}
