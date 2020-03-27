package sample;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnection{

    private int port = 7789;
    private String host = "localhost";
    private Socket socket;

    private String input = "";
    private String response = "";

    private OutputListener outputListener;
    private InputListener inputListener;        //InputListener is een Runnable


    public ServerConnection() {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            System.out.println("Connection Failed.");
            e.printStackTrace();
        }
        if (socket != null) {
            inputListener = new InputListener(socket);
            Thread responseThrd = new Thread(inputListener);
            responseThrd.start();

            outputListener = new OutputListener(socket);
        }
    }

    public void sendCommand(String str){
        outputListener.sendCommand(str);
    }

    public String showResponse(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e){
            System.out.println("Fail to sleep");
        }
        return inputListener.getLastResponse();
    }

}
