package com.ahah.lz.mychat.contact.friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.BaseActivity;
import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.common.ImageLoadTool;
import com.ahah.lz.mychat.widget.CircleImageView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchFriendsActivity extends BaseActivity {

    private EditText FName;
    private TextView friendName , notFind;
    private CircleImageView userIcon;
    private LinearLayout userInfo;
    private String fName , fIcon ;
    private int fid;
    private String HOST_SEARCH_FRIENDS = Global.HOST + Global.SEARCHUSER;
    private String TAG_SEARCH_FRIEDNS = "TAG_SEARCH_FRIEDNS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);

        FName = (EditText) findViewById(R.id.FName);
        friendName = (TextView) findViewById(R.id.friendName);
        userIcon = (CircleImageView) findViewById(R.id.userIcon);
        notFind = (TextView) findViewById(R.id.notFind);
        userInfo = (LinearLayout) findViewById(R.id.userInfo);
        ImageLoad();

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

    public void Search(View view){

        userInfo.setVisibility(View.GONE);
        notFind.setVisibility(View.GONE);
        fName = FName.getText().toString();
        RequestParams params = new RequestParams();
        params.put("fName" , fName);
        postNetwork(HOST_SEARCH_FRIENDS , params , TAG_SEARCH_FRIEDNS);
    }

    public void addFriend(View view){
        Intent it = new Intent(this , AddFriendActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fname" , fName);
        bundle.putString("iconUrl" , Global.HOST+"icon/"+fIcon);
        bundle.putInt("fid" , fid);
        it.putExtra("data" , bundle);
        startActivity(it);
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

        System.out.println("---"+code+"---"+respanse+"---"+tag);
        if (code == 2){
            if (TAG_SEARCH_FRIEDNS.equals(tag)){

                userInfo.setVisibility(View.VISIBLE);

                JSONArray jsonArray = respanse.getJSONArray("data");
                for (int i = 0 ; i < jsonArray.length() ; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    friendName.setText(jsonObject.getString("uname"));
                    fIcon = jsonObject.getString("icon");
                    fid = jsonObject.getInt("uid");
                    System.out.println("--------"+fIcon);
                    iconfromNetwork(userIcon , Global.HOST+"icon/"+fIcon);
                }

            }
        }else {
            notFind.setVisibility(View.VISIBLE);
        }

    }
}
