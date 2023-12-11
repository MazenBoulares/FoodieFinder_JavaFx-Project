package Services;

import Entitys.Plat;
import Utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicePlat implements IServicePlat<Plat> {

    private Connection con = DataSource.getInstance().getCon();
    private Statement ste;

    public ServicePlat() {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void ajouter(Plat menuItem) throws SQLException {
        String req = "INSERT INTO MenuItems (RestaurantID, NomItem, Description, Prix) VALUES (" +
               // menuItem.getRestaurantID() + ", '" +
                menuItem.getNom() + "', '" +
                menuItem.getDescription() + "', " +
                menuItem.getPrix() + ")";
        ste.executeUpdate(req);
    }



    @Override
    public void update(Plat menuItem) throws SQLException {
        String req = "UPDATE MenuItems SET " +
              //  "RestaurantID=" + menuItem.getRestaurantID() + ", " +
                "NomItem='" + menuItem.getNom() + "', " +
                "Description='" + menuItem.getDescription() + "', " +
                //"Prix=" + menuItem.getPrix() +
                " WHERE MenuItemID=" + menuItem.getId();
        ste.executeUpdate(req);
    }

    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM MenuItems WHERE MenuItemID=" + id;
        ste.executeUpdate(req);
    }

    @Override
    public ArrayList<Plat> readAll() throws SQLException {
        ArrayList<Plat> list = new ArrayList<>();
        try (ResultSet resultSet = ste.executeQuery("SELECT * FROM MenuItems")) {
            while (resultSet.next()) {
                int menuItemID = resultSet.getInt("MenuItemID");
                int restaurantID = resultSet.getInt("RestaurantID");
                String nomItem = resultSet.getString("NomItem");
                String description = resultSet.getString("Description");
                double prix = resultSet.getDouble("Prix");
                Plat menuItem = new Plat(menuItemID, nomItem, description);
                list.add(menuItem);
            }
        }
        return list;
    }

    @Override
    public Plat get(int id) throws SQLException {
        Plat menuItem = null;
        try (ResultSet resultSet = ste.executeQuery("SELECT * FROM MenuItems WHERE MenuItemID=" + id)) {
            if (resultSet.next()) {
                int menuItemID = resultSet.getInt("MenuItemID");
                int restaurantID = resultSet.getInt("RestaurantID");
                String nomItem = resultSet.getString("NomItem");
                String description = resultSet.getString("Description");
                double prix = resultSet.getDouble("Prix");
                menuItem = new Plat(menuItemID, nomItem, description);
            }
        }
        return menuItem;
    }

}
