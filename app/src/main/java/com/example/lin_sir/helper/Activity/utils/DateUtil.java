package com.example.lin_sir.helper.Activity.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lin_sir on 2016/2/16.
 */
public class DateUtil
{
    public static String longTimeToStr(long time) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        return sdf.format(new Date(time));
    }
}
