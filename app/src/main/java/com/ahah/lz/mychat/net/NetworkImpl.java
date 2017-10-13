package com.ahah.lz.mychat.net;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;

/**
 * Created by 40660 on 2017/7/23.
 */

public class NetworkImpl {

    private NetworkCallback callback;
    private Context context;

    public NetworkImpl(Context context , NetworkCallback callback){
        this.context = context;
        this.callback = callback;
    }

    public void connectedSocket(String url , Request type){
        AsyncHttpClient client = MyAsyncHttpClient.createClient(context);
        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("onFailure--respon--"+responseString);
                System.out.println("onFailure--statusCode--"+statusCode);
                System.out.println("onFailure--headers--"+headers);
                System.out.println("onFailure--throwable--"+throwable);
            }


        };
    }

    public void loadData(String url , RequestParams params , final String tag , Request type){

        AsyncHttpClient client = MyAsyncHttpClient.createClient(context);

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                PersistentCookieStore persistentCookieStore = new PersistentCookieStore(context);
                List<Cookie> cookies = persistentCookieStore.getCookies();

                for (Cookie cookie : cookies){
                    System.out.println("test-cookie-"+cookie.getName()+cookie.getValue());
                }

                System.out.println("test-code"+statusCode);
                System.out.println("test-code"+response);

                try {
                    int code = response.getInt("code");
                    callback.parseJson(code , response , tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("onFailure--respon--"+responseString);
                System.out.println("onFailure--statusCode--"+statusCode);
                System.out.println("onFailure--headers--"+headers);
                System.out.println("onFailure--throwable--"+throwable);
            }


        };

        switch (type){
            case Get:
                System.out.println("--get-"+url);
                client.post(url , jsonHttpResponseHandler);
                break;
            case Post:
                System.out.println("--post-"+url);
                client.post(url , params , jsonHttpResponseHandler);
                break;
            case Put:
                break;
            case Delete:
                break;
        }
    }


    public void addFriend(String url , RequestParams params , final String tag , Request type){

        AsyncHttpClient client = MyAsyncHttpClient.createClient(context);
        client.addHeader("Authorization" , "Basic  NWYxMjNkZDg4YjRlYmI0MDg3MWFkNzU0OmE2ZWQ3MGRhOThjNzU2NmRiMzQxOWMzMg==");
//        client.addHeader("Host" , "api.jpush.cn");
//        client.addHeader("Content-Length" , "210");

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                System.out.println("test-code"+statusCode);
                System.out.println("test-code"+response);
                System.out.println("test-header"+headers);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("onFailure--respon--"+responseString);
                System.out.println("onFailure--statusCode--"+statusCode);
                System.out.println("onFailure--headers--"+headers);
                System.out.println("onFailure--throwable--"+throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("onFailure--jsonObject--"+errorResponse);
                System.out.println("onFailure--statusCode--"+statusCode);
                System.out.println("onFailure--headers--"+headers);
                System.out.println("onFailure--throwable--"+throwable);
            }
        };

        switch (type){
            case Get:
                System.out.println("--get-"+url);
                client.post(url , jsonHttpResponseHandler);
                break;
            case Post:
                System.out.println("--post-"+url);
                client.post(url , params , jsonHttpResponseHandler);
                break;
            case Put:
                break;
            case Delete:
                break;
        }
    }


    public enum Request{
        Get , Post , Put , Delete
    }

}
