package com.obuda.nik.picpuzzle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private static final String SHARED_PREF_ID="AlarmPrefs";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);

        intent=new Intent(this, AlarmReceiver.class);

        final Spinner spinner= (Spinner) findViewById(R.id.spinner);
        Switch sw= (Switch) findViewById(R.id.alarmSwitch);
        final TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker);
        final boolean alarmUp;

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item, Difficulty.getNames());
        spinner.setAdapter(adapter);

        alarmUp=PendingIntent.getBroadcast(this,REQUEST_ID,intent,
                                PendingIntent.FLAG_NO_CREATE)!=null;
        sw.setChecked(alarmUp);

        if(alarmUp) {
            setTimePicker(timePicker,getAlarmTime()[0],getAlarmTime()[1]);
            int j=0;
            while (!String.valueOf(Difficulty.getNames()[j]).equals(getAlarmDifficulty())){
                j++;
            }
            spinner.setSelection(j);
        }


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    int[] time=getTimePicker(timePicker);
                    setNewAlarm(time[0], time[1],
                            String.valueOf(Difficulty.getNames()[spinner.getSelectedItemPosition()]));
                }else
                    cancelAlarm();
            }
        });


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                if(alarmUp)
                    updateAlarm(hour,minute,
                            String.valueOf(Difficulty.getNames()[spinner.getSelectedItemPosition()]));
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(alarmUp) {
                    int[] time=getTimePicker(timePicker);
                    updateAlarm(time[0], time[1],
                            String.valueOf(Difficulty.getNames()[position]));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setNewAlarm(int hour,int minute, String difficulty){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar=setTime(hour,minute);

        intent.putExtra("difficulty",difficulty);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(AlarmClockActivity.this,
                REQUEST_ID,this.intent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        saveAlarmTime(hour,minute);
        saveAlarmDifficulty(difficulty);
    }
    private void updateAlarm(int hour,int minute,String difficulty){
        cancelAlarm();
        setNewAlarm(hour,minute,difficulty);
    }
    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(AlarmClockActivity.this,
                REQUEST_ID,this.intent,PendingIntent.FLAG_NO_CREATE);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }
    private Calendar setTime(int hour,int minute){
        Calendar calendar= Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if(calendar.compareTo(Calendar.getInstance())<0)
            calendar.add(Calendar.DATE,1);

        return  calendar;
    }
    private void setTimePicker(TimePicker timePicker, int hour, int minute){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        }else{
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }
    }
    private int[] getTimePicker(TimePicker timePicker){
        int[] result=new int[2];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result[0]=timePicker.getHour();
            result[1]=timePicker.getMinute();
        }else{
            result[0]=timePicker.getCurrentHour();
            result[1]=timePicker.getCurrentMinute();
        }
        return result;
    }
    private void saveAlarmTime(int hour,int minute){
        SharedPreferences.Editor editor=getSharedPreferences(SHARED_PREF_ID,0).edit();
        editor.putInt("hour",hour);
        editor.putInt("minute",minute);
        editor.apply();
    }
    private int[] getAlarmTime(){
        int[] alarmTime=new  int[2];
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_ID,0);

        alarmTime[0]=sharedPreferences.getInt("hour",0);
        alarmTime[1]=sharedPreferences.getInt("minute",1);

        return alarmTime;
    }
    private void saveAlarmDifficulty(String value){
        SharedPreferences.Editor editor=getSharedPreferences(SHARED_PREF_ID,0).edit();
        editor.putString("difficulty",value);
        editor.apply();
    }
    private String getAlarmDifficulty(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_ID,0);
        return sharedPreferences.getString("difficulty",null);
    }
}
