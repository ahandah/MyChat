package com.ahah.lz.mychat.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.ahah.lz.mychat.net.NetworkCallback;
import com.ahah.lz.mychat.net.NetworkImpl;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 40660 on 2017/7/28.
 */

public class BaseActivity extends AppCompatActivity implements NetworkCallback {

    LayoutInflater layoutInflater;
    NetworkImpl networkImpl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutInflater = getLayoutInflater();
        networkImpl = new NetworkImpl(this , this);

    }

    public void postNetwork(String url, RequestParams params, final String tag) {
        networkImpl.loadData( url , params , tag , NetworkImpl.Request.Post);
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

    }

    @Override
    public void getNetwork(String url, String tag) {
        networkImpl.loadData( url , null , tag , NetworkImpl.Request.Get);
    }


}
