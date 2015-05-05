package com.example.hectorroman.multithreadedserver;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class MainActivity extends ActionBarActivity implements SensorEventListener {


    double xAcc, yAcc, zAcc;

    SensorManager sensorMan;
    Sensor accelerometer;

    boolean serverOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
        serverOn = true;
        Toast.makeText(this, "Starting Server Thread", Toast.LENGTH_LONG).show();
        Server newServer = new Server();
        newServer.start();

    }

    public void stopListener(View view) {
        serverOn = false;
        Toast.makeText(this, "Stopping Server Thread", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMan.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        } else {
            double alpha = 0.8;
            double[] gravity = new double[3];

            //Isolate the force of gravity with the low pass filter
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            //Remove gravity contribution with the high pass filter
            xAcc = event.values[0] - gravity[0];
            yAcc = event.values[1] - gravity[1];
            zAcc = event.values[2] - gravity[2];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }




    public class Server extends Thread {
        ServerSocket serverSocket;

        @Override
        public void run() {
            super.run();

            try {
                serverSocket = new ServerSocket(8000);
            } catch (Exception a) {
            }

            while (serverOn) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientThread newClient = new clientThread(clientSocket);
                    newClient.start();
                } catch (Exception b) {
                }

            }
            try {
                serverSocket.close();
            } catch (Exception ex) {

            }
        }
    }

    public class clientThread extends Thread {
        Socket clientSocket;

        public clientThread(Socket sock) {
            clientSocket = sock;

        }

        @Override
        public void run() {
            super.run();
                try {

                    DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                    String valor = in.readUTF();

                    double sensorVal = -1;
                    if (valor.equalsIgnoreCase("accelx"))
                        sensorVal = xAcc;
                    else if(valor.equalsIgnoreCase("accely"))
                        sensorVal = yAcc;
                    else if(valor.equalsIgnoreCase("accelz"))
                        sensorVal = zAcc;

                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                    out.writeDouble(sensorVal);

                } catch (Exception e) {

                }

            try {
                clientSocket.close();
            } catch (Exception exc) {

            }
        }
    }


}
