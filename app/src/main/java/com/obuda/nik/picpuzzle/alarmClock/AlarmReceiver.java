package com.obuda.nik.picpuzzle.alarmClock;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.obuda.nik.picpuzzle.GameActivity;
import com.obuda.nik.picpuzzle.game.Difficulty;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Dezsi on 2017. 11. 20..
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static final int REQUEST_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        WakeLocker.acquire(context);
        Log.d("TIME", DateFormat.getDateTimeInstance().format(new Date()));
        Intent i=new Intent(context, GameActivity.class);
        i.putExtra("difficulty", Difficulty.getNames()[0]);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        //Toast.makeText(context,"Hi",Toast.LENGTH_SHORT).show();
        PendingIntent.getBroadcast(context,REQUEST_ID,intent,PendingIntent.FLAG_NO_CREATE).cancel();
    }
}
