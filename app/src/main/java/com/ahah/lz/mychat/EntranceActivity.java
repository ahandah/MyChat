package com.ahah.lz.mychat;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.model.AccountInfo;
import com.ahah.lz.mychat.net.NetworkCallback;
import com.ahah.lz.mychat.net.NetworkImpl;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class EntranceActivity extends AppCompatActivity implements NetworkCallback{

    private static final String HOST_COOKIE_LOGIN = Global.HOST + Global.COOKIE_LOGIN;
    private static final String TAG_COOKIE_LOGIN = "CookieLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

//        //      初始化wilddog
//        WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("https://wd1769526484bgdoow.wilddogio.com/").build();
//        WilddogApp.initializeApp(this, options);
        getNetwork(HOST_COOKIE_LOGIN , TAG_COOKIE_LOGIN);
    }


    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {
        System.out.println("entrance--"+code+respanse+tag);

        if (code == 1){
            if (tag.equals("CookieLogin")){
                Global.Account = AccountInfo.loadAccount(this);
                System.out.println("entrance-----"+Global.Account.name);
                System.out.println("Cookie--login-success");
                Intent it = new Intent(EntranceActivity.this , MainActivity.class);
                startActivity(it);
            }
        }else if (code == 4){
            Intent it = new Intent(EntranceActivity.this , LoginActivity.class);
            startActivity(it);
        }
    }

    @Override
    public void getNetwork(String url , String tag) {
        NetworkImpl networkImpl = new NetworkImpl(this , this);
        networkImpl.loadData(url , null , tag , NetworkImpl.Request.Post);
    }

    public void enLogin(View v){
        Intent it = new Intent(EntranceActivity.this , LoginActivity.class);
        startActivity(it);
    }
}
