package com.chamas.luis.server;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Luis on 3/19/2015.
 */
public class serverService extends Service implements SensorEventListener{
    private SensorManager mSensorMan;
    private Sensor mAccel;
    public boolean serverOn = true;
    double mSensorX, mSensorY, mSensorZ;
    public serverService(){

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(this,"Server Service Created", Toast.LENGTH_SHORT).show();
        mSensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccel = mSensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "Server Started", Toast.LENGTH_SHORT).show();
        Server server = new Server(this);
        server.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        serverOn = false;
        Toast.makeText(this, "Server Stopped", Toast.LENGTH_SHORT).show();
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
            mSensorX = event.values[0] - gravity[0];
            mSensorY = event.values[1] - gravity[1];
            mSensorZ = event.values[2] - gravity[2];
        }
    }

    public String getSense(String some){
        String sensor="";
        if(some.equalsIgnoreCase("AccelX")){
            sensor = String.valueOf(mSensorX);
        }else if(some.equalsIgnoreCase("AccelY")){
            sensor = String.valueOf(mSensorY);
        }else if(some.equalsIgnoreCase("AccelZ")){
            sensor = String.valueOf(mSensorZ);
        }

        return sensor;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
