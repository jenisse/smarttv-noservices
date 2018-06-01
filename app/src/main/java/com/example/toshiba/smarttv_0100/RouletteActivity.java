package com.example.toshiba.smarttv_0100;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Random;

public class RouletteActivity extends Activity {

    /*
        X => 420 - 960 - 1500
        Y => 0 - 540 - 1080
    */
    int POS_RULETA_X = 420, POS_RULETA_Y = 0, SIZE_RULETA_X = 1080, SIZE_RULETA_Y = 1080,
            SIZE_ELEM_X = 200, SIZE_ELEM_Y = 200, BASE_X = 960 - SIZE_ELEM_X /2, BASE_Y = 540 - SIZE_ELEM_Y /2,
            BORDE_RULETA = 50, RADIO_RULETA = 540 - SIZE_ELEM_Y /2 - BORDE_RULETA;

    private Handler mHandler = new Handler();

    Integer CUPONES = 6, LOADING_ELEM;

    int num_ruleta, cantidad_max_giradas, REDUCIR_VELOCIDAD = 40, FRAMES_RULETA = 3, FRAMES_VELOCIDAD = 1,
            DESVIACION_AGUJA = 0;
    int inicio_giro = 1000, fin_giro = 2300;
    Button btn_girar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_roulette);
        LOADING_ELEM = -2;
        num_ruleta = 0;
        Random r = new Random();
        cantidad_max_giradas = r.nextInt(fin_giro - inicio_giro) + inicio_giro;

        _inicializar_layout();

        //Obtener urls y ids
        String url_ruleta = _obtener_url_ruleta();
        String url_aguja = _obtener_url_aguja();
        String urls[] = _obtener_url_elementos_ruleta();

        int id_ruleta = _obtener_id_ruleta();
        int id_aguja = _obtener_id_aguja();
        int ids[] = _obtener_id_elementos_ruleta();

        //Creamos la ruleta y sus elementos
        crear_ruleta(id_ruleta, url_ruleta, id_aguja, url_aguja);
        crear_elementos(ids, urls);

        //Hacemos girar la ruleta
        //girar_ruleta();
    }

    private void _inicializar_layout(){
        _datos_persona();

        btn_girar = (Button) findViewById(R.id.btn_girar);
        btn_girar.setX(POS_RULETA_X + SIZE_RULETA_X + POS_RULETA_X/2);
        btn_girar.setY(POS_RULETA_Y + SIZE_RULETA_Y/2 );

        btn_girar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                btn_girar.setEnabled(false);
                //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                num_ruleta = 0;

                //Prueba
                //cantidad_max_giradas = 20 seg
                //cantidad_max_giradas = 40 seg
                //cantidad_max_giradas = fin_giro;

                //Inicar contador
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        //_velocidad_ruleta();
                        _girar_ruleta();
                    }
                }, 2);

                //Iniciar girar

                //Iniciar fin
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        _mostrar_premio();
                    }
                    //}, cantidad_max_giradas + 0);
                }, ((cantidad_max_giradas*39*1000)/2300)+5000);
            }
        });

    }
    private void _datos_persona(){
        TextView text_persona = (TextView) findViewById(R.id.text_persona);
        text_persona.setX(50);
        text_persona.setY(20);
        String NAME_CLIENT = "Sergio";
        text_persona.setText("Suerte " + NAME_CLIENT);

        ImageView img_persona = (ImageView) findViewById(R.id.img_persona);
        String link_foto = "https://scontent.flim5-4.fna.fbcdn.net/v/t1.0-1/p160x160/26196489_1312794842159787_7029482683852320049_n.jpg?_nc_cat=0&oh=4af9d1b1823bb094a58b40eb9d0ce670&oe=5B7FC544";
        new DownloadImageTask(img_persona).execute(link_foto);

        img_persona.getLayoutParams().height = SIZE_ELEM_Y*3/2;
        img_persona.getLayoutParams().width = SIZE_ELEM_X*3/2;
        img_persona.setX(50);
        img_persona.setY(100);
    }



    //SERVICIOS
    private String _obtener_url_ruleta(){
        //String url_fondo = "http://www.saberperder.com/files/2008/05/113.jpg";
        String url_fondo = "http://creativecasino.srv.ladbrokes.com/b/cultural-roulette/assets/images/roulette-wheel.png";
        return url_fondo;
    }
    private String _obtener_url_aguja(){
        String url_aguja = "http://www.toptrivial.com/themes/top_trivial_oficial/img/ruleta-aguja.png";
        return url_aguja;
    }
    //SERVICIOS
    private String[] _obtener_url_elementos_ruleta(){
        String urls[] = new String[CUPONES];

        //urls[0] = "https://png.pngtree.com/element_origin_min_pic/04/03/26/2857dec4522d272.jpg";
        //urls[1] = "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/5768/5768501cv11d.jpg";
        urls[1] = "http://www.samsung.com/ar/curveduhd/common/images/tv_featured/tv.png";
        //urls[2] = "https://www.att.com/catalog/en/skus/Apple/Apple%20iPhone%20X/overview/326743-pdp-iphoneX-s2-1@2x.jpg";
        urls[2] = "http://pluspng.com/img-png/iphone-png-apple-iphone-6-550.png";
        //urls[3] = "https://lf.lids.com/hwl?set=sku[20869640],c[2],w[1000],h[750]&call=url[file:product]";
        //urls[4] = "https://i.kinja-img.com/gawker-media/image/upload/s--Q7JabjMW--/c_scale,fl_progressive,q_80,w_800/u7ssdnrkvgpn4teilnvy.jpg";
        urls[4] = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Nintendo_Switch_Portable.png/1024px-Nintendo_Switch_Portable.png";
        urls[5] = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/Starbucks_Corporation_Logo_2011.svg/1200px-Starbucks_Corporation_Logo_2011.svg.png";

        //urls[0] = "https://super.walmart.com.mx/images/product-images/img_large/00000000004430L.jpg";
        //urls[3] = "https://super.walmart.com.mx/images/product-images/img_large/00000000004430L.jpg";
        urls[0] = "https://vignette.wikia.nocookie.net/clubpenguin/images/8/89/Pi%C3%B1a.png/revision/latest?cb=20140623151452&path-prefix=es";
        urls[3] = "https://vignette.wikia.nocookie.net/clubpenguin/images/8/89/Pi%C3%B1a.png/revision/latest?cb=20140623151452&path-prefix=es";

        return urls;
    }



    //HARDCODEO
    private int _obtener_id_ruleta(){
        int id = R.id.ruleta_fondo;
        return id;
    }
    //HARDCODEO
    private int _obtener_id_aguja(){
        int id = R.id.aguja_ruleta;
        return id;
    }
    //HARDCODEO
    private int[] _obtener_id_elementos_ruleta(){
        int ids[] = new int[CUPONES];

        ids[0] = R.id.elem_1;
        ids[1] = R.id.elem_2;
        ids[2] = R.id.elem_3;
        ids[3] = R.id.elem_4;
        ids[4] = R.id.elem_5;
        ids[5] = R.id.elem_6;

        return ids;
    }



    //LAYOUT
    private void crear_ruleta(int id_ruleta, String url_ruleta, int id_aguja, String url_aguja){
        ImageView imagen_ruleta = (ImageView) findViewById(id_ruleta);
        new DownloadImageTask(imagen_ruleta).execute(url_ruleta);
        imagen_ruleta.getLayoutParams().height = SIZE_RULETA_Y;
        imagen_ruleta.getLayoutParams().width = SIZE_RULETA_X;
        imagen_ruleta.setX(POS_RULETA_X);
        imagen_ruleta.setY(POS_RULETA_Y);

        ImageView imagen_aguja = (ImageView) findViewById(id_aguja);
        new DownloadImageTask(imagen_aguja).execute(url_aguja);
        imagen_aguja.getLayoutParams().height = SIZE_ELEM_Y*4;
        imagen_aguja.getLayoutParams().width = SIZE_ELEM_X*4;
        imagen_aguja.setX(POS_RULETA_X + SIZE_RULETA_X/2 - SIZE_ELEM_X*2);
        imagen_aguja.setY(POS_RULETA_Y + SIZE_RULETA_Y/2 - SIZE_ELEM_Y*2);
        imagen_aguja.setRotation(imagen_aguja.getRotation() - 30);
    }
    //LAYOUT
    private void crear_elementos(int id_elemento[], String url_elemento[]){
        int grados = 0;

        for(int i = 0; i < CUPONES; i++) {

            ImageView elemento_ruleta = (ImageView) findViewById(id_elemento[i]);
            new DownloadImageTask(elemento_ruleta).execute(url_elemento[i]);
            elemento_ruleta.getLayoutParams().height = SIZE_ELEM_Y;
            elemento_ruleta.getLayoutParams().width = SIZE_ELEM_X;

            elemento_ruleta.setRotation(grados);

            _posicion_elemento(elemento_ruleta);

            grados += 360/CUPONES;
        }
    }



    //FUNCIONES
    private void _posicion_elemento(ImageView elemento_ruleta){
        double change_pos_x = RADIO_RULETA *Math.sin( toRadians( (int) elemento_ruleta.getRotation() ) );
        elemento_ruleta.setX(BASE_X + (float) change_pos_x  );
        double change_pos_y = RADIO_RULETA *Math.cos( toRadians( (int) elemento_ruleta.getRotation() ) );
        elemento_ruleta.setY(BASE_Y - (float) change_pos_y );
    }
    //FUNCIONES
    private double toRadians(int angle){
        return  angle*( Math.PI/180 );
    }



    //GIRAR
    private void _accion_girar(){
        int id_ruleta = _obtener_id_ruleta();
        int id_elementos[] = _obtener_id_elementos_ruleta();
        ImageView fondo = findViewById(id_ruleta);
        fondo.setRotation( fondo.getRotation() + 1 );

        for(int i = 0; i < CUPONES; i++){
            ImageView elemento = findViewById(id_elementos[i]);
            elemento.setRotation( elemento.getRotation() + 1 );
            _posicion_elemento(elemento);
        }
    }
    //GIRAR
    private void _girar_ruleta(){
        if(num_ruleta >= cantidad_max_giradas) {
            //num_ruleta = 0;
            return;
        }
        //else
        //    num_ruleta++;
        if( num_ruleta < (cantidad_max_giradas/2) ) {
            for (int i = 0; i < num_ruleta/REDUCIR_VELOCIDAD; i++) {
                _accion_girar();
            }
        }
        else {
            for (int i = cantidad_max_giradas/REDUCIR_VELOCIDAD; i > num_ruleta/REDUCIR_VELOCIDAD; i--) {
                _accion_girar();
            }
        }

        mHandler.postDelayed(new Runnable() {
            public void run() {
                num_ruleta++;
                _girar_ruleta();
            }
        }, FRAMES_RULETA);
    }
    //VELOCIDAD GIRAR
    private void _velocidad_ruleta(){
        if(num_ruleta >= cantidad_max_giradas)
            return;
        num_ruleta++;
        mHandler.postDelayed(new Runnable() {
            public void run() {
                _girar_ruleta();
            }
        }, FRAMES_VELOCIDAD);
    }
    //FIN GIRAR
    private void _mostrar_premio(){

        set_all_invisible();
        //Temporal
        TextView text_persona = (TextView) findViewById(R.id.text_persona);
        //text_persona.setX(50);
        //text_persona.setY(20);
        String PREMIO =  ( (Integer) _get_premio() ).toString();

        ImageView ruleta = (ImageView) findViewById( _obtener_id_ruleta() );
        Integer angulo =  360 - ( ( ( (int) ruleta.getRotation() ) + DESVIACION_AGUJA) % 360 );
        text_persona.setText("Obtuviste el premio " + PREMIO + " - giro " + angulo.toString() );

        String urls[] = _obtener_url_elementos_ruleta();
        ImageView img_persona = (ImageView) findViewById(R.id.img_persona);
        String link_foto = urls[_get_premio() - 1];
        new DownloadImageTask(img_persona).execute(link_foto);

    }
    //FIN GIRAR
    private int _get_premio(){
        ImageView ruleta = (ImageView) findViewById( _obtener_id_ruleta() );
        int angulo =  360 - ( ( (int) ruleta.getRotation() ) + DESVIACION_AGUJA );

        for(int i = 1; i <= CUPONES; i++){
            if( (angulo%360) < (360/CUPONES)*i )
                return i;
        }
        return -1;
    }


    public  void set_all_invisible(){
        btn_girar.setVisibility(View.INVISIBLE);
        ImageView ruleta = (ImageView) findViewById( _obtener_id_ruleta() );
        ruleta.setVisibility(View.INVISIBLE);
        ImageView imagen_aguja = (ImageView) findViewById(_obtener_id_aguja());
        imagen_aguja.setVisibility(View.INVISIBLE);

        int[] id_elemento=_obtener_id_elementos_ruleta();

        for(int i = 0; i < CUPONES; i++) {

            ImageView elemento_ruleta = (ImageView) findViewById(id_elemento[i]);
            elemento_ruleta.setVisibility(View.INVISIBLE);

        }
    }

    //Para asignar una imagen a un ImageView con un url [NO TOCAR]
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            //Activamos el boton
            LOADING_ELEM++;
            if(LOADING_ELEM > CUPONES) {
                btn_girar.setEnabled(true);
            }
        }

    }
}