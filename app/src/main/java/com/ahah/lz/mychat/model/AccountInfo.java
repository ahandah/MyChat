package com.ahah.lz.mychat.model;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.ahah.lz.mychat.message.ChatRoom;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * Created by 40660 on 2017/7/18.
 */

public class AccountInfo {

    private static final String ACCOUNT = "ACCOUNT";
    private static final String CHATDATA = "CHATDATA";

    public static void saveAccount(Context cxt , UserObject userData){

        File file = new File(cxt.getFilesDir() , ACCOUNT);
        if (file.exists()){
            file.delete();
        }

        try{
            ObjectOutputStream oos = new ObjectOutputStream(cxt.openFileOutput(ACCOUNT , Context.MODE_PRIVATE));
            oos.writeObject(userData);
            oos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static UserObject loadAccount(Context cxt){

        UserObject user = null;
        File file = new File(cxt.getFilesDir() , ACCOUNT);
        if (file.exists()){
            try {
                ObjectInputStream ois = new ObjectInputStream(cxt.openFileInput(ACCOUNT));
                user = (UserObject) ois.readObject();
                ois.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (user == null){
            user = new UserObject();
        }

        return user;
    }

    public static void saveChatData(Context cxt , ArrayList<ChatModel> data){

        if (cxt == null){
            return;
        }

        File file = new File(cxt.getFilesDir() , CHATDATA);
        if (file.exists()){
            file.delete();
        }

        try{
            ObjectOutputStream oos = new ObjectOutputStream(cxt.openFileOutput(CHATDATA , Context.MODE_PRIVATE));
            oos.writeObject(data);
            oos.close();

        }catch (Exception e){
            System.out.println("写入失败--"+e);
            e.printStackTrace();
        }
    }

    public static ArrayList<ChatModel> loadChatData(Context cxt){

        ArrayList<ChatModel> chatData = null;
        File file = new File(cxt.getFilesDir() , CHATDATA);
        if (file.exists()){
            try {
                ObjectInputStream ois = new ObjectInputStream(cxt.openFileInput(CHATDATA));
                ChatRoom.getData = (ArrayList<ChatModel>) ois.readObject();
//                chatData = (ArrayList<ChatModel>) ois.readObject();
                ois.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (chatData == null) {
            chatData = new ArrayList<>();
        }

        return ChatRoom.getData;
    }

    public static void saveChat(){

    }

    public static void loginOut(Context ctx) {
        File dir = ctx.getFilesDir();
        String[] fileNameList = dir.list();
        for (String item : fileNameList) {
            File file = new File(dir, item);
            if (file.exists() && !file.isDirectory()) {
                file.delete();
            }
        }

//        AccountInfo.setNeedPush(ctx, true);

        NotificationManager notificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

//    public static void setNeedPush(Context ctx, boolean push) {
//        SharedPreferences sp = ctx.getSharedPreferences(FILE_PUSH, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putBoolean(KEY_NEED_PUSH, push);
//        editor.commit();
//    }

}
