package com.example.toshiba.smarttv_0100;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import com.example.toshiba.smarttv_0100.Model.Path;

public class SponsorVideoActivity extends AppCompatActivity {
    String RESOURCES_PATH = Environment.getExternalStorageDirectory().toString();
    VideoView video_iddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_video);
        video_iddle= (VideoView) findViewById(R.id.videoView);
        video_iddle.setVideoURI(Uri.parse(RESOURCES_PATH+"/"+ Path.FILENAME_VIDEO_SPONSOR));
        video_loop();


    }

    public void video_loop(){
        video_iddle.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        video_iddle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("RES","HOLA");
                //ACA ES EL RECONOCIMIENTOOO pero antes instrucciones lueg
                Intent intent = new Intent(getApplicationContext(), InstructionActivity.class);
                startActivity(intent);
                return false;
            }
        });
        //Iniciamos el video
        video_iddle.start();

    }
}
