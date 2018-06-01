package com.example.toshiba.smarttv_0100.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.toshiba.smarttv_0100.LoginActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {

    Context context;

    ProgressDialog mProgressDialog;



    public Map parse_login_response(JSONObject response) throws Exception{
        Map map= new HashMap() ;
        JSONObject jsonObject=response.getJSONObject("login");
        JSONObject sponsor=jsonObject.getJSONObject("smart_tv_sponsor");
        String id=sponsor.getString("id");
        String video_url=sponsor.getString("video_url");
        String sponsor_frame_url=sponsor.getString("image_frame_url");
        map.put("sponsor_id",id);
        map.put("sponsor_video_url",video_url);
        map.put("sponsor_frame_url", sponsor_frame_url);
        return map;
    }



}
