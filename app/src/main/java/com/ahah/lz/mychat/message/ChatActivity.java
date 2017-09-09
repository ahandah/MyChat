package com.ahah.lz.mychat.message;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.ahah.lz.mychat.MyAdapter;
import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.WildDogChat.Chat;
import com.ahah.lz.mychat.WildDogChat.ChatListAdapter;
import com.ahah.lz.mychat.common.BaseActivity;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.model.ChatModel;
import com.ahah.lz.mychat.model.UserObject;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ChatActivity extends BaseActivity {

    private EditText tv;
//    private RecyclerView mRecyclerView;
    private ChatListAdapter adapter;
//    private String HOST_CHATDATA = Global.HOST + Global.CHATDATA;
//    private String HOST_CHATSOCKET = Global.HOST + Global.CHATSOCKET;
//    private String TAG_CHATDATA = "TAG_CHATDATA";
    private UserObject friend ;

    private SyncReference mWilddogRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//      初始化wilddog
        WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("https://wd1769526484bgdoow.wilddogio.com/").build();
        WilddogApp.initializeApp(this, options);
//        SyncReference mWilddogRef = WilddogSync.getInstance().getReference(WILDDOG_URL).child("chat");
        // Setup our Wilddog mWilddogRef
        mWilddogRef = WilddogSync.getInstance().getReference().child("chat");

//      获取从FriendsFragment传来的好友类
        Intent it = getIntent();
        friend = (UserObject) it.getSerializableExtra("friend");
        tv = (EditText) findViewById(R.id.message);

//        mRecyclerView = (RecyclerView) findViewById(R.id.recycleListView);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
//        mRecyclerView.setLayoutManager(manager);
        adapter = new ChatListAdapter(mWilddogRef.limitToLast(50) , this , Global.Account.name);
        System.out.println("onCreat-----"+Global.Account.name+Global.Account.icon);
//        mRecyclerView.setAdapter(adapter);
        final ListView listView = (ListView) findViewById(R.id.ChatList);
        listView.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(adapter.getCount()-1);
            }
        });

    }

    public void send(View v){

        EditText inputText = (EditText) findViewById(R.id.message);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, Global.Account.name);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mWilddogRef.push().setValue(chat);
            inputText.setText("");
        }
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {
        System.out.println("chatActivity----"+code+"---"+respanse+"---"+tag);

    }
}
