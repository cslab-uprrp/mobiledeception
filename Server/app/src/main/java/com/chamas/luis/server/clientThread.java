package com.chamas.luis.server;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Luis on 3/19/2015.
 */
public class clientThread extends Thread implements SensorEventListener {
    Socket clientSocket;
    serverService mainO;



    public clientThread(Socket sock, serverService mainOb){
        clientSocket = sock;
        mainO = mainOb;
    }

    @Override
    public void run() {
        super.run();
        while(mainO.serverOn) {
            try {
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                String sensorData = in.readUTF();
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                out.writeUTF(mainO.getSense(sensorData));

//               Toast.makeText(mainO.getApplicationContext(), "Sensor Data: " + String.valueOf(sensorData), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
//                Toast.makeText(mainO.getApplicationContext(), "Error Reading From Client", Toast.LENGTH_LONG).show();
            }
        }
        try {
            clientSocket.close();
        } catch (Exception exc) {
//            Toast.makeText(mainO.getApplicationContext(),"Error Terminating Client Connection" , Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}