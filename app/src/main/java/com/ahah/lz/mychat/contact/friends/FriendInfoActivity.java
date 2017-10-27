package com.ahah.lz.mychat.contact.friends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.BaseActivity;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.message.ChatActivity;
import com.ahah.lz.mychat.model.UserObject;
import com.ahah.lz.mychat.widget.CircleImageView;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendInfoActivity extends BaseActivity {

    private UserObject friend ;
    private CircleImageView icon;
    private TextView name;
    private String HOST_DEL_FRIEND = Global.HOST + "DelFriendServlet";
    private String TAG_DEL_FRIEND = "TAG_DEL_FRIEND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        //      获取从FriendsFragment传来的好友类
        Intent it = getIntent();
        friend = (UserObject) it.getSerializableExtra("friend");
        name = (TextView) findViewById(R.id.name);
        icon = (CircleImageView) findViewById(R.id.icon);
        iconfromNetwork(icon , Global.HOST+"icon/"+friend.icon);
        name.setText(friend.name);

    }

    //获得用户头像图面
    private ImageLoadTool imageLoadTool = new ImageLoadTool();
    protected void iconfromNetwork(ImageView view, String url) {
        imageLoadTool.loadImage(view, url);
    }

    public void delFriend(View view){

        RequestParams params = new RequestParams();
        params.put("uname" , Global.Account.name);
        params.put("fname" , friend.name);

        postNetwork(HOST_DEL_FRIEND , params , TAG_DEL_FRIEND);

    }

    public void chat(View view){

        Intent itChat = new Intent(FriendInfoActivity.this , ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("friend" , friend);
        itChat.putExtras(bundle);
        startActivity(itChat);
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {
        if (code == 2){
            Toast.makeText(this , respanse.getString("data") , Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}
