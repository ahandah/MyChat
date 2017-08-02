package com.ahah.lz.mychat.net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 40660 on 2017/7/23.
 */

public interface NetworkCallback {
    void parseJson(int code , JSONObject respanse, String tag) throws JSONException;

    void getNetwork(String url , String tag);
}
