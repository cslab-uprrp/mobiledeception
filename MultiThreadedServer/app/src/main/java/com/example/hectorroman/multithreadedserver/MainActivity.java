package com.example.hectorroman.multithreadedserver;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
    private Sensor accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    private double xAcc, yAcc, zAcc;

    MainActivity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startListener(View view) {
        Intent i = new Intent(this, serverService.class);
        startService(i);

    }

    public void stopListener(View view) {
        Intent i = new Intent(this, serverService.class);
        stopService(i);
    }

    public void quit(View view) {
        Intent i = new Intent(this, serverService.class);
        stopService(i);
        System.exit(0);
    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER){
            return;
        }else{
            double alpha = 0.8;
            double[] gravity = new double [3];

            //Isolate the force of gravity with the low pass filter
            gravity[0] = alpha * gravity[0] + (1-alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1-alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1-alpha) * event.values[2];

            //Remove gravity contribution with the high pass filter
            xAcc = event.values[0] - gravity[0];
            yAcc = event.values[1] - gravity[1];
            zAcc = event.values[2] - gravity[2];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public double getSensor(String request){
        double valor = -1;

        if (request.equalsIgnoreCase("xaccel"))
            valor = xAcc;
        else if (request.equalsIgnoreCase("yaccel"))
            valor = yAcc;
        else if (request.equalsIgnoreCase("yaccel"))
            valor = yAcc;

        return valor;
    }


}
