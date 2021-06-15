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
import android.widget.ImageView;
import android.widget.TextView;

public class LumusActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mAccelerometer;

    AlertDialog alertDialog;

    private boolean notAble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LumusActivity", "LumusActivity called.");
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_lumus);
        initUI();

        setMode(Mode.LUMUS);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        if (mAccelerometer == null) notAble = true;
        if (notAble) noSensors();
    }

    final float THRESHOLD = 25.0f, MIN_TOTAL_DELTA = 150f;
    final int MIN_ITERATIONS = 5;

    float[] lastValues;
    int iterations = 0;
    float totalDelta;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if(currentMode == Mode.RUNNING){

                if(lastValues == null){
                    lastValues = new float[3];

                    lastValues[0] = event.values[0];
                    lastValues[1] = event.values[1];
                    lastValues[2] = event.values[2];

                    iterations = 0;
                    totalDelta = 0f;

                }

                boolean pass = false;

                float delta = 0f;
                for(int i = 0; i < lastValues.length; i++){                                      // event.values[0] = X Axis variation
                    delta += Math.abs(event.values[i] - lastValues[i]);                          // event.values[1] = Y Axis variation
                }                                                                                // event.values[2] = Z Axis variation

                if(delta > THRESHOLD){ lastValues = null; setMode(Mode.EXPLODED); return; }
                if(iterations > MIN_ITERATIONS && totalDelta > MIN_TOTAL_DELTA){ lastValues = null; setMode(lightStatus? Mode.LUMUS : Mode.NOX); return; }

                lastValues[0] = event.values[0];
                lastValues[1] = event.values[1];
                lastValues[2] = event.values[2];

                iterations++;
                totalDelta += delta;

            }

        }
    }

    // Mode

    public void Spell(View view){
        if(notAble) { noSensors(); return; }

        if(currentMode!=Mode.RUNNING) setMode(Mode.RUNNING);
    }

    public enum Mode{ RUNNING, LUMUS, NOX, EXPLODED}
    private Mode currentMode;

    Button btnSpell;
    TextView txtRelease;
    TextView txtShake;
    TextView txtJoke;
    ImageView imgExplosion;
    private void setMode(Mode newMode){

        if(newMode == currentMode) return;

        currentMode = newMode;

        resetUI();

        switch (currentMode){
            case RUNNING:
                txtShake.setVisibility(View.VISIBLE);
                break;
            case LUMUS:
                setLight(false);
                txtRelease.setVisibility(View.VISIBLE);
                btnSpell.setVisibility(View.VISIBLE);
                btnSpell.setText(getString(R.string.lumus_lumus));
                break;
            case NOX:
                setLight(true);
                txtRelease.setVisibility(View.VISIBLE);
                btnSpell.setVisibility(View.VISIBLE);
                btnSpell.setText(getString(R.string.lumus_nox));
                break;
            case EXPLODED:
                btnSpell.setVisibility(View.VISIBLE);
                btnSpell.setText(getString(R.string.lumus_retry));
                imgExplosion.setVisibility(View.VISIBLE);
                txtJoke.setVisibility(View.VISIBLE);
                break;
        }
    }

    // UI

    private void resetUI(){
        btnSpell.setVisibility(View.INVISIBLE);
        txtRelease.setVisibility(View.INVISIBLE);
        txtShake.setVisibility(View.INVISIBLE);
        txtJoke.setVisibility(View.INVISIBLE);
        imgExplosion.setVisibility(View.INVISIBLE);
    }

    private void initUI(){
        btnSpell = findViewById(R.id.btnSpell);
        txtRelease = findViewById(R.id.txtRelease);
        txtShake = findViewById(R.id.txtShake);
        txtJoke = findViewById(R.id.txtJoke);
        imgExplosion = findViewById(R.id.imgExplosion);
    }

    private void noSensors(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.no_sensor_lumus_title));
        builder.setMessage(getString(R.string.no_sensor_lumus_desc));
        alertDialog = builder.create();
        alertDialog.show();
    }

    // Light

    private boolean lightStatus = false;
    String cameraId;
    CameraManager camManager;
    private void setLight(boolean status){
        this.lightStatus = status;
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}