package com.example.lin_sir.helper.Activity.utils;

import com.avos.avoscloud.AVObject;

/**
 * Created by lin_sir on 2016/2/14.
 */
public class BeanUtil
{
    public static AVObject statebean2Obj(TasksBean bean)
    {
//
        AVObject obj = new AVObject("linlinlinlinlin");
//        obj.put("fa_bu_ren", bean.getFa_bu_ren());
//        obj.put("kai_shi_shi_jian", bean.getKai_shi_shi_jian());
//        obj.put("ren_wu_nei_rong", bean.getRen_wu_nei_rong());
//        obj.put("shou_ji_hao", bean.getShou_ji_hao());
//        obj.put("zhou_qi", bean.getZhou_qi());
//
        return obj;
    }

    public static TasksBean obj2statebean(AVObject obj)
    {

        TasksBean bean = new TasksBean();


        bean.setNickname(obj.getString("userphone"));
        bean.setContent(obj.getString("content"));
        bean.setTitle(obj.getString("title"));


        return bean;
    }
}
