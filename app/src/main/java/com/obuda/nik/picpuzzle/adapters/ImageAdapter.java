package com.obuda.nik.picpuzzle.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.obuda.nik.picpuzzle.models.Tile;

/**
 * Created by Dezsi on 2017.11.17..
 */

public class ImageAdapter extends BaseAdapter {
    private Tile[] Tiles;
    private Context context;

    public ImageAdapter(Context context, Tile[] Tiles){
        this.context=context;
        this.Tiles= Tiles;
    }

    public Tile[] getTiles(){
        return this.Tiles;
    }
    public void setTiles(Tile[] Tiles){
        this.Tiles=Tiles;
    }

    @Override
    public int getCount() {
        return Tiles.length;
    }

    @Override
    public Object getItem(int i) {
        return Tiles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            ImageView img=new ImageView(context);
            img.setImageBitmap(Tiles[i].getPicture());
            img.setScaleType(ImageView.ScaleType.FIT_XY);

            viewHolder=new ViewHolder(img);
            img.setTag(viewHolder);
            view=img;
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        if(Tiles[i]!=null && Tiles[i].getPicture()!=null) {
            viewHolder.view.setImageBitmap(Tiles[i].getPicture());
            viewHolder.view.setImageAlpha(255);
        }
        else
            viewHolder.view.setImageAlpha(0);
        return view;
    }

    private class ViewHolder{
        ImageView view;
        ViewHolder(ImageView v){
            view=v;
        }
    }
}

