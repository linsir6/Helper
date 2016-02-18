package com.example.lin_sir.helper.Activity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lin_sir.helper.R;

/**
 * Created by lin_sir on 2016/2/7.
 */
public class SendFragment extends Fragment
{



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_send, null);
        return view;


    }


}
