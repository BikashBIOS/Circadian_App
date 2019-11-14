package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StepCount extends AppCompatActivity {
    TextView step,stepc;
    SensorManager sensorManager;
    Sensor sc;
    int n=0;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count);
        getSupportActionBar().setTitle("Step Counter");
        reset=findViewById(R.id.reset);
        step=findViewById(R.id.step);
        stepc=findViewById(R.id.stepc);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sc = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(sp, sc, SensorManager.SENSOR_DELAY_NORMAL);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n=0;
                stepc.setText("Steps= "+n);
            }
        });

    }
    SensorEventListener sp=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType()==Sensor.TYPE_STEP_COUNTER)
            {
                n++;
                stepc.setText("Steps= "+n);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


}

