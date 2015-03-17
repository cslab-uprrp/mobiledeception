package com.chamas.luis.server;

/**
 * Created by Luis on 3/17/2015.
 */
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Hector Roman on 3/15/2015.
 */
public class clientThread extends Thread {
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
                double sensorData = in.readDouble();
//                Toast.makeText(mainO.getApplicationContext(), "Sensor Data: " + String.valueOf(sensorData), Toast.LENGTH_LONG).show();
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
}