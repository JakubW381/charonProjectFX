package com.example.chatprojectfx.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String guestName;
    private Set<PrintWriter> clientWriters;

    public ClientHandler(Socket socket, Set<PrintWriter> clientWriters,String guestName){
        this.socket = socket;
        this.clientWriters = clientWriters;
        this.guestName = guestName;
    }

    @Override
    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);

            synchronized (clientWriters){
                clientWriters.add(out);
            }

            String message;
            while((message = in.readLine()) != null){
                String formattedMessage = guestName + ": " + message;
                synchronized (clientWriters){
                    for (PrintWriter writer : clientWriters){
                        writer.println(formattedMessage);
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            synchronized (clientWriters){
                clientWriters.remove(out);
            }
        }
    }
}
