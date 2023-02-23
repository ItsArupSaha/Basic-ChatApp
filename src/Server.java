package src;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;

class Server{

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Server(){

        try{

            server = new ServerSocket(7000);
            System.out.println("Server is ready to accept request.");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void startReading(){
        Runnable r1 = () -> {

            try {

                System.out.println("Reader Started..");
                while (!socket.isClosed()){
                    String message = br.readLine();
                    if(message.equals("exit")){
                        System.out.println("Client has terminated the chat!!");
                        socket.close();
                        break;
                    }

                    System.out.println("Client: "+message);
                }

            }catch (Exception e){
                // e.printStackTrace();
                System.out.println("Connection is closed");
            }

        };

        new Thread(r1).start();
    }

    public void startWriting(){
        Runnable r2 = () -> {

            System.out.println("Writer started..");
            try{

                while(!socket.isClosed()){

                    BufferedReader text = new BufferedReader(new InputStreamReader(System.in));
                    String content = text.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }

        };

        new Thread(r2).start();
    }

    public static void main(String[] args) throws Exception {

        System.out.println("This is server");
        new Server();
    }
}
