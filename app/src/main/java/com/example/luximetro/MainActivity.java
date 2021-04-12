package com.example.luximetro;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    float value;
    float valueMaior;
    float valueMenor;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (sensor == null) {
            Toast.makeText(this, "O dispositivo não possui sensor de luz!",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                value = sensorEvent.values[0];

                TextView txtResultadoProg = (TextView) findViewById(R.id.txtResultado);
                TextView txtMaiorProg = (TextView) findViewById(R.id.txtMaior);
                TextView txtMenorProg = (TextView) findViewById(R.id.txtMenor);
                txtResultadoProg.setText("Luminosidade: " + value + " lx");

                switch (i){

                    case 0:
                        valueMaior = value;
                        valueMenor = value;
                        txtMaiorProg.setText("Maior Luminosidade: " + valueMaior + " lx");
                        txtMenorProg.setText("Menor Luminosidade: " + valueMenor + " lx");
                        i++;
                        break;

                    case 1:
                        if(value > valueMaior){
                            valueMaior = value;
                            txtMaiorProg.setText("Maior Luminosidade: " + valueMaior + " lx");
                        } else if( value < valueMenor){
                            valueMenor = value;
                            txtMenorProg.setText("Menor Luminosidade: " + valueMenor + " lx");
                        }
                        break;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}