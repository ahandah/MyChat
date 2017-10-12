package com.ahah.lz.mychat;

import android.app.Application;

import com.ahah.lz.mychat.common.Global;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 40660 on 2017/10/11.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
//        Set<String> set = new HashSet<>();
//        set.add("a1a1a1");//名字任意，可多添加几个
//        JPushInterface.setTags(this, set, null);//设置标签
//        JPushInterface.setAlias(this , Global.Account.id , Global.Account.name);
    }
}
