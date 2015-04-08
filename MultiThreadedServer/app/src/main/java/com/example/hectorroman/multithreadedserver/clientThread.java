package com.example.hectorroman.multithreadedserver;

import android.os.Looper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Created by Hector Roman on 3/15/2015.
 */
public class clientThread extends Thread {
    Socket clientSocket;
//    SensorData sensorOb;
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
                String valor = in.readUTF();

//                double value = mainO


//                sensorOb = new SensorData();


//                double sensorData = in.readDouble();
//                sensorData += sensorOb.getSensor("accelx");
//                sensorData += 50;


//                Log.d("Mensaje","Valor: " + String.valueOf(senData));
//                mainO.Toast(senData);
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
//                out.writeDouble(valor);

//                out.writeDouble(sensorData);

            } catch (Exception e) {

            }
        }
        try {
            clientSocket.close();
        } catch (Exception exc) {

        }
    }
}
