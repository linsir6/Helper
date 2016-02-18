package com.example.lin_sir.helper.Activity.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.avos.avospush.notification.NotificationCompat;
import com.example.lin_sir.helper.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by lin_sir on 2016/2/17.
 */
public class NotificationUtils {

    /**
     * tag list，用来标记是否应该展示 Notification
     * 比如已经在聊天页面了，实际就不应该再弹出 notification
     */
    private static List<String> notificationTagList = new LinkedList<String>();

    /**
     * 添加 tag 到 tag list，在 MessageHandler 弹出 notification 前会判断是否与此 tag 相等
     * 若相等，则不弹，反之，则弹出
     */
    public static void addTag(String tag) {
        if (!notificationTagList.contains(tag)) {
            notificationTagList.add(tag);
        }
    }

    /**
     * 在 tag list 中 remove 该 tag
     */
    public static void removeTag(String tag) {
        notificationTagList.remove(tag);
    }

    /**
     * 判断是否应该弹出 notification
     * 判断标准是该 tag 是否包含在 tag list 中
     */
    public static boolean isShowNotification(String tag) {
        return !notificationTagList.contains(tag);
    }

    /**
     * 显示通知，一般在MessageHandler中收到消息，然后传入Intent，发送通知，最后在NotificationBroadcastReceiver中对接收到的通知进行处理
     *
     * @param context 上下文
     * @param title   通知标题
     * @param content 通知内容
     * @param sound   通知提示音
     * @param intent  带有conversationId和fromUserObjId的Intent
     */
    public static void showNotification(Context context, String title, String content, String sound, Intent intent) {
        intent.setFlags(0);
        int notificationId = (new Random()).nextInt();
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.logo01)
                        .setContentTitle(title).setAutoCancel(true).setContentIntent(contentIntent)
                        .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                        .setContentText(content);
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = mBuilder.build();
        if (sound != null && sound.trim().length() > 0) {
            notification.sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + sound);
        }
        manager.notify(notificationId, notification);
    }
}

