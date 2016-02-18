package com.example.lin_sir.helper.Activity.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.lin_sir.helper.Activity.utils.DateUtil;
import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/16.
 */
public class RightItemChatHolder extends RecyclerView.ViewHolder {

    private Context mContext;

    private TextView tvTime;
    private TextView tvContent;


    public RightItemChatHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;

        tvTime = (TextView) itemView.findViewById(R.id.tv_chat_item_right_time);
        tvContent = (TextView) itemView.findViewById(R.id.tv_chat_item_right_content);
    }

    /**
     * 将数据绑定到视图中
     *
     * @param data Message实例
     */
    public void bindData(Object data) {
        AVIMMessage message = (AVIMMessage) data;

        String time = DateUtil.longTimeToStr(message.getTimestamp());

        String content = "暂不支持此类型消息";
        if (message instanceof AVIMTextMessage) {
            content = ((AVIMTextMessage) message).getText();
        }

        tvTime.setText(time);
        tvContent.setText(content);
    }

    /**
     * 显示时间，根据传入的值确定是否显示时间视图
     *
     * @param isShowTime boolean
     */
    public void showTime(boolean isShowTime) {
        tvTime.setVisibility(isShowTime ? View.VISIBLE : View.GONE);
    }

}
