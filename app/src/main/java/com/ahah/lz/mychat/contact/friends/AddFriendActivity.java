package com.ahah.lz.mychat.contact.friends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.BaseActivity;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.model.AddFriend;
import com.ahah.lz.mychat.net.NetworkImpl;
import com.ahah.lz.mychat.widget.CircleImageView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AddFriendActivity extends BaseActivity {

    public String url = "https://api.jpush.cn/v3/push";
    public String tag = "ADDFRIEND";
    private CircleImageView uIcon;
    private TextView uName;
    private String uname , group;
    private int fid;
    private String iconUrl;
    private Spinner groupSpinner;
    private String HOST_ADDFRIEND = Global.HOST + Global.ADDFRIEND;
    private String TAG_ADDFRIEND = "TAG_ADDFRIEND";
    private SyncReference mAddFriendRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        Intent it = getIntent();
        Bundle bundle = it.getBundleExtra("data");
        uname = bundle.getString("fname");
        iconUrl = bundle.getString("iconUrl");
        fid = bundle.getInt("fid");

        mAddFriendRef = WilddogSync.getInstance().getReference().child("com_mychat_addfriend").child("addfriend"+fid);
        uIcon = (CircleImageView) findViewById(R.id.userIcon);
        uName = (TextView) findViewById(R.id.uName);
        groupSpinner = (Spinner) findViewById(R.id.groupSpinner);

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item , Global.fGroup);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        System.out.println("-----"+Global.fGroup.size());
        groupSpinner.setAdapter(arrayAdapter);
        uName.setText(uname);
        ImageLoad();
        iconfromNetwork(uIcon , iconUrl);

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                group = Global.fGroup.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Confirm(View view) {

        if (group == null) {
            Toast.makeText(this, "请输选择分组", Toast.LENGTH_SHORT).show();
        } else {
            RequestParams params = new RequestParams();
            params.put("platform", "all");

            JSONArray alias = new JSONArray();
            try {
                //      -----------------alias------------------------
                alias.put(uname);
                JSONObject json = new JSONObject();
                json.put("alias", alias);
                params.put("audience", json);
                //        params.put("audience" , "all");
                //        ------------------notification------------------------------
                JSONObject notification = new JSONObject();
                notification.put("alert", Global.Account.name+"想加你为好友");
                JSONObject notifAndroid = new JSONObject();
                notification.put("android", notifAndroid);
                params.put("notification", notification);

                //        -------------------message-----------------------------------

                JSONObject message = new JSONObject();
                message.put("msg_content", Global.Account.name+"想加你为好友");
                message.put("content_type", "text");
                message.put("title", "msg");
                JSONObject extras = new JSONObject();
                extras.put("name" , Global.Account.name);
                extras.put("id" , Global.Account.id);
                extras.put("iconUrl" , Global.Account.icon);
                message.put("extras" , extras);
                params.put("message", message);

                System.out.println("parms---" + params);
                NetworkImpl network = new NetworkImpl(this, this);
                network.addFriend(url, params, tag, NetworkImpl.Request.Post);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void Remove(View view){

        mAddFriendRef.removeValue();
    }

    //使用默认图片读取配置
    public void ImageLoad(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }

    private ImageLoadTool imageLoadTool = new ImageLoadTool();
    protected void iconfromNetwork(ImageView view, String url) {
        imageLoadTool.loadImage(view, url);
    }


    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

        System.out.println("AddFriendActivity---"+code+"--"+respanse+"--"+tag);
    }
}
