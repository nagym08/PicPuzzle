package com.obuda.nik.picpuzzle.alarmClock;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.obuda.nik.picpuzzle.GameActivity;


/**
 * Created by Dezsi on 2017. 11. 20..
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static final int REQUEST_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        WakeLocker.acquire(context);
        Intent i=new Intent(context, AlarmActivity.class);
        i.putExtra("difficulty", intent.getStringExtra("difficulty"));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        PendingIntent.getBroadcast(context,REQUEST_ID,intent,PendingIntent.FLAG_NO_CREATE).cancel();
    }
}
