package com.ahah.lz.mychat.message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ahah.lz.mychat.MyAdapter;
import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.model.AccountInfo;
import com.ahah.lz.mychat.model.ChatModel;
import com.ahah.lz.mychat.model.UserObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ChatActivity extends AppCompatActivity {

    private EditText tv;
    private RecyclerView mRecyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleListView);
        tv = (EditText) findViewById(R.id.message);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        mRecyclerView.setLayoutManager(manager);
//        ChatRoom.getAddData();
//        AccountInfo.saveChatData(this , ChatRoom.getData);
//        ----已经写入数据到CHATDATA文件
        adapter = new MyAdapter(AccountInfo.loadChatData(this));
        mRecyclerView.setAdapter(adapter);
        ImageLoad();

    }
    //使用默认图片读取配置
    public void ImageLoad(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(ChatActivity.this);
        ImageLoader.getInstance().init(configuration);
    }

    public void send(View v){

        if (TextUtils.isEmpty(tv.getText())){
            Toast.makeText(this , "请输入发送信息！" , Toast.LENGTH_SHORT).show();
        }else {
            //发送格式 时间+用户名+内容
            UserObject user001 = new UserObject( 1 , "ahah" , "http://192.168.11.107/MyChat/icon/1.jpg" );
            ChatModel chatModel = new ChatModel();
            chatModel.chatAdd(tv.getText().toString() , user001);
            adapter.addData(chatModel);
            ChatRoom.getData.add(chatModel);
            AccountInfo.saveChatData(this , ChatRoom.getData);
            tv.setText("");
            mRecyclerView.smoothScrollToPosition(adapter.getItemCount()-1);

        }

    }


}
