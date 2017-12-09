package com.obuda.nik.picpuzzle.models;

import java.util.concurrent.TimeUnit;

/**
 * Created by nagym on 14/11/2017.
 */

public class HighscoreItem {

    private long timeInMillisec;

    public HighscoreItem(long timeInMillisec) {
        this.timeInMillisec = timeInMillisec;
    }

    public long getTimeInMillisec() {
        return timeInMillisec;
    }

    public void setTimeInMillisec(long timeInMillisec) {
        this.timeInMillisec = timeInMillisec;
    }

    public String getBestTime(){
        int mins = (int) TimeUnit.MILLISECONDS.toMinutes(timeInMillisec);
        int secs = (int)(TimeUnit.MILLISECONDS.toSeconds(timeInMillisec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMillisec)));
        String bestTimeText = mins + ":" + secs;
        return bestTimeText;
    }
}
