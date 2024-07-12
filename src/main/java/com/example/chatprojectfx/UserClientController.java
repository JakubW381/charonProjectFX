package com.example.chatprojectfx;

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