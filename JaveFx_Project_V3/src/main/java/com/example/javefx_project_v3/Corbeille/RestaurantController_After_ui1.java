package com.example.javefx_project_v3.Corbeille;

import com.example.javefx_project_v3.Entitys.Restaurant;
import com.example.javefx_project_v3.Services.ServiceRestaurant;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.sql.SQLException;

public class RestaurantController_After_ui1 {

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
    private TableColumn<Restaurant, Long> nomColumn;

    @FXML
    private TableColumn<Restaurant, String> adresseColumn;

    @FXML
    private TableColumn<Restaurant, String> descriptionColumn;

    @FXML
    private TableColumn<Restaurant, Double> noteMoyenneColumn;

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

        // Set cell factory to customize each cell in the TableView
        restaurantTableView.setRowFactory(param -> new RestaurantTableRow());

        // Set data to the TableView
        restaurantTableView.setItems(restaurantList);

        // Set cell value factories for each column
        nomColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRestaurantID()));
        adresseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomRestaurant()));
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdresseRestaurant()));
        noteMoyenneColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNoteMoyenne()));
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
            // Refresh the TableView after deletion
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
            // Refresh the TableView after addition
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
