package com.chamas.luis.server;

/**
 * Created by Luis on 3/17/2015.
 */
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static android.widget.Toast.*;
import static android.widget.Toast.makeText;

/**
 * Created by Hector Roman on 3/15/2015.
 */
public class Server extends Thread {
    ServerSocket serverSocket;
    serverService object;


    public Server(serverService obj){
        object = obj;

    }

    @Override
    public void run() {
        super.run();
        try{
            serverSocket = new ServerSocket(8000);
        }
        catch (Exception a){
//            Toast.makeText(object.getApplicationContext(), "Error Binding Socket", LENGTH_LONG).show();
        }

        while(object.serverOn){
            try {
                Socket clientSocket = serverSocket.accept();
                clientThread newClient = new clientThread(clientSocket, object);
                newClient.start();
            }
            catch(Exception b){
//                Toast.makeText(object.getApplicationContext(), "Error Connecting To Client", LENGTH_LONG).show();
            }

        }
        try {
            serverSocket.close();
        } catch (Exception ex) {
//            Toast.makeText(object.getApplicationContext(),"Error Closing Server Socket", LENGTH_LONG).show();
        }
    }
}