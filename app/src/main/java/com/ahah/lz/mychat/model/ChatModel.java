package com.ahah.lz.mychat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 以后添加emji表情、图片、文件
 * Socket
 * Created by 40660 on 2017/7/13.
 */

public class ChatModel implements Serializable{

    public String content;
    public int time;
    public UserObject user;

    public void chatAdd(String content , int time , UserObject user) {

        this.content = content;
        this.time = time;
        this.user = user;

    }

    public void chatAdd(String content , UserObject user) {

        this.content = content;
        this.user = user;

    }

}
