package com.example.lin_sir.helper.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lin_sir.helper.Activity.utils.TasksBean;
import com.example.lin_sir.helper.R;

import java.util.List;

/**
 * Created by lin_sir on 2016/2/14.
 */
public class TasksAdapter extends ArrayAdapter<TasksBean>
{


    public int resourceId;

    public TasksAdapter(Context context, int textVIewResourceID, List<TasksBean> objects)
    {

        super(context, textVIewResourceID, objects);
        resourceId = textVIewResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        TasksBean fruitBean = getItem(position);
        View view;
        ViewHolder1 viewHolder;


        if (convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder1();
            viewHolder.content= (TextView) view.findViewById(R.id.content_lv_item);
            viewHolder.title= (TextView) view.findViewById(R.id.title_lv_item);
            viewHolder.username= (TextView) view.findViewById(R.id.username_lv_item);


            view.setTag(viewHolder);


        } else
        {
            view = convertView;
            viewHolder = (ViewHolder1) view.getTag();
        }

        viewHolder.title.setText(fruitBean.getTitle());
        viewHolder.content.setText(fruitBean.getContent());
        viewHolder.username.setText(fruitBean.getNickname());



        return view;
    }


}
