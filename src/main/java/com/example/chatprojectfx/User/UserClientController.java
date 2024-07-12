package com.example.chatprojectfx.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserClientController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}