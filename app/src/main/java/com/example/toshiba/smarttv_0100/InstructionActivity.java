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

import com.example.toshiba.smarttv_0100.PhotoRecoActivity;
import com.example.toshiba.smarttv_0100.R;

public class InstructionActivity extends AppCompatActivity {
    String RESOURCES_PATH = Environment.getExternalStorageDirectory().toString();
    VideoView video_iddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        video_iddle= (VideoView) findViewById(R.id.videoView);
        video_iddle.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.instructions ));
        video_iddle.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                @Override
                                                public void onCompletion(MediaPlayer mp) {
                                                    Intent intent = new Intent(getApplicationContext(), PhotoRecoActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                //Iniciamos el video
                video_iddle.start();

    }


}
