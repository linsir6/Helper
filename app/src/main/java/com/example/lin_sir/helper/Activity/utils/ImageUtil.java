package com.example.lin_sir.helper.Activity.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.io.ByteArrayOutputStream;

/**
 * Created by lin_sir on 2016/2/13.
 */
public class ImageUtil
{

    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    /**
     * 将图片处理成圆形
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int r;

        if (width > height) {
            r = height;
        } else {
            r = width;
        }
        Bitmap backgroundBmp = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBmp);
        Paint paint = new Paint();
        // 设置边缘光滑，去掉锯齿
        paint.setAntiAlias(true);
        // 宽高相等，即正方形
        RectF rect = new RectF(0, 0, r, r);
        // 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        // 且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, paint);
        // 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉

        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, paint);

        return backgroundBmp;
    }
}

