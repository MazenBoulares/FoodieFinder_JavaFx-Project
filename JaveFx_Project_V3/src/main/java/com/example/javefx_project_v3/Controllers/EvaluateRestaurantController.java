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
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.io.IOException;
import java.sql.SQLException;

public class EvaluateRestaurantController {

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
    private TableColumn<Restaurant, String> managerColumn;

    @FXML
    private TableColumn<Restaurant, Void> operationsColumn;




    private final ServiceRestaurant serviceRestaurant = new ServiceRestaurant();

    @FXML
    private Text authName;

    @FXML
    private Text authuserType;


    private final CurrentLoggedIn currentLoggedIn = CurrentLoggedIn.getInstance();

    @FXML
    public void initialize() {

        authName.setText( currentLoggedIn.getLoggedIn().getPrenom()+" "+currentLoggedIn.getLoggedIn().getNom());

        authuserType.setText(String.valueOf(currentLoggedIn.getLoggedIn().getTypeUtilisateur()));



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
        noteMoyenneColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getNoteMoyenne()));



        managerColumn.setCellValueFactory(data -> {
            return new SimpleObjectProperty<>(data.getValue().getManager().getPrenom()+" "+data.getValue().getManager().getNom() );
        });
        // Inside your EvaluateRestaurantController class

// ...

        operationsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button inProgressButton = new Button("In Progress");
            private final Button acceptButton = new Button("Accepted");
            private final Button refuseButton = new Button("Refused");

            {
                inProgressButton.getStyleClass().add("in-progress-button");
                acceptButton.getStyleClass().add("accept-button");
                refuseButton.getStyleClass().add("refuse-button");

                inProgressButton.setOnAction(event -> {
                    Restaurant restaurant = getTableView().getItems().get(getIndex());
                    showApprovalPopup(restaurant);
                });

                acceptButton.setOnAction(event -> {
                    Restaurant restaurant = getTableView().getItems().get(getIndex());
                    handleAcceptRestaurant(restaurant);
                });

                refuseButton.setOnAction(event -> {
                    Restaurant restaurant = getTableView().getItems().get(getIndex());
                    handleRefuseRestaurant(restaurant);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    int approvalStatus = getTableView().getItems().get(getIndex()).isApproved;
                    System.out.println(getTableView().getItems().get(getIndex()).isApproved);

                    switch (approvalStatus) {
                        case 0:
                            setGraphic(inProgressButton);
                            break;
                        case 1:
                            setGraphic(acceptButton);
                            break;
                        case 2:
                            setGraphic(refuseButton);
                            break;
                        default:
                            setGraphic(null);
                    }
                }
            }


        });



        restaurantTableView.getSelectionModel()
                .selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        handleRestaurantSelection(newSelection);
                    }
                });
    }

    private void showApprovalPopup(Restaurant restaurant) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Approval Confirmation");
        alert.setHeaderText("Do you want to accept or refuse the restaurant?");
        alert.setContentText("Choose your option:");

        ButtonType acceptButton = new ButtonType("Accept");
        ButtonType refuseButton = new ButtonType("Refuse");

        alert.getButtonTypes().setAll(acceptButton, refuseButton);

        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait().ifPresent(result -> {
            if (result == acceptButton) {
                handleAcceptRestaurant(restaurant);
            } else if (result == refuseButton) {
                handleRefuseRestaurant(restaurant);
            }
        });
    }

    private void handleAcceptRestaurant(Restaurant restaurant) {
        try {
            serviceRestaurant.acceptRestaurant(restaurant);
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleRefuseRestaurant(Restaurant restaurant) {
        try {
            serviceRestaurant.refuseRestaurant(restaurant);
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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



    //********************************navbar******************************************

    @FXML
    private void handleEvaluateRestaurantsButtonAction() throws IOException {
        MainApplication.showEvaluateRestaurantPage();
    }

    @FXML
    private void handleManageRestaurantsButtonAction() throws IOException {
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
