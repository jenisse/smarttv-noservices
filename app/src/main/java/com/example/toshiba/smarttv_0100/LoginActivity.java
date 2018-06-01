package com.example.toshiba.smarttv_0100;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.net.URL;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.toshiba.smarttv_0100.Model.Path;
import com.example.toshiba.smarttv_0100.Model.api.ServiceManager;
import com.example.toshiba.smarttv_0100.R;
import com.example.toshiba.smarttv_0100.Utility.Utility;
import com.google.gson.JsonObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.ButterKnife;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity  extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    Utility utility = new Utility();
    String RESOURCES_PATH = Environment.getExternalStorageDirectory().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long downloadReference;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        _loginButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            login();
        }
    });

}

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        Integer id = Integer.parseInt(_emailText.getText().toString());
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        call_login_service(id, password, progressDialog);
    }


    public void call_login_service(Integer id, String password, ProgressDialog progressDialog){
        ServiceManager
                .getInstance(getApplicationContext())
                .login(id, password, new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.i("RESP", response.toString());
                        try {
                            if (response.isNull("login")   ){
                                onLoginFailed();
                            }
                            else if (response.getJSONObject("login").getString("authenticate").equals("true")){
                                //call sponsor of the day for the user
                                //call_todaysponsor_service();

                                String imageURL="http://www.pptbackgrounds.org/uploads/coca-cola-2-backgrounds-wallpapers.jpg";
                                String videoURL="https://s3.amazonaws.com/dp2awsfacial/video_sprite.mp4";
                                new DownloadSponsorResources().execute(imageURL, videoURL);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        onLoginFailed();
                    }
                });

    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Inicio de sesion fallido. Intente de nuevo", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        Pattern p = Pattern.compile("[a-z]");
        Matcher m = p.matcher(email);

        if (email.isEmpty() || m.find()) {
            _emailText.setError("Ingresar usuario valido");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Ingresar contraseña válida");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }




    // DownloadImage AsyncTask
    public class DownloadSponsorResources extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog mProgressDialog;

        private final int TIMEOUT_CONNECTION = 5000;//5sec
        private final int TIMEOUT_SOCKET = 30000;//30sec

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog

            mProgressDialog = new ProgressDialog(LoginActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Descargando Recursos");
            // Set progressdialog message
            mProgressDialog.setMessage("Cargando...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];
            String videoURL = URL[1];

            Bitmap bitmap = null;
            FileOutputStream out_image = null;
            FileOutputStream out_video = null;

            try {
                // Download Image from URL
                InputStream input_image = new java.net.URL(imageURL).openStream();
                // Decode Bitmap

            File file_image = new File(RESOURCES_PATH, "/" + Path.FILENAME_FRAME_SPONSOR);
                File file_video = new File(RESOURCES_PATH, "/" + Path.FILENAME_VIDEO_SPONSOR);

                out_image = new FileOutputStream(file_image);


                bitmap = BitmapFactory.decodeStream(input_image);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out_image);



                URL url = new URL(URL[1]);
                InputStream is = url.openStream();
                HttpURLConnection huc = (HttpURLConnection)url.openConnection(); //to know the size of video
                int size = huc.getContentLength();

                if(huc != null) {

                    FileOutputStream fos = new FileOutputStream(file_video);
                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    if(is != null) {
                        while ((len1 = is.read(buffer)) > 0) {
                            fos.write(buffer,0, len1);
                        }
                    }
                    if(fos != null) {
                        fos.close();
                    }
                }





            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            //image.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
            Intent intent = new Intent(getApplicationContext(), SponsorVideoActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
