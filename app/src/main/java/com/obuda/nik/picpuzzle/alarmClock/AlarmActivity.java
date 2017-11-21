package com.obuda.nik.picpuzzle.alarmClock;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.obuda.nik.picpuzzle.GameActivity;
import com.obuda.nik.picpuzzle.R;

import java.io.IOException;

public class AlarmActivity extends AppCompatActivity {

    private boolean muted=false;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent intent=new Intent(this, GameActivity.class);
        intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));

        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if(audioManager.getStreamVolume(AudioManager.STREAM_RING)==0){
            audioManager.setStreamVolume(AudioManager.STREAM_RING,1,AudioManager.FLAG_PLAY_SOUND);
            muted=true;
        }

        try {
            Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mediaPlayer.setDataSource(this, alarm);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (IOException e)
        {}
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mediaPlayer.stop();
        if(muted)
            audioManager.setStreamVolume(AudioManager.STREAM_RING,0,AudioManager.FLAG_PLAY_SOUND);
        finish();

    }
}
