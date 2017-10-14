package com.ahah.lz.mychat.common;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.ahah.lz.mychat.model.UserObject;
import com.loopj.android.http.PersistentCookieStore;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.cookie.Cookie;

/**
 * Created by 40660 on 2017/8/1.
 */

public class Global {

    public static final String HOST =
//            "http://192.168.11.107/MyChat/";
            "http://192.168.1.10:70/MyChat/";        //学校
    public static final String HOST_ADDFRIEND = Global.HOST + Global.ADDFRIEND;
    public static final String LOGINTAG = "LOGINTAG";
    public static final String LOGIN = "LoginServlet";
    public static final String COOKIE_LOGIN ="cookielogin.jsp";
    public static final String MESSAGE = "MessageServlet";
    public static final String FRIENDS = "FriendsServlet";
//    public static final String CHATDATA = "ChatServlet";
//    public static final String CHATSOCKET = "ChatSocketServlet";
    public static final String SEARCHUSER = "UserServlet";
    public static final String ADDFRIEND = "AddFriendServlet";

    public static final String ACCPET = "accpet";
    public static final String REFUSE = "refuse";

    public static ArrayList<String> fGroup = new ArrayList<>();
    public static ArrayList<UserObject> fRequest = new ArrayList<>();
    public static UserObject Account = null;


    public static void syncCookie(Context context){

        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        List<Cookie> cookies = cookieStore.getCookies();
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        for (int i = 0 ; i < cookies.size() ; i++){
            Cookie eachCookie = cookies.get(i);
            cookieManager.setCookie(Global.HOST , String.format("username=%s;password=%s", eachCookie.getName() , eachCookie.getValue()));
        }
    }
}
