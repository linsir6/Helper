package com.example.lin_sir.helper.Activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.lin_sir.helper.Activity.adapter.TasksAdapter;
import com.example.lin_sir.helper.Activity.utils.BeanUtil;
import com.example.lin_sir.helper.Activity.utils.TasksBean;
import com.example.lin_sir.helper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin_sir on 2016/2/8.
 */
public class MeOrders extends android.support.v4.app.Fragment
{
    private List<TasksBean> fruitBeanList = new ArrayList<TasksBean>();
    private FloatingActionButton mFabSend;
    private SwipeRefreshLayout mRefreshLayout;
    private Context mContext = null;
    private EditText editText111, editText222;
    private TextView textView1, textView2;
    private ListView listView;
    private PopupWindow popupWindow;
    private TasksAdapter adapter;
    AVUser user = AVUser.getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_me_order, null);

        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //final View contentview = inflater.inflate(R.layout.popup_process, null);
//        textView1 = (TextView) contentview.findViewById(R.id.abandon_popup);
//        textView2 = (TextView) contentview.findViewById(R.id.submit_popup);
//        editText111 = (EditText) contentview.findViewById(R.id.title_popup111);
//        editText222 = (EditText) contentview.findViewById(R.id.content_popup222);

        listView = (ListView) view.findViewById(R.id.listview_frgmentorders22);
        listView.setAdapter(adapter);
        final AVUser user = AVUser.getCurrentUser();
//        setview();
        //setview();
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_fragment_list22);
        adapter = new TasksAdapter(getContext(), R.layout.listview_item_tasks, fruitBeanList);
        listView.setAdapter(adapter);


//        textView1.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                popupWindow.dismiss();
//            }
//        });
//
//        textView2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                String userphone = user.getString("nickname");
//                String id = user.getUsername();
//                final String title111 = editText111.getText().toString();
//                final String title222 = editText222.getText().toString();
//
//                if (TextUtils.isEmpty(title111))
//                {
//                    Toast.makeText(getActivity(), "亲，标题不能为空", Toast.LENGTH_LONG).show();
//                } else if (TextUtils.isEmpty(title222))
//                {
//                    Toast.makeText(getActivity(), "亲，内容不能为空", Toast.LENGTH_LONG).show();
//                } else
//                {
//                    AVObject post = new AVObject("tasks");
//                    post.put("userphone", userphone);
//                    post.put("title", title111);
//                    post.put("id", id);
//                    post.put("content", title222);
//                    post.saveInBackground();
//                    Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_LONG).show();
//                    popupWindow.dismiss();
//                }
//            }
//        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
                                            {
                                                @Override
                                                public void onRefresh()
                                                {
                                                    setview();
                                                }
                                            }

        );
        listView = (ListView) view.findViewById(R.id.listview_frgmentorders22);
        listView.setAdapter(adapter);


//        mFabSend = (FloatingActionButton) view.findViewById(R.id.id_fab_send);
//        mFabSend.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                contentview.setFocusable(true); // 这个很重要
//                contentview.setFocusableInTouchMode(true);
//                popupWindow = new PopupWindow(contentview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//
//                popupWindow.setFocusable(true);
//                popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
//                popupWindow.setOutsideTouchable(false);
//                contentview.setOnKeyListener(new View.OnKeyListener()
//                {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event)
//                    {
//                        if (keyCode == KeyEvent.KEYCODE_BACK)
//                        {
//                            popupWindow.dismiss();
//
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                popupWindow.showAtLocation(v, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
//
//            }
//        });
        return view;
    }


    private void setview()
    {
        String id = user.getUsername();
        fruitBeanList.clear();
        AVQuery<AVObject> query = new AVQuery<AVObject>("tasks");
        query.whereEqualTo("id", id);
        query.setLimit(10);
        query.findInBackground(new FindCallback<AVObject>()
                               {
                                   public void done(List<AVObject> avObjects, AVException e)
                                   {
                                       if (e == null)
                                       {
                                           for (int i = 0; i < avObjects.size(); i++)
                                           {
                                               TasksBean bean = BeanUtil.obj2statebean(avObjects.get(i));
                                               fruitBeanList.add(bean);
                                               listView.setAdapter(adapter);

                                           }
                                           listView.setAdapter(adapter);
                                           Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                                           Log.d("成功", "meorders查询到" + avObjects.size() + " 条符合条件的数据");
                                       } else
                                       {

                                           Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                                           Log.d("失败", "meorders查询错误: " + e.getMessage());
                                       }
                                   }
                               }

        );

        mRefreshLayout.setRefreshing(false);

    }

}
