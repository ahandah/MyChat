package com.ahah.lz.mychat.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 40660 on 2017/8/11.
 */

public class Friends extends UserObject implements Serializable {

    public int friendTag = 1;
    public int fid = 0;
    public String frName;
//    public String name;
//    public String icon;
    public boolean isExpand = false;
//    public ArrayList<UserObject> friends = new ArrayList<>();
    public ArrayList<Friends> friends;

    public Friends(){

    }

    public Friends(String frName , ArrayList<Friends> friends) throws JSONException {

        this.frName = frName;
        this.friends = new ArrayList<>(friends);
    }

    public Friends(JSONObject response) throws JSONException {

        fid = response.getInt("fid");
        frName = response.getString("frgroup");
        name = response.getString("fname");
        icon = response.getString("icon");
    }

    public Friends(int i , String lastName , ArrayList<Friends> jsonFriends) {

        this.frName = lastName;
        this.friendTag = i;
        this.friends = new ArrayList<>(jsonFriends);
    }
}
