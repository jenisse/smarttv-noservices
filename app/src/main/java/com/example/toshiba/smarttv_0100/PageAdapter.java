package com.example.toshiba.smarttv_0100;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView opt_name;
        ImageView opt_image;

        MyViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            opt_name = (TextView)itemView.findViewById(R.id.opt_name);
            opt_image = (ImageView)itemView.findViewById(R.id.opt_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                    _ejecutar_funcion(opt_name.getText().toString(),itemView );
                }
            });
        }

        private void _ejecutar_funcion(String funcionalidad, View view){
            if(funcionalidad.equals(view.getContext().getResources().getString(R.string.ruleta))){
                _entrar_actividad( view, RouletteActivity.class);
            }else if(funcionalidad.equals(view.getContext().getResources().getString(R.string.foto))){
                _entrar_actividad( view, TakePictureActivity.class);
            }
        }

        private void _entrar_actividad(View view, Class clase){
            view.getContext().startActivity(new Intent(view.getContext(),clase));
        }
    }

    List<String> listName;
    List<Integer> listImage;
    Context context;
    Resources resources;

    PageAdapter(Context context, List<String> names,List<Integer> images){
        this.listName = names ;
        this.listImage = images;
        this.context=context;
        resources=context.getResources();
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);
    }


@Override
    public void onBindViewHolder(MyViewHolder personViewHolder, int i) {
        personViewHolder.opt_name.setText(listName.get(i));
        personViewHolder.opt_image.setImageResource(listImage.get(i));
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    }
