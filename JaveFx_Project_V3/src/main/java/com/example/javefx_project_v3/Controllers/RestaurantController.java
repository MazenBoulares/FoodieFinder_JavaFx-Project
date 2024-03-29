package com.example.javefx_project_v3.Controllers;

import com.example.javefx_project_v3.Entitys.CurrentLoggedIn;
import com.example.javefx_project_v3.Entitys.Restaurant;
import com.example.javefx_project_v3.MainApplication;
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
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import java.io.IOException;
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



    @FXML
    private Text authName;

    @FXML
    private Text authuserType;




    private final ServiceRestaurant serviceRestaurant = new ServiceRestaurant();

    private final CurrentLoggedIn currentLoggedIn = CurrentLoggedIn.getInstance();

    @FXML
    public void initialize() {
//        System.out.println("inisde");
//
//
//        System.out.println(currentLoggedIn.getLoggedIn().getEmail());
//        System.out.println(currentLoggedIn.getLoggedIn().getTypeUtilisateur());

        authName.setText( currentLoggedIn.getLoggedIn().getPrenom()+" "+currentLoggedIn.getLoggedIn().getNom());

        authuserType.setText(String.valueOf(currentLoggedIn.getLoggedIn().getTypeUtilisateur()));



        ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

//        try {
//            restaurantList.addAll(serviceRestaurant.readAll());
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle the exception appropriately
//        }

        try {
            // Assuming that you have a method to get the managerID from the logged-in user
            int managerID = currentLoggedIn.getLoggedIn().getUserID();
            restaurantList.addAll(serviceRestaurant.getRestaurantsByManagerID(managerID));
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
            private final Button deleteButton = new Button("❌");


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
        if (validateInput()) {
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
    }

    @FXML
    private void handleUpdateRestaurant() {
        if (validateInput()) {
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

    private boolean validateInput() {
        if (nomTextField.getText().isEmpty() || adresseTextField.getText().isEmpty() ||
                descriptionTextField.getText().isEmpty() || noteMoyenneTextField.getText().isEmpty()) {
            showAlert("Error", "All fields must be filled!");
            return false;
        }

        // Check if nom contains only characters
        if (!nomTextField.getText().matches("[a-zA-Z]+")) {
            showAlert("Error", "Nom must contain only characters!");
            return false;
        }

        try {
            double noteMoyenne = Double.parseDouble(noteMoyenneTextField.getText());

            // Check if noteMoyenne is between 0 and 10
            if (noteMoyenne < 0 || noteMoyenne > 10) {
                showAlert("Error", "Note must be between 0 and 10!");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Note must be a valid number!");
            return false;
        }

        // Additional validation checks can be added here

        return true;
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }




    //********************************navbar******************************************

    @FXML
    private void handleEvaluateRestaurantsButtonAction() throws IOException {
        // Perform login logic

        // If login is successful, navigate to the home page
        MainApplication.showEvaluateRestaurantPage();
    }




    @FXML
    private void handleManageRestaurantsButtonAction() throws IOException {
        // Perform login logic

        // If login is successful, navigate to the home page
        MainApplication.showRestaurantPage();
    }


    @FXML
    private void handleLogout() throws IOException {
        // Perform login logic

        // If login is successful, navigate to the home page
        currentLoggedIn.setLoggedIn(null);
        MainApplication.showLoginPage();
    }






    //********************************navbar******************************************


}
