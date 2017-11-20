package com.obuda.nik.picpuzzle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.obuda.nik.picpuzzle.alarmClock.AlarmReceiver;
import com.obuda.nik.picpuzzle.game.Difficulty;

import java.util.Calendar;

public class AlarmClockActivity extends AppCompatActivity {

    private static final int REQUEST_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);

        Spinner spinner= (Spinner) findViewById(R.id.spinner);
        Switch sw= (Switch) findViewById(R.id.alarmSwitch);
        final TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent intent;
        boolean alarmUp;

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item, Difficulty.getNames());
        spinner.setAdapter(adapter);


        intent = new Intent(this, AlarmReceiver.class);
        alarmUp=PendingIntent.getBroadcast(this,REQUEST_ID,intent,
                                PendingIntent.FLAG_NO_CREATE)!=null;
        sw.setChecked(alarmUp);
        //FIXME if alarm is up show the alarm time
        //FIXME if selected time changed when alarm is active refresh the alarm

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Calendar calendar= Calendar.getInstance();

                    calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                    calendar.set(Calendar.SECOND, 0);

                    if(calendar.compareTo(Calendar.getInstance())<0)
                        calendar.add(Calendar.DATE,1);

                    PendingIntent pendingIntent=PendingIntent.getBroadcast(AlarmClockActivity.this,
                            REQUEST_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                }else{
                    PendingIntent pendingIntent=PendingIntent.getBroadcast(AlarmClockActivity.this,
                            REQUEST_ID,intent,PendingIntent.FLAG_NO_CREATE);
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                }
            }
        });


    }
}
