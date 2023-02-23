package src;

import java.io.*;
import java.net.*;

class Client{

    Socket socket;
    BufferedReader br;
    PrintWriter out;


    //constructor
    public Client(){

        try{

            System.out.println("Sending request to server..");
            socket = new Socket("127.0.0.1", 7000);
            System.out.println("Connection done!!");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //start reading(method)
    public void startReading(){
        Runnable r1 = () -> {

            try {

                System.out.println("Reader Started..");
                while (!socket.isClosed()){
                    String message = br.readLine();
                    if(message.equals("exit")){
                        System.out.println("Server has terminated the chat!!");

                        socket.close();
                        break;
                    }

                    System.out.println("Server: "+message);

                }

            }catch (Exception e){
                //e.printStackTrace();
                System.out.println("Connection is closed");
            }

        };

        new Thread(r1).start();
    }

    //start writing(method)
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

    public static void main(String[] args) {
        System.out.println("This is client");
        new Client();
    }
}
