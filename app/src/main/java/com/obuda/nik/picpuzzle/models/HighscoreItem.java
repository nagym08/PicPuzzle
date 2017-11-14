package com.obuda.nik.picpuzzle.models;

import java.util.Date;

/**
 * Created by nagym on 14/11/2017.
 */

public class HighscoreItem {
    private String pictureName;
    private int mins;
    private int secs;

    public HighscoreItem(String pictureName, int mins, int secs) {
        this.pictureName = pictureName;
        this.mins = mins;
        this.secs = secs;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getBestTime(){
        String bestTimeText = "";
        bestTimeText = mins + ":" + secs;
        return bestTimeText;
    }
}
