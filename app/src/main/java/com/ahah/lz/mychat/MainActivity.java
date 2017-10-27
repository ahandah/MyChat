package com.ahah.lz.mychat;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity {

//    private CircleImageView uIcon;
    private TextView toolBarText;
    private BottomBar bottomBar;
    private String HOST_GROUP = Global.HOST + "GroupServlet";
    private String TAG_GROUP = "TAG_GROUP";

    // 要申请的权限
    private String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            , android.Manifest.permission.READ_EXTERNAL_STORAGE
            , android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS};
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
//                startRequestPermission();
            }
        }

        toolBarText = (TextView) findViewById(R.id.toolBatText);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                onItemSelected(tabId);

            }
        });

        JPushInterface.setAlias(this , Global.Account.id , Global.Account.name);
        getNetwork(HOST_GROUP , TAG_GROUP);

    }


    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("由于MyChat需要获取存储空间，为你存储个人信息；\n否则，您将无法正常使用MyChat")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许MyChat使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
        Intent it = new Intent(this , UserInfoActivity.class);
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
