package com.example.toshiba.smarttv_0100.Model.api;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.toshiba.smarttv_0100.Model.Path;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.Headers;
import okhttp3.RequestBody;

public class ServiceManager {

    private String URL_LOGIN_ADMIN="http://200.16.7.150:8083/api/v1/smart_tv_users/login";
    private String URL_LOGIN_CLIENT="http://200.16.7.150:8083/api/v1/smart_tv_users/login";

    private Context mContext;
    private static  ServiceManager instance = new ServiceManager();

    private ServiceManager() {}

    public static ServiceManager getInstance(Context context) {
        if(instance.mContext == null) {
            instance.mContext = context;
            AndroidNetworking.initialize(context);

        }
        return instance;
    }

public void login(Integer username, String password, JSONObjectRequestListener listener) {

    JSONObject jsonObject= new JSONObject();
    try {
        jsonObject.put("user_tv", username);
        jsonObject.put("password_tv", password);
    } catch (JSONException e) {
        e.printStackTrace();
    }


    AndroidNetworking.post(URL_LOGIN_ADMIN)
            .addJSONObjectBody(jsonObject)
            .addHeaders("Content-Type","application/json")
            .addHeaders("Accept","application/json")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(listener);
}

    public void login_client(File file, JSONObjectRequestListener listener) {

        AndroidNetworking.upload(URL_LOGIN_CLIENT)
                .addMultipartFile("img",file)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(listener);
    }
}