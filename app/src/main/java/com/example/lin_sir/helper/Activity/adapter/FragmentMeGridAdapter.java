package com.example.lin_sir.helper.Activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/9.
 */
public class FragmentMeGridAdapter extends BaseAdapter
{

    private Context context;
    private String[] names;
    private static int[] icons = {
            R.mipmap.fragment_me_grid1, R.mipmap.fragment_me_grid2,
            R.mipmap.fragment_me_grid3, R.mipmap.fragment_me_grid4,
            R.mipmap.fragment_me_grid5,


    };

    public FragmentMeGridAdapter(Context context)
    {
        this.context = context;
        this.names = context.getResources().getStringArray(R.array.fr_me_grid);
    }


    @Override
    public int getCount()
    {
        return icons.length;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = View.inflate(context, R.layout.grideview_item_meadapter, null);
        ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
        TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
        iv_item.setImageResource(icons[position]);
        tv_item.setText(names[position]);
        return view;
    }


}
