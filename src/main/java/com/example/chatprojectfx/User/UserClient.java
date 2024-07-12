package com.example.chatprojectfx.User;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;

public class UserClient extends Application {
    private TextArea messageTextArea;
    private PrintWriter out;
    private BufferedReader in;

    @Override
    public void start(Stage stage) throws IOException {
        connectToServer();
        messageTextArea = new TextArea();
        messageTextArea.setEditable(false);

        TextField inputField = new TextField();
        Button sendButton = new Button("Send");

        sendButton.setOnAction(event -> {
            String message = inputField.getText();
            out.println(message);
            inputField.clear();
        });

        GridPane pane = new GridPane();
        pane.add(messageTextArea, 0, 0, 3, 1);
        pane.add(inputField, 0, 1);
        pane.add(sendButton, 1, 1);

        Scene scene = new Scene(pane, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Chat Client");
        stage.show();
    }

    private void connectToServer(){
        String ip = getUserIP();
        int port = 60666;
        new Thread(() -> {
            try{Socket socket = new Socket(ip,port);
                out = new PrintWriter(socket.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String message;
                while((message = in.readLine()) != null){
                    final String msgToDisplay = message;
                    Platform.runLater(() -> messageTextArea.appendText(msgToDisplay + "\n"));
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }).start();
    }
    private String getUserIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "IP not found";
        }
    }


    public static void main(String[] args) {
        launch();
    }
}