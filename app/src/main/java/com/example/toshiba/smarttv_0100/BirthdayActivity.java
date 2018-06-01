package com.example.toshiba.smarttv_0100;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.tomer.fadingtextview.FadingTextView;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;

public class BirthdayActivity extends AppCompatActivity{
    String RESOURCES_PATH = Environment.getExternalStorageDirectory().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        Bundle extras = getIntent().getExtras();
        String name_client= extras.getString("name");

        ConstraintLayout layout =(ConstraintLayout)findViewById(R.id.birthAct);
        File f = new File(RESOURCES_PATH+"/"+ com.example.toshiba.smarttv_0100.Model.Path.FILENAME_FRAME_SPONSOR);
        Drawable d = Drawable.createFromPath(f.getAbsolutePath());
        layout.setBackground(d);

        TextView textView=(TextView)findViewById(R.id.textViewBirthday);

        textView.setText(name_client);

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 2 seconds
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        }, 4000);
    }
}
