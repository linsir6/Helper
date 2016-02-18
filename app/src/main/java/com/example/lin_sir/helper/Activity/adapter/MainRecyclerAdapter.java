package com.example.lin_sir.helper.Activity.adapter;


import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.lin_sir.helper.Activity.activitys.ChatActivity;
import com.example.lin_sir.helper.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

/**
 * Created by lin_sir on 2016/2/17.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>
{
    private List<Map<String, String>> list;
    private Context mContext;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private ProgressBar pbFooterLoadMore;
    private TextView tvFooterNoMore;

    public MainRecyclerAdapter(Context context, List<Map<String, String>> data)
    {
        mContext = context;
        list = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_ITEM)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recycler, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_recycler, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        if (holder instanceof ItemViewHolder)
        {

            Map dataItem = list.get(position);
            String username = dataItem.get("username").toString();
            String userObjId = dataItem.get("userObjId").toString();
            String nickname = dataItem.get("nickname").toString();
            String userview;

            if (TextUtils.isEmpty(dataItem.get("imageUrl").toString()))
            {
                userview = dataItem.get("imageUrl").toString();
            } else
            {
                userview = "http://ac-gDpARhub.clouddn.com/67mz4eiqN8nLabmGcdW95g1A016mDwdpuH8brXgO.png";
            }

            ((ItemViewHolder) holder).tvUsername.setText(username);
            ((ItemViewHolder) holder).llAll.setOnClickListener(new UserItemClickListener(userObjId));
            ((ItemViewHolder) holder).tvNikcname.setText(nickname);
            ((ItemViewHolder) holder).userview.setImageURI(Uri.parse(userview));


        } else if (holder instanceof FooterViewHolder)
        {
            pbFooterLoadMore = ((FooterViewHolder) holder).pbLoadMore;
            tvFooterNoMore = ((FooterViewHolder) holder).tvNoMore;
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position + 1 == getItemCount())
        {
            return TYPE_FOOTER;
        } else
        {
            return TYPE_ITEM;
        }
    }

    /**
     * item的holder
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder
    {

        private RelativeLayout llAll;
        private TextView tvUsername, tvNikcname;
        private SimpleDraweeView userview;


        public ItemViewHolder(View itemView)
        {
            super(itemView);

            llAll = (RelativeLayout) itemView.findViewById(R.id.ll_item_main_all);
            tvUsername = (TextView) itemView.findViewById(R.id.tv_item_main_username);
            tvNikcname = (TextView) itemView.findViewById(R.id.id_message_name_);
            userview = (SimpleDraweeView) itemView.findViewById(R.id.sdv_edit_userInfo_avatar_item);

        }
    }

    /**
     * footer的holder
     */
    public class FooterViewHolder extends RecyclerView.ViewHolder
    {
        private ProgressBar pbLoadMore;
        private TextView tvNoMore;

        public FooterViewHolder(View itemView)
        {
            super(itemView);
            pbLoadMore = (ProgressBar) itemView.findViewById(R.id.pb_footer_recycler);
            tvNoMore = (TextView) itemView.findViewById(R.id.tv_footer_no_more);
        }
    }

    /**
     * item的点击事件，点击跳转到聊天页面
     */
    private class UserItemClickListener implements View.OnClickListener
    {
        String userObjId;

        public UserItemClickListener(String objId)
        {
            userObjId = objId;
        }

        @Override
        public void onClick(View v)
        {
            Intent toChat = new Intent(mContext, ChatActivity.class);
            toChat.putExtra("fromUserObjId", userObjId);
            mContext.startActivity(toChat);
        }
    }


    /**
     * 显示加载更多的进度条
     */
    public void showLoadMore()
    {
        if (pbFooterLoadMore != null && tvFooterNoMore != null)
        {
            pbFooterLoadMore.setVisibility(View.VISIBLE);
            tvFooterNoMore.setVisibility(View.GONE);
        }
    }

    /**
     * 显示没有更多的文本
     */
    public void showNoMore()
    {
        if (pbFooterLoadMore != null && tvFooterNoMore != null)
        {
            pbFooterLoadMore.setVisibility(View.GONE);
            tvFooterNoMore.setVisibility(View.VISIBLE);
        }
    }

}




















