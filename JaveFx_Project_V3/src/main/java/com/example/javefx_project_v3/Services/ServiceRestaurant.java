package com.example.javefx_project_v3.Services;

import com.example.javefx_project_v3.Entitys.CurrentLoggedIn;
import com.example.javefx_project_v3.Entitys.Restaurant;
import com.example.javefx_project_v3.Entitys.User;
import com.example.javefx_project_v3.Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
public class ServiceRestaurant implements IServiceResto<Restaurant> {
    Connection con = DataSource.getInstance().getCon();
    private Statement ste;

    private final CurrentLoggedIn currentLoggedIn = CurrentLoggedIn.getInstance();

   private IService us;

    public ServiceRestaurant() {
        try {

            us= new ServiceUsers();

            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//    @Override
//    public void ajouter(Restaurant restaurant) throws SQLException {
//        String sql = "INSERT INTO Restaurants (NomRestaurant, AdresseRestaurant, Description, NoteMoyenne) VALUES (?, ?, ?, ?)";
//
//        try (PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setString(1, restaurant.getNomRestaurant());
//            preparedStatement.setString(2, restaurant.getAdresseRestaurant());
//            preparedStatement.setString(3, restaurant.getDescription());
//            preparedStatement.setDouble(4, restaurant.getNoteMoyenne());
//
//            preparedStatement.executeUpdate();
//
//            // Récupérer l'ID généré automatiquement
//            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    restaurant.setRestaurantID(generatedKeys.getLong(1));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle the exception appropriately
//        }
//    }


    @Override
    public void ajouter(Restaurant restaurant) throws SQLException {
        String sql = "INSERT INTO Restaurants (NomRestaurant, AdresseRestaurant, Description, NoteMoyenne, ManagerID) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, restaurant.getNomRestaurant());
            preparedStatement.setString(2, restaurant.getAdresseRestaurant());
            preparedStatement.setString(3, restaurant.getDescription());
            preparedStatement.setDouble(4, restaurant.getNoteMoyenne());



            preparedStatement.setLong(5, currentLoggedIn.getLoggedIn().getUserID()); // Assuming getId() returns the user ID

            preparedStatement.executeUpdate();

            // Récupérer l'ID généré automatiquement
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    restaurant.setRestaurantID(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {

            System.out.println("exception dans l'ajout service");
            e.printStackTrace(); // Handle the exception appropriately
        }
    }







    @Override
    public void update(Restaurant restaurant) throws SQLException {
        String sql = "UPDATE Restaurants SET NomRestaurant=?, AdresseRestaurant=?, Description=?, NoteMoyenne=? WHERE RestaurantID=?";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, restaurant.getNomRestaurant());
            preparedStatement.setString(2, restaurant.getAdresseRestaurant());
            preparedStatement.setString(3, restaurant.getDescription());
            preparedStatement.setDouble(4, restaurant.getNoteMoyenne());
            preparedStatement.setLong(5, restaurant.getRestaurantID());

            System.out.println("id= "+restaurant.getRestaurantID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Restaurants WHERE RestaurantID=?";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @Override
    public ArrayList<Restaurant> readAll() throws SQLException {
        ArrayList<Restaurant> list = new ArrayList<>();
        try {
            ResultSet resultSet = ste.executeQuery("SELECT * FROM Restaurants");
            while (resultSet.next()) {
                long id = resultSet.getLong("RestaurantID");
                String nom = resultSet.getString("NomRestaurant");
                String adresse = resultSet.getString("AdresseRestaurant");
                String description = resultSet.getString("Description");
                double noteMoyenne = resultSet.getDouble("NoteMoyenne");
                int isApproved = resultSet.getInt("IsApproved");

                User manager= us.get(resultSet.getInt("ManagerID"));
if(manager!=null){
                 System.out.println("this is da manager"+ manager.getPrenom());}

                Restaurant restaurant = new Restaurant(id,nom, adresse, description, noteMoyenne, isApproved, manager);
                list.add(restaurant);


            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    @Override
    public Restaurant get(int id) throws SQLException {
        String sql = "SELECT * FROM Restaurants WHERE RestaurantID=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long restaurantID = resultSet.getLong("RestaurantID");
                String nom = resultSet.getString("NomRestaurant");
                String adresse = resultSet.getString("AdresseRestaurant");
                String description = resultSet.getString("Description");
                double noteMoyenne = resultSet.getDouble("NoteMoyenne");

                return new Restaurant(restaurantID,nom, adresse, description, noteMoyenne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public void acceptRestaurant(Restaurant restaurant) throws SQLException {
        String sql = "UPDATE Restaurants SET IsApproved=? WHERE RestaurantID=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1); // 1 represents accepted status
            preparedStatement.setLong(2, restaurant.getRestaurantID());
            preparedStatement.executeUpdate();
        }
    }

    public void refuseRestaurant(Restaurant restaurant) throws SQLException {
        String sql = "UPDATE Restaurants SET IsApproved=? WHERE RestaurantID=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, 2); // 2 represents refused status
            preparedStatement.setLong(2, restaurant.getRestaurantID());
            preparedStatement.executeUpdate();
        }
    }


}
