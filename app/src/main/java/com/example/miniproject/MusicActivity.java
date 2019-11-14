package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {
    Button btn_next,btn_previous,btn_pause;
    TextView songTextLabel;
    SeekBar songSeekBar;
    static MediaPlayer mediaPlayer;
    int position;
    String sname;
    SensorManager sensorManager;
    Sensor Proximity;
    ArrayList<File> mySongs;
    Thread updateSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        btn_next=findViewById(R.id.next);
        btn_pause=findViewById(R.id.pause);
        btn_previous=findViewById(R.id.previous);
        songTextLabel=findViewById(R.id.songLabel);
        songSeekBar=findViewById(R.id.seekbar);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
       /* Acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(acc, Acc, SensorManager.SENSOR_DELAY_NORMAL);*/
        sensorManager.registerListener(proxy, Proximity, SensorManager.SENSOR_DELAY_NORMAL);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updateSeekbar=new Thread(){
            @Override
            public void run() {
                int totalDuration=mediaPlayer.getDuration();
                int currentPosition=0;
                while(currentPosition<totalDuration)
                {
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        if (songSeekBar != null) {
                            songSeekBar.setProgress(currentPosition);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };

        if (mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i=getIntent();
        Bundle  bundle=i.getExtras();
        mySongs=(ArrayList)bundle.getParcelableArrayList("songs");
        sname=mySongs.get(position).getName().toString();
        final String songName=i.getStringExtra("songname");
        songTextLabel.setText(songName);
        songTextLabel.setSelected(true);

        position= bundle.getInt("pos",0);

        Uri u=Uri.parse(mySongs.get(position).toString());

        mediaPlayer=MediaPlayer.create(getApplicationContext(),u);
        mediaPlayer.start();
        songSeekBar.setMax(mediaPlayer.getDuration());
        updateSeekbar.start();
        songSeekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        songSeekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN);

        songSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });


        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songSeekBar.setMax(mediaPlayer.getDuration());
                if (mediaPlayer.isPlaying())
                {
                    btn_pause.setBackgroundResource(R.drawable.icon_play);
                    mediaPlayer.pause();
                }
                else
                {
                    btn_pause.setBackgroundResource(R.drawable.icon_pause);
                    mediaPlayer.start();
                }
            }
        });



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position=((position+1)%mySongs.size());

                Uri u=Uri.parse(mySongs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),u);
                sname=mySongs.get(position).getName().toString();
                songTextLabel.setText(sname);
                mediaPlayer.start();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position=((position-1)<0)?(mySongs.size()-1):(position-1);
                Uri u=Uri.parse(mySongs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),u);
                sname=mySongs.get(position).getName().toString();
                songTextLabel.setText(sname);
                mediaPlayer.start();
            }
        });

    }
    SensorEventListener proxy=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            songSeekBar.setMax(mediaPlayer.getDuration());
            if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
                if (sensorEvent.values[0] == 0){
                    if (mediaPlayer.isPlaying())
                    {
                        btn_pause.setBackgroundResource(R.drawable.icon_play);
                        mediaPlayer.pause();
                    }
                    else
                    {
                        btn_pause.setBackgroundResource(R.drawable.icon_pause);
                        mediaPlayer.start();
                    }
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    /*SensorEventListener acc=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            songSeekBar.setMax(mediaPlayer.getDuration());
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                if (sensorEvent.values[0]>=10.0){
                    if (mediaPlayer.isPlaying())
                    {
                        btn_pause.setBackgroundResource(R.drawable.icon_play);
                        mediaPlayer.pause();
                    }
                    else
                    {
                        btn_pause.setBackgroundResource(R.drawable.icon_pause);
                        mediaPlayer.start();
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}


