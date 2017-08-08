package com.ahah.lz.mychat.common;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahah.lz.mychat.net.NetworkCallback;
import com.ahah.lz.mychat.net.NetworkImpl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 40660 on 2017/8/4.
 */

public class BaseFragment extends Fragment implements NetworkCallback {

    LayoutInflater layoutInflater;
    NetworkImpl networkImpl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutInflater = LayoutInflater.from(getActivity());
        networkImpl = new NetworkImpl(layoutInflater.getContext() , this);
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag) throws JSONException {

    }

    @Override
    public void getNetwork(String url, String tag) {
        System.out.println("BaseFragment--getNetwork"+url);
        networkImpl.loadData( url , null , tag , NetworkImpl.Request.Get);
    }
}
