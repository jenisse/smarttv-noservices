package com.example.toshiba.smarttv_0100;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity  {
    private PageAdapter adapter;
    String RESOURCES_PATH = Environment.getExternalStorageDirectory().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_list);
        set_components();
    }

    private void set_components(){

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        List<String> optNames=new ArrayList<>();
        List<Integer> optImages= new ArrayList<>();
        optNames.add(getString(R.string.foto));
        optNames.add(getString(R.string.ruleta));
        optImages.add(R.drawable.camera);
        optImages.add(R.drawable.wheel);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_recycler_view);
        //rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        adapter = new PageAdapter(this, optNames, optImages);
        rv.setAdapter(adapter);
        LinearLayout layout =(LinearLayout)findViewById(R.id.linear_layout);
        File f = new File(RESOURCES_PATH+"/"+ com.example.toshiba.smarttv_0100.Model.Path.FILENAME_FRAME_SPONSOR);
        Drawable d = Drawable.createFromPath(f.getAbsolutePath());
        layout.setBackground(d);


    }





}
