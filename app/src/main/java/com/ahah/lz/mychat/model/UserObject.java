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
    public String icon;
    public String name;

    public UserObject(){

    }

    public UserObject(JSONObject response) throws JSONException{
    //临时先这样改，登入时验证true则使用第一个
        if(response.getString("loginTag").equals(Global.LOGIN)){
            System.out.println("UserObject---"+response);
            name = response.getString("name");
        }else {
            System.out.println("UserObject---"+response);
            name = response.getString("fname");
            icon = response.getString("icon");
        }

    }

    public UserObject(int id ,String name , String icon ){
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

}
