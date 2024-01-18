package com.example.javefx_project_v3.Services;

import com.example.javefx_project_v3.Entitys.TypeUSer;
import com.example.javefx_project_v3.Entitys.User;
import com.example.javefx_project_v3.Utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ServiceUsers implements com.example.javefx_project_v3.Services.IService<User> {
    private Connection con= DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceUsers()
    {
        try {
            ste = con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void ajouter(User users) throws SQLException
    {
        String req="INSERT INTO users (`UserID`, `Nom`, `Prenom`,`Email`,  `MotDePasse`, `TypeUtilisateur`)" +
                " VALUES (NULL, '"+users.getNom()+"', '"+users.getPrenom()+"', '"+users.getEmail()+"','"+users.getMotDePasse()+"','"+users.getTypeUtilisateur()+"');";
        System.out.println(req);
        int res=   ste.executeUpdate(req);
    }
    @Override
    public void update(User users) throws SQLException
    {
        String req = "UPDATE users SET Nom = '" + users.getNom() + "',  " + "Prenom = '" + users.getPrenom() + "', " +
                "Email = '" + users.getEmail() +"', " +"MotDePasse = '" + users.getMotDePasse() +"', " +"TypeUtilisateur = '"+users.getTypeUtilisateur() +

                "' WHERE UserID = " + users.getUserID();
        System.out.println(req);
        int res = ste.executeUpdate(req);
    }


    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM `users` WHERE 'UserID' = " + id;
        int res = ste.executeUpdate(req);
    }
    @Override
    public ArrayList<User> consulterAll() throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        try {
            ResultSet resultSet = ste.executeQuery("select * from users");
            while (resultSet.next()) {
                int UserID = resultSet.getInt("UserID");
                String Nom  = resultSet.getString(2);
                String Prenom  = resultSet.getString(3);
                String Email  = resultSet.getString(4);
                String MotDePasse  = resultSet.getString(5);
                TypeUSer TypeUtilisateur  = TypeUSer.valueOf(resultSet.getString(6));
                User p = new User(UserID, Nom, Prenom, Email, MotDePasse, TypeUtilisateur);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }


    @Override
    public User get(int id) throws SQLException {
        String req = "SELECT * FROM users WHERE UserID = " + id;
        ResultSet resultSet = ste.executeQuery(req);
        if (resultSet.next()) {
            int UserID = resultSet.getInt("UserID");
            String Nom = resultSet.getString(2);
            String Prenom = resultSet.getString(3);
            String Email = resultSet.getString(4);
            String MotDePasse = resultSet.getString(5);
            TypeUSer TypeUtilisateur = TypeUSer.valueOf(resultSet.getString(6));
            User p = new User(UserID, Nom, Prenom, Email, MotDePasse, TypeUtilisateur);
            return p;
        }
        return null;
    }
    public User getUserByEmail(String email) throws SQLException {
        String req = "SELECT * FROM users WHERE Email = '" + email + "'";
        System.out.println(req);

        ResultSet resultSet = ste.executeQuery(req);

        if (resultSet.next()) {
            User user = new User();
            user.setUserID(resultSet.getInt("UserID"));
            user.setNom(resultSet.getString("Nom"));
            user.setPrenom(resultSet.getString("Prenom"));
            user.setEmail(resultSet.getString("Email"));
            user.setMotDePasse(resultSet.getString("MotDePasse"));
            String tu = resultSet.getString("TypeUtilisateur");
            if(tu.equals("Admin")){
                user.setTypeUtilisateur(TypeUSer.Admin);
            }else
                user.setTypeUtilisateur(TypeUSer.Client);
            return user;
        } else {
            // No user found with the given email
            return null;
        }
    }

}
