package com.ahah.lz.mychat.model;

/**
 * Created by 40660 on 2017/9/19.
 */

public class AddFriend {


    private String account;
    private String friend;
    private String isAdd;

    // Required default constructor for Wilddog object mapping
    @SuppressWarnings("unused")
    private AddFriend() {
    }

    public AddFriend(String account, String friend , String isAdd) {
        this.account = account;
        this.friend = friend;
        this.isAdd = isAdd;
    }

    public String getAccount() {
        return account;
    }

    public String getFriend() {
        return friend;
    }

    public String getisAdd() {
        return isAdd;
    }
}
