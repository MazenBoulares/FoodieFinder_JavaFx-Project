package com.example.javefx_project_v3.Controllers;

import com.example.javefx_project_v3.Entitys.Restaurant;
import com.example.javefx_project_v3.Services.ServiceRestaurant;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class RestaurantController {

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

    @FXML
    private TableColumn<Restaurant, Long> idColumn;

    @FXML
    private TableColumn<Restaurant, String> nomColumn;

    @FXML
    private TableColumn<Restaurant, String> adresseColumn;

    @FXML
    private TableColumn<Restaurant, String> descriptionColumn;

    @FXML
    private TableColumn<Restaurant, Double> noteMoyenneColumn;

    @FXML
    private TableColumn<Restaurant, Void> operationsColumn;

    private final ServiceRestaurant serviceRestaurant = new ServiceRestaurant();

    @FXML
    public void initialize() {
        ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

        try {
            restaurantList.addAll(serviceRestaurant.readAll());
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        restaurantTableView.setRowFactory(param -> new RestaurantTableRow());

        restaurantTableView.setItems(restaurantList);

        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRestaurantID()));
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomRestaurant()));
        adresseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdresseRestaurant()));
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
        noteMoyenneColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNoteMoyenne()));

        operationsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");


            {
                deleteButton.getStyleClass().add("delete-button");
                deleteButton.setOnAction(event -> {

                    Restaurant restaurant = getTableView().getItems().get(getIndex());
                    handleDeleteRestaurant(restaurant);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        restaurantTableView.getSelectionModel().
                selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        handleRestaurantSelection(newSelection);
                    }
                });
    }

    private class RestaurantTableRow extends javafx.scene.control.TableRow<Restaurant> {

        @Override
        protected void updateItem(Restaurant restaurant, boolean empty) {
            super.updateItem(restaurant, empty);

            if (empty || restaurant == null) {
                setText(null);
                setGraphic(null);
            } else {
                // Handle the display of the row data (if needed)
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
