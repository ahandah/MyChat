package com.ahah.lz.mychat;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ahah.lz.mychat.common.BaseActivity;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.contact.ContactFragment;
import com.ahah.lz.mychat.contact.friends.SearchFriendsActivity;
import com.ahah.lz.mychat.message.MessageFragment;
import com.ahah.lz.mychat.news.NewsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {

//    private CircleImageView uIcon;
    private TextView toolBarText;
    private BottomBar bottomBar;
    private String HOST_GROUP = Global.HOST + "GroupServlet";
    private String TAG_GROUP = "TAG_GROUP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBarText = (TextView) findViewById(R.id.toolBatText);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                onItemSelected(tabId);

            }
        });

        getNetwork(HOST_GROUP , TAG_GROUP);

    }

    private void onItemSelected(int i){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MessageFragment messageFragment = null;
        ContactFragment contactFragment = null;
        NewsFragment newsFragment = null;

        switch (i){
            case R.id.message:
                if (messageFragment == null){
                    messageFragment = new MessageFragment();
                }
                System.out.println("消息");
                toolBarText.setText("消息");
                fragmentTransaction.replace(R.id.context , messageFragment);
                break;
            case R.id.contact:
                if (contactFragment == null){
                    contactFragment = new ContactFragment();
                }
                System.out.println("联系人");
                toolBarText.setText("联系人");
                fragmentTransaction.replace(R.id.context , contactFragment);
                break;
            case R.id.news:
                if (newsFragment == null){
                    newsFragment = new NewsFragment();
                }
                System.out.println("动态");
                toolBarText.setText("动态");
                fragmentTransaction.replace(R.id.context , newsFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    public void uIcon(View v){
        Toast.makeText(MainActivity.this , "打开侧边菜单" , Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this , LoginActivity.class);
        startActivity(it);
    }

    public void Info(View v){
        Toast.makeText(MainActivity.this , "打开侧边菜单" , Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this , SearchFriendsActivity.class);
        startActivity(it);
    }


    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {
        System.out.println("MainActivity----"+code+"--"+respanse+"--"+tag);
        if (code == 2){
            if (tag.equals(TAG_GROUP)){
                JSONArray jsonArray = respanse.getJSONArray("data");
                for (int i = 0 ; i < jsonArray.length() ; i ++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Global.fGroup.add(jsonObject.getString("frgroup"));
                }
            }
        }
    }
}
