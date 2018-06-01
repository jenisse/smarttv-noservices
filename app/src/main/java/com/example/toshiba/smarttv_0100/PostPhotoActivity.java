package com.example.toshiba.smarttv_0100;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.io.File;

public class PostPhotoActivity extends AppCompatActivity{
    String RESOURCES_PATH = Environment.getExternalStorageDirectory().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_photo);
        LinearLayout layout =(LinearLayout)findViewById(R.id.postPhoto);
        File f = new File(RESOURCES_PATH+"/"+ com.example.toshiba.smarttv_0100.Model.Path.FILENAME_FRAME_SPONSOR);
        Drawable d = Drawable.createFromPath(f.getAbsolutePath());
        layout.setBackground(d);

        File file=new File(RESOURCES_PATH+"/"+ com.example.toshiba.smarttv_0100.Model.Path.FILENAME_USER_PHOTO_FB);
        //Bitmap bmp= BitmapFactory.decodeFile(f2.getAbsolutePath());
        //int nh = (int) ( bmp.getHeight() * (512.0 / bmp.getWidth()) );
        ImageView imageView=(ImageView)findViewById(R.id.photoToPost);
        Uri imageUri = Uri.fromFile(file);

        Glide.with(this)
                .load(imageUri)
                .apply(new RequestOptions()
                        .signature(new ObjectKey(file.length() + "@" + file.lastModified()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE))

                .into(imageView);

//        Bitmap scaled = Bitmap.createScaledBitmap(bmp, 512, nh, true);
//        ImageView imageView=(ImageView)findViewById(R.id.photoToPost);
//        imageView.setImageBitmap(scaled);

        Button posting_button= (Button) findViewById(R.id.buttonPost);
        posting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Foto Publicada",Toast.LENGTH_LONG ).show();
                //retorna a menu principal
                Intent intent= new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
        Button again_button=(Button) findViewById(R.id.buttonAgain);
        again_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), TakePictureActivity.class);
                startActivity(intent);
            }
        });

    }
}
