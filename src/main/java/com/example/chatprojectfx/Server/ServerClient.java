package com.example.chatprojectfx.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerClient {
    private static final int PORT = 60666;
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static int guestCounter = 1;

    private List<ClientHandler> ClientList = new ArrayList<>();

    private static String getGuestName() {
        return "Charon " + integerToRoman(guestCounter++);
    }

    private static String integerToRoman(int num) {
        StringBuilder result = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                result.append(symbols[i]);
                num -= values[i];
            }
        }
        return result.toString();
    }



    public static void main(String[] args) throws IOException {
        System.out.println("Chat server started!");
        ServerSocket serverSocket = new ServerSocket(PORT);

        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected" + clientSocket.getInetAddress());
            ClientHandler clientHandler = new ClientHandler(clientSocket,clientWriters,getGuestName());
            new Thread(clientHandler).start();
        }
    }
}
