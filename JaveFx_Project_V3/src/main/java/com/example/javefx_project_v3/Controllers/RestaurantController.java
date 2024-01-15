package com.example.javefx_project_v3.Controllers;

import com.example.javefx_project_v3.Entitys.Restaurant;
import com.example.javefx_project_v3.Services.ServiceRestaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class RestaurantController {

    @FXML
    private ListView<Restaurant> restaurantListView;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField adresseTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField noteMoyenneTextField;

    private final ServiceRestaurant serviceRestaurant = new ServiceRestaurant();

    @FXML
    public void initialize() {
        // Set up your service to get the list of restaurants
        ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

        try {
            // Sample data (replace this with data from your service)
            restaurantList.addAll(serviceRestaurant.readAll());
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Set cell factory to customize each cell in the ListView
        restaurantListView.setCellFactory(param -> new RestaurantListCell());

        // Set data to the ListView
        restaurantListView.setItems(restaurantList);
    }

    // Custom ListCell to display each restaurant with a delete button
    private class RestaurantListCell extends javafx.scene.control.ListCell<Restaurant> {

        @Override
        protected void updateItem(Restaurant restaurant, boolean empty) {
            super.updateItem(restaurant, empty);

            if (empty || restaurant == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox cell = new HBox();
                cell.getStyleClass().add("restaurant-cell");

                Button deleteButton = new Button("Delete");
                deleteButton.getStyleClass().add("delete-button");
                deleteButton.setOnAction(event -> handleDeleteRestaurant(restaurant));

                // Customize the content to display restaurant information
                Button nameLabel = new Button(restaurant.getNomRestaurant());
                nameLabel.getStyleClass().add("restaurant-name-button");
                nameLabel.setOnAction(event -> handleRestaurantSelection(restaurant));

                cell.getChildren().addAll(
                        nameLabel,
                        deleteButton
                );

                setGraphic(cell);
            }
        }
    }

    private void handleDeleteRestaurant(Restaurant restaurant) {
        try {
            // Call your service's delete method
            serviceRestaurant.delete(Math.toIntExact(restaurant.getRestaurantID()));
            // Refresh the ListView after deletion
            initialize();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void handleRestaurantSelection(Restaurant restaurant) {
        // Display selected restaurant information
        nomTextField.setText(restaurant.getNomRestaurant());
        adresseTextField.setText(restaurant.getAdresseRestaurant());
        descriptionTextField.setText(restaurant.getDescription());
        noteMoyenneTextField.setText(String.valueOf(restaurant.getNoteMoyenne()));
    }

    @FXML
    private void handleAddRestaurant() {
        // Handle the add action here
        String nom = nomTextField.getText();
        String adresse = adresseTextField.getText();
        String description = descriptionTextField.getText();
        double noteMoyenne = Double.parseDouble(noteMoyenneTextField.getText());

        Restaurant newRestaurant = new Restaurant(nom, adresse, description, noteMoyenne);

        try {
            serviceRestaurant.ajouter(newRestaurant);
            // Refresh the ListView after addition
            initialize();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Clear the input fields after adding
        nomTextField.clear();
        adresseTextField.clear();
        descriptionTextField.clear();
        noteMoyenneTextField.clear();
    }
}
