package com.example.lin_sir.helper.Activity.utils;

import android.os.Environment;

/**
 * Created by lin_sir on 2016/2/13.
 */
public class FileUtil
{
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}
