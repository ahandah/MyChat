package com.ahah.lz.mychat.model;

import com.ahah.lz.mychat.common.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 当前属性 id 头像 名称
 * Created by 40660 on 2017/7/13.
 */

public class UserObject implements Serializable {

    public int id;
    public int fid;
    public int connect;
    public String icon;
    public String name;

    public UserObject(){

    }

    public UserObject(JSONObject response) throws JSONException{
    //临时先这样改，登入时验证true则使用第一个
        if(response.getString("loginTag").equals(Global.LOGINTAG)){
            System.out.println("UserObject---"+response);
            id = response.getInt("id");
            name = response.getString("name");
            icon = response.getString("icon");
        }else {
            System.out.println("UserObject---"+response);
            name = response.getString("fname");
            icon = response.getString("icon");
        }

    }

    public UserObject(int fid , int connect , String name , String icon ){
        this.connect = connect;
        this.fid = fid;
        this.name = name;
        this.icon = icon;
    }

    public UserObject(int id , String name , String icon ){
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

}
