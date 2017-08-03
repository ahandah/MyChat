package com.ahah.lz.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.message.ChatActivity;
import com.ahah.lz.mychat.common.BaseActivity;
import com.ahah.lz.mychat.model.UserObject;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private EditText euname;
    private EditText eupwd;
    private String uname , upwd;
    private String HOST_LOGIN =
            Global.HOST + Global.LOGIN;
//            "http://192.168.11.107/MyChat/LoginServlet";
//            "http://192.168.11.107/MyChat/login1.action";
//            "http://192.168.11.107/MyChat/login.jsp";
    private String TAG_LOGIN = "TAG_LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        euname = (EditText) findViewById(R.id.uname);
        eupwd = (EditText) findViewById(R.id.upwd);
    }

    public void login(View v){
        uname = euname.getText().toString();
        upwd = eupwd.getText().toString();
        RequestParams params = new RequestParams();
        params.put("username" , uname);
        params.put("password" , upwd);
        postNetwork(HOST_LOGIN , params , TAG_LOGIN);
    }

//    private void postNetwork(String url , RequestParams params , final String tag) {
//
//        NetworkImpl network = new NetworkImpl(this , this);
//        network.loadData( url , params , tag , NetworkImpl.Request.Post);
//    }

    private void loginSuccess(JSONObject response) throws JSONException{

        UserObject user = new UserObject(response.getJSONObject("data"));

        Intent it = new Intent(LoginActivity.this , ChatActivity.class);
        startActivity(it);
    }

    @Override
    public void parseJson(int code , JSONObject respanse, String tag) throws JSONException {


        if (tag.equals(TAG_LOGIN)){
            if (code == 1){

                loginSuccess(respanse);
                System.out.println("登入成功---"+respanse);
            }
        }

    }



    //进入注册页面
    public void onRegister(View v){
        Intent it = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(it);
    }


}


