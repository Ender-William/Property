package com.kkpip2022.property.api;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {
    private static OkHttpClient client;
    private static String requestUrl;
    private static HashMap<String, Object> mParams;
    public static Api api = new Api();

    public Api(){

    }

    public static Api config(String BaseUrl, String ServerPort, String API, String ExtURL, HashMap<String, Object> params) {
        client = new OkHttpClient.Builder().build();
        requestUrl = BaseUrl + ":" + ServerPort + API + ExtURL;
        mParams = params;
        return api;
    }

    public void postRequest(TtitCallback callback) {
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson =
                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
                        ,jsonStr);
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("contentType","application/json;charset=UTF-8")
                .post(requestBodyJson)
                .build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailuer",e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e("onResponse", "");
                callback.onSuccess(result);
            }
        });
    }

}
