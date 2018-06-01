package com.example.toshiba.smarttv_0100;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.toshiba.smarttv_0100.Model.Path;
import com.example.toshiba.smarttv_0100.Model.api.ServiceManager;
import com.tomer.fadingtextview.FadingTextView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class RecognizeActivity extends AppCompatActivity {
    String RESOURCES_PATH = Environment.getExternalStorageDirectory().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);

        ConstraintLayout layout =(ConstraintLayout)findViewById(R.id.recogAct);
        File f = new File(RESOURCES_PATH+"/"+ com.example.toshiba.smarttv_0100.Model.Path.FILENAME_FRAME_SPONSOR);
        Drawable d = Drawable.createFromPath(f.getAbsolutePath());
        layout.setBackground(d);

        LottieAnimationView animationView = (LottieAnimationView)findViewById(R.id.lottieAnimationViewR);
        LottieAnimationView animationView2 = (LottieAnimationView)findViewById(R.id.lottieAnimationViewR2);

        animationView.playAnimation();
        //TODO aca llamar al servicio
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 2 seconds
                //recognize_service();
                evaluate_response(new JSONObject()); //esto es de pruebaaa
            }
        }, 3500);



    }

    public void recognize_service(){
        File file = new File(RESOURCES_PATH, "/" + Path.FILENAME_USER_PHOTO_RECOG);
        ServiceManager
                .getInstance(getApplicationContext())
                .login_client(file, new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("RESP", response.toString());
                        evaluate_response(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        onLoginFailed();
                    }
                });

    }

    public void evaluate_response(JSONObject response){
        Boolean flag=true;
        Boolean birthday=true;
        if(flag==true){
            //Si respuesta exitosa
            //TODO parcear y guardar el objeto
            FadingTextView textView= (FadingTextView)findViewById(R.id.textRec);
            textView.setTexts(R.array.recog_act_2_fading);
            LottieAnimationView unlock= (LottieAnimationView)findViewById(R.id.lottieAnimationViewR);
            unlock.setAnimation(R.raw.padlock_tick);
            unlock.playAnimation();
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 2 seconds
                    Log.i("TERMINO","TERMINO");
                    if (birthday){
                        Intent intent = new Intent(getApplicationContext(), BirthdayActivity.class);
                        String name_client  = "FREDY HERNANDEZ";
                        intent.putExtra( "name", name_client );
                        startActivity(intent);
                    }else{
                        //Defrente pasa a la pantalla de menu principal
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    }
                }
            }, 3500);


        }
        else{
            //Si no se reconocio

        }
    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Error al procesar.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), SponsorVideoActivity.class);
        startActivity(intent);

    }
}
