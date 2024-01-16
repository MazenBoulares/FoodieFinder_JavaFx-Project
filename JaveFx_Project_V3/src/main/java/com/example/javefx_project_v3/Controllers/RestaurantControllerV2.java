package com.example.javefx_project_v3.Controllers;

import com.example.javefx_project_v3.Entitys.Restaurant;
import com.example.javefx_project_v3.Services.ServiceRestaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.sql.SQLException;

public class RestaurantControllerV2 {

    @FXML
    private TableView<Restaurant> restaurantTableView;

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
        ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

        try {
            restaurantList.addAll(serviceRestaurant.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        restaurantTableView.setRowFactory(param -> new RestaurantTableRow());
        restaurantTableView.setItems(restaurantList);
    }

    private class RestaurantTableRow extends javafx.scene.control.TableRow<Restaurant> {

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
            serviceRestaurant.delete(Math.toIntExact(restaurant.getRestaurantID()));
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleRestaurantSelection(Restaurant restaurant) {
        nomTextField.setText(restaurant.getNomRestaurant());
        adresseTextField.setText(restaurant.getAdresseRestaurant());
        descriptionTextField.setText(restaurant.getDescription());
        noteMoyenneTextField.setText(String.valueOf(restaurant.getNoteMoyenne()));
    }

    @FXML
    private void handleAddRestaurant() {
        String nom = nomTextField.getText();
        String adresse = adresseTextField.getText();
        String description = descriptionTextField.getText();
        double noteMoyenne = Double.parseDouble(noteMoyenneTextField.getText());

        Restaurant newRestaurant = new Restaurant(nom, adresse, description, noteMoyenne);

        try {
            serviceRestaurant.ajouter(newRestaurant);
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nomTextField.clear();
        adresseTextField.clear();
        descriptionTextField.clear();
        noteMoyenneTextField.clear();
    }

    @FXML
    private void handleUpdateRestaurant() {
        String nom = nomTextField.getText();
        String adresse = adresseTextField.getText();
        String description = descriptionTextField.getText();
        double noteMoyenne = Double.parseDouble(noteMoyenneTextField.getText());

        Restaurant updatedRestaurant = new Restaurant(nom, adresse, description, noteMoyenne);

        try {
            Restaurant selectedRestaurant = restaurantTableView.getSelectionModel().getSelectedItem();
            if (selectedRestaurant != null) {
                updatedRestaurant.setRestaurantID(selectedRestaurant.getRestaurantID());
                serviceRestaurant.update(updatedRestaurant);
                initialize();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nomTextField.clear();
        adresseTextField.clear();
        descriptionTextField.clear();
        noteMoyenneTextField.clear();
    }
}

