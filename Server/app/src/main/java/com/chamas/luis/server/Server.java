package com.chamas.luis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Luis on 3/19/2015.
 */
public class Server extends Thread {
    ServerSocket serverSocket;
    serverService object;

    public Server(serverService obj){
        object = obj;
    }

    @Override
    public void run(){
        super.run();
        try{
            serverSocket = new ServerSocket(8000);
        }catch (IOException e){
            e.printStackTrace();
        }

        while(object.serverOn){
            try{
                Socket clientSocket = serverSocket.accept();
                clientThread newClient = new clientThread(clientSocket, object);
                newClient.start();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
