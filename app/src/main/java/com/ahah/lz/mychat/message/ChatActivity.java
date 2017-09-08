package com.ahah.lz.mychat.message;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahah.lz.mychat.MyAdapter;
import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.BaseActivity;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.model.AccountInfo;
import com.ahah.lz.mychat.model.ChatModel;
import com.ahah.lz.mychat.model.Friends;
import com.ahah.lz.mychat.model.UserObject;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
    private RecyclerView mRecyclerView;
    private MyAdapter adapter;
    private String HOST_CHATDATA = Global.HOST + Global.CHATDATA;
    private String HOST_CHATSOCKET = Global.HOST + Global.CHATSOCKET;
    private String TAG_CHATDATA = "TAG_CHATDATA";
    private UserObject friend ;
    private int port;
    private ArrayList<ChatModel> chatData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//      获取从FriendsFragment传来的好友类
        Intent it = getIntent();
        friend = (UserObject) it.getSerializableExtra("friend");
        port = friend.fid*Global.Account.id;

        getChatData();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleListView);
        tv = (EditText) findViewById(R.id.message);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        mRecyclerView.setLayoutManager(manager);
//        ChatRoom.getAddData();
//        AccountInfo.saveChatData(this , ChatRoom.getData);
//        ----已经写入数据到CHATDATA文件
//        adapter = new MyAdapter(AccountInfo.loadChatData(this));
        adapter = new MyAdapter(chatData);
        System.out.println("onCreat-----"+Global.Account.name+Global.Account.icon);
        mRecyclerView.setAdapter(adapter);
    }

    public void getChatData(){

        System.out.println(HOST_CHATDATA);
        getNetwork(HOST_CHATDATA , TAG_CHATDATA);
        RequestParams params = new RequestParams();
        System.out.println("-----port-----"+port);
        params.put("port" , port);
        postNetwork(HOST_CHATSOCKET ,params , TAG_CHATDATA);

    }

    //使用默认图片读取配置
    public void ImageLoad(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(ChatActivity.this);
        ImageLoader.getInstance().init(configuration);
    }

    public void send(View v){

//        if (TextUtils.isEmpty(tv.getText())){
//            Toast.makeText(this , "请输入发送信息！" , Toast.LENGTH_SHORT).show();
//        }else {
//            //发送格式 时间+用户名+内容
//            ChatModel chatModel = new ChatModel();
//            chatModel.chatAdd(tv.getText().toString() , Global.Account);
//            adapter.addData(chatModel);
//            ChatRoom.getData.add(chatModel);
//            AccountInfo.saveChatData(this , ChatRoom.getData);
//            tv.setText("");
//            mRecyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
//
//        }

        new MyThread(tv.getText().toString()).start();


    }

    public Socket socket;
    public String buffer;

    class MyThread extends Thread {

        public String txt1;

        public MyThread(String str) {
            txt1 = str;
        }

        @Override
        public void run() {
            //定义消息
            Message msg = new Message();
            msg.what = 0x11;
            Bundle bundle = new Bundle();
            bundle.clear();
            try {
                //连接服务器 并设置连接超时为5秒
                socket = new Socket("192.168.1.8" , 11220+port);
                System.out.println("connected---"+socket.isConnected());
                OutputStream ou = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(ou);
                pw.write(txt1);
                pw.flush();
                socket.shutdownOutput();
                InputStream in = socket.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(in));
//                System.out.println("server----1--"+bf.read());
                String reply=null;
//                while(!((reply=bf.readLine())==null)){
//                    System.out.println("接收服务器的信息："+reply);
//                }
//                System.out.println(reply==null);
                System.out.println("--server----"+bf.readLine());
                //关闭各种输入输出流
                in.close();
                bf.close();
                ou.close();
                socket.close();
            } catch (SocketTimeoutException aa) {
                //连接超时 在UI界面显示消息
                bundle.putString("msg", "服务器连接失败！请检查网络是否打开");
                msg.setData(bundle);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {
        System.out.println("chatActivity----"+code+"---"+respanse+"---"+tag);
        JSONArray jsonArray = respanse.getJSONArray("data");
        JSONObject jsonObject;
        adapter.mData.clear();
        for (int i = 0 ; i < jsonArray.length() ; i++){

            jsonObject = jsonArray.getJSONObject(i);
            String sender = jsonObject.getString("sender");
            String message = jsonObject.getString("message");
            String time = jsonObject.getString("msgtime");
            ChatModel chatModel = new ChatModel();
            if (sender.equals(Global.Account.name)){
                chatModel.chatAdd(message , Global.Account);
                adapter.mData.add(chatModel);
            }else if (sender.equals(friend.name)){
                chatModel.chatAdd(message , friend);
                adapter.mData.add(chatModel);
            }
        }


        adapter.notifyDataSetChanged();

        System.out.println("-----回调------"+adapter.mData.size()+Global.Account.name);

    }
}
