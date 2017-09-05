package com.ahah.lz.mychat;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ahah.lz.mychat.contact.ContactFragment;
import com.ahah.lz.mychat.message.MessageFragment;
import com.ahah.lz.mychat.news.NewsFragment;
import com.ahah.lz.mychat.widget.CircleImageView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

//    private CircleImageView uIcon;
    private TextView toolBarText;
    private BottomBar bottomBar;
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
}
