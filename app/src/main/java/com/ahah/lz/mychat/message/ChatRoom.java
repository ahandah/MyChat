package com.ahah.lz.mychat.message;

import com.ahah.lz.mychat.model.ChatModel;
import com.ahah.lz.mychat.model.UserObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 40660 on 2017/7/12.
 */

public class ChatRoom implements Serializable{

    public static ArrayList<ChatModel> getData = new ArrayList<ChatModel>();

    public static ArrayList<ChatModel> getAddData(){

        UserObject user001 = new UserObject( 1 , "ahah" , "http://192.168.11.107/MyChat/icon/1.jpg" );
        UserObject user002 = new UserObject( 2 , "shsh" , "http://192.168.11.107/MyChat/icon/2.jpg" );
        ChatModel chatModel1 = new ChatModel();
        chatModel1.chatAdd("测试己方第一条" , 1526 , user001);
        getData.add(chatModel1);
        ChatModel chatModel2 = new ChatModel();
        chatModel2.chatAdd("测试对方第一条" , 1530 , user002);
        getData.add(chatModel2);
        return getData;
    }

    public static ArrayList<ChatModel> sendMessage(ChatModel chatData){

        getData.add(chatData);
        return getData;
    }

}
