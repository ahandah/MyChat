package com.ahah.lz.mychat.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

/**
 * Created by 40660 on 2017/7/23.
 */

public class MyAsyncHttpClient {

    public static AsyncHttpClient createClient(Context context){
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore persistentCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(persistentCookieStore);
        client.setTimeout(30*1000);
        return client;
    }

//    public static AsyncHttpClient socketClient(Context context){
//
//        AsyncHttpClient client = new AsyncHttpClient
//        return
//    }

}
