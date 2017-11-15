package com.obuda.nik.picpuzzle.models;

import android.graphics.Bitmap;

/**
 * Created by Dezsi on 2017.11.15..
 */

public class Tile {
    private int ID;
    private Bitmap picture;

    public  Tile(int ID, Bitmap picture){
        this.ID=ID;
        this.picture=picture;
    }

    public int getID(){
        return this.ID;
    }
    public  Bitmap getPicture() {
        return this.picture;
    }
}
