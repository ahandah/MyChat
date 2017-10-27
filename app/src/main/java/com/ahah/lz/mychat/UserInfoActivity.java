package com.ahah.lz.mychat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ahah.lz.mychat.common.Global;
import com.ahah.lz.mychat.net.MyAsyncHttpClient;
import com.ahah.lz.mychat.net.NetworkCallback;
import com.ahah.lz.mychat.net.NetworkImpl;
import com.ahah.lz.mychat.user.CameraPhotoUtil;
import com.ahah.lz.mychat.user.FileUtil;
import com.ahah.lz.mychat.user.StartActivity;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

public class UserInfoActivity extends AppCompatActivity implements StartActivity, NetworkCallback {

    private final int RESULT_REQUEST_PHOTO = 1005;
    private final int RESULT_REQUEST_PHOTO_CROP = 1006;
    public String HOST_USER_AVATAR = Global.HOST + "SimpleFileuploadServlet";
    private Uri fileCropUri;
    private Uri fileUri;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_REQUEST_PHOTO);
            }
        });

    }

    public void loginOut(View v){
        MyAsyncHttpClient.clearCookie();//删除本地cookie
        //关闭任务栏推送服务
        NotificationManager notificationManager = (NotificationManager)
                UserInfoActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        Intent it = new Intent(UserInfoActivity.this , LoginActivity.class);
        startActivity(it);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_REQUEST_PHOTO) {
            if (data != null) {
                fileUri = data.getData();
            }

            System.out.println("fileUri------"+fileUri);
            fileCropUri = CameraPhotoUtil.getOutputMediaFileUri();
            System.out.println("fileCropUri-----"+fileCropUri);
            Global.cropImageUri(this, fileUri, fileCropUri, 640, 640, RESULT_REQUEST_PHOTO_CROP);
        }else if (requestCode == RESULT_REQUEST_PHOTO_CROP) {
            String filePath = FileUtil.getPath(this, fileCropUri);
            System.out.println("filePath----"+filePath);
//            System.out.println("file--exit--"+file.exists());

            try {
                RequestParams params = new RequestParams();
                params.put("fileupload", new File(filePath));
                params.put("uid" , Global.Account.id);
                NetworkImpl network = new NetworkImpl(this , this);
                network.loadData(HOST_USER_AVATAR, params , null , NetworkImpl.Request.Post);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

    }

    @Override
    public void getNetwork(String url, String tag) {

    }
}
