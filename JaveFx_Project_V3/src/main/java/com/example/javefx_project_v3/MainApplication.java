package com.example.javefx_project_v3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("restaurant-view.fxml"));
        Parent root = fxmlLoader.load();

        // Apply the CSS style
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("ManageClubs.css").toExternalForm());

        stage.setTitle("Restaurant List");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}