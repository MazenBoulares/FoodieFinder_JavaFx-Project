package com.example.javefx_project_v3.Controllers;

import javafx.fxml.FXML;

import java.io.IOException;

import com.example.javefx_project_v3.MainApplication;

public class LoginController {

    @FXML
    private void handleLoginButtonAction() throws IOException {
        // Perform login logic

        // If login is successful, navigate to the home page
        MainApplication.showRestaurantPage();
    }
}