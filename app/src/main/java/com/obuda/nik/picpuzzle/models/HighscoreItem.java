package com.obuda.nik.picpuzzle.models;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public void setBestTime(long timeElapsed){
        mins = (int) TimeUnit.MILLISECONDS.toMinutes(timeElapsed);
        secs = (int)(TimeUnit.MILLISECONDS.toSeconds(timeElapsed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeElapsed)));
    }
}
