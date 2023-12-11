import Entitys.Restaurant;
import Services.ServiceRestaurant;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ServiceRestaurant restaurantService = new ServiceRestaurant();

        // Test ajout resto

        Restaurant restaurantToAdd = new Restaurant("Pizzaria 2","Borj Cedria","bnina",4.5);
        Restaurant restaurantToAdd2 = new Restaurant("MAchwa","jandouba","mele7",3.6);
        Restaurant restaurantToAdd3 = new Restaurant("Pizzaria 4","Bizerte","bnina",48.2);
        Restaurant restaurantToAdd4 = new Restaurant("Sushi","Hlif","bnina",98.5);



        try {
            restaurantService.ajouter(restaurantToAdd);
            restaurantService.ajouter(restaurantToAdd2);
            restaurantService.ajouter(restaurantToAdd3);
            restaurantService.ajouter(restaurantToAdd4);

            System.out.println("ajoutée");
        } catch (SQLException e) {
            System.out.println("Erreur ajout: " + e.getMessage());
        }


        // Test affiche resto
        try {
            ArrayList<Restaurant> restaurants = restaurantService.readAll();
            System.out.println("Liste des restaurants :");
            for (Restaurant restaurant : restaurants) {
                System.out.println(restaurant);
            }
        } catch (SQLException e) {
            System.out.println("Erreur affichage: " + e.getMessage());
        }


        System.out.println("");
        System.out.println("----------------------------------");
        System.out.println("");


        // Test MAJ RESTO


        try {
            Restaurant restaurantToUpdate = restaurantService.get(3);

            System.out.println(restaurantToUpdate.toString());

            restaurantToUpdate.setNomRestaurant("Pizzaria updated");
            restaurantService.update(restaurantToUpdate);
            System.out.println("Restaurant mis à jour");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour: " + e.getMessage());
        }

        System.out.println("");


        // Test affiche resto apres MAJ
        try {
            ArrayList<Restaurant> restaurants = restaurantService.readAll();
            System.out.println("Liste des restaurants apres mise a jour :");
            for (Restaurant restaurant : restaurants) {
                System.out.println(restaurant);
            }
        } catch (SQLException e) {
            System.out.println("Erreur affichage: " + e.getMessage());
        }




        System.out.println("");
        System.out.println("----------------------------------");
        System.out.println("");



        // Test suppression resto
        int restoId = 6;
        try {
            restaurantService.delete(restoId);
            System.out.println("Restaurant supprimé");
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }



        System.out.println("");

        // Test affiche resto apres supression
        try {
            ArrayList<Restaurant> restaurants = restaurantService.readAll();
            System.out.println("Liste des restaurants apres supression :");
            for (Restaurant restaurant : restaurants) {
                System.out.println(restaurant);
            }
        } catch (SQLException e) {
            System.out.println("Erreur affichage: " + e.getMessage());
        }
    }
}
