package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LumusActivity extends AppCompatActivity implements SensorEventListener {

    AlertDialog alertDialog;
    private SensorManager sensorManager;
    private Sensor sensor;

    private boolean notAble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_lumus);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        // Use the accelerometer.
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        } else{
            notAble = true;
        }

        if(notAble) noSensors();
    }

    private boolean isRunning = false;
    public void Start(View view){
        if(notAble) noSensors();
        else {
            ((Button) findViewById(R.id.btnSpell)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.txtRelease)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.txtShake)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.txtJoke)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.btnStop)).setVisibility(View.VISIBLE);
            isRunning = true;
        }
    }

    public void Stop(View view){
        isRunning = false;
        setLight(false);
        ((Button) findViewById(R.id.btnSpell)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.txtRelease)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.txtShake)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.txtJoke)).setVisibility(View.INVISIBLE);
        ((Button) findViewById(R.id.btnStop)).setVisibility(View.INVISIBLE);
    }

    private float[] initValues = new float[3];
    private boolean init = true, mode = false;
    private float THRESHOLD = 2;
    public void onSensorChanged(SensorEvent event){ // TODO: FIX HERE

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            if(init) {
                initValues[0] = event.values[0];
                initValues[1] = event.values[1];
                initValues[2] = event.values[2];
                init = false;
            }

            boolean done = false;

            for(int i = 0; i < initValues.length; i++){
                if(done) break;
                if(event.values[i] > initValues[i] + THRESHOLD) done = true;
            }

            if(done){
                mode = !mode;
                setLight(mode);
                init = true;
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void noSensors(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.no_sensor_lumus_title));
        builder.setMessage(getString(R.string.no_sensor_lumus_desc));
        alertDialog = builder.create();
        alertDialog.show();
    }

    String cameraId;
    CameraManager camManager;
    private void setLight(boolean status){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            cameraId = null;

            try {
                cameraId = camManager.getCameraIdList()[0];
                camManager.setTorchMode(cameraId, status);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

}