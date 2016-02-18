package com.example.lin_sir.helper.Activity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/7.
 */
public class ReceiveFragment extends Fragment
{

    private TabLayout mtablayout;
    private ViewPager mviewPager;
    private String[] mTitle = new String[]{"所有任务", "我的订单"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_receive, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        initViews();
    }

    private void initViews()
    {

        mtablayout = (TabLayout) getView().findViewById(R.id.tabLayout);
        mviewPager = (ViewPager) getView().findViewById(R.id.view_pager);


        mviewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {

                if (position == 1) return new MeOrders();

                return new AllTasks();
            }

            @Override
            public int getCount()
            {
                return mTitle.length;
            }

            @Override
            public CharSequence getPageTitle(int position)
            {
                return mTitle[position];
            }
        });
        mtablayout.setupWithViewPager(mviewPager);
    }
}




















