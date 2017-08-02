package com.ahah.lz.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ahah.lz.mychat.net.NetworkCallback;
import com.ahah.lz.mychat.net.NetworkImpl;

import org.json.JSONException;
import org.json.JSONObject;

public class EntranceActivity extends AppCompatActivity implements NetworkCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        getNetwork("http://192.168.11.107/MyChat/login1.action" , "http://192.168.11.107/MyChat/login1.action");
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

    }

    @Override
    public void getNetwork(String url , String tag) {
        NetworkImpl networkImpl = new NetworkImpl(this , this);
        networkImpl.loadData(url , null , null , NetworkImpl.Request.Post);
    }

    public void enLogin(View v){
        Intent it = new Intent(EntranceActivity.this , LoginActivity.class);
        startActivity(it);
    }
}
