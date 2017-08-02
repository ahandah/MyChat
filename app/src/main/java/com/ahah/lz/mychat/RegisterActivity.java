package com.ahah.lz.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ahah.lz.mychat.common.BaseActivity;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity {

    private EditText runame;
    private EditText rupwd;
    private EditText reupwd;
    private EditText ricon;
    private String rname , rpwd , repwd , icon , pwd;
    private String HOST_REGISTER = "http://192.168.11.107/MyChat/register1.action";
    private String TAG_REGITSTER = "TAG_REGISTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        runame = (EditText) findViewById(R.id.rname);
        rupwd = (EditText) findViewById(R.id.rpwd);
        reupwd = (EditText) findViewById(R.id.rrpwd);
        ricon = (EditText) findViewById(R.id.ricon);
    }

    public void register(View v){

        System.out.println("注册");
        rname = runame.getText().toString();
        rpwd = rupwd.getText().toString();
        repwd = reupwd.getText().toString();
        icon = ricon.getText().toString();
        if (rname.length() != 0){
            System.out.println("registerrrrrr---"+rpwd+repwd);
            if (rpwd.equals(repwd)){
                pwd = rpwd;
            }
//            Toast.makeText(RegisterActivity.this , "密码不能为空" , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this , "用户名不能为空" , Toast.LENGTH_SHORT).show();
        }

        if (icon.length() == 0){
            Toast.makeText(RegisterActivity.this , "图片不能为空" , Toast.LENGTH_SHORT).show();
        }

        RequestParams params = new RequestParams();
        params.put("username" , rname);
        params.put("password" , rpwd);
        params.put("icon" , icon);

        postNetwork(HOST_REGISTER , params , TAG_REGITSTER);

    }

    private void registerSuccess(JSONObject respanse){
        System.out.println("登入成功22---"+respanse);
    }

    public void back(View v){
//        Intent it = new Intent(RegisterActivity.this , LoginActivity.class);
//        startActivity(it);
        finish();
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

        if (tag.equals(TAG_REGITSTER)){
            if (code == 1){
                registerSuccess(respanse);
                System.out.println("登入成功1---"+respanse);
            }
        }
    }
}
