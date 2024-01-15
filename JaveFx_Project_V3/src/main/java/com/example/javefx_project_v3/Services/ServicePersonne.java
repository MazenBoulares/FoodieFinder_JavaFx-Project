package com.example.javefx_project_v3.Services;//package Services;
//
//import Entitys.Personne;
//
//import java.sql.*;
//import java.util.ArrayList;
//
//public class ServicePersonne implements IServiceResto<Personne> {
//    Connection con = Utils.DataSource.getInstance().getCon();
//    private Statement ste;
//
//
//    public ServicePersonne() {
//        try {
//            ste = con.createStatement();
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }
//
//    @Override
//    public void ajouter(Personne personne) throws SQLException {
//        // Assuming you have a Connection object named "connection"
//        String sql = "INSERT INTO personne (id, nom, prenom, age) VALUES (?, ?, ?, ?)";
//
//        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
//            preparedStatement.setInt(1, personne.getId());
//            preparedStatement.setString(2, personne.getNom());
//            preparedStatement.setString(3, personne.getPrenom());
//            preparedStatement.setInt(4, personne.getAge());
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle the exception appropriately
//        }
//
//    }
//
//    @Override
//    public void update(Personne personne) throws SQLException {
//
//    }
//
//    @Override
//    public void delete(int id) throws SQLException {
//
//    }
//
//    @Override
//    public ArrayList<Personne> readAll() throws SQLException {
//        ArrayList<Personne> list = new ArrayList<>();
//        try {
//            ResultSet resultSet = ste.executeQuery("select * from personne");
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String nom = resultSet.getString(2);
//
//                String prenom = resultSet.getString(3);
//                int age = resultSet.getInt(4);
//                Personne p = new Personne(id, nom, prenom, age);
//                list.add(p);
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//
//
//        return list;
//    }
//
//    @Override
//    public Personne get(int id) throws SQLException {
//        return null;
//    }
//
//
////
////    public void ajouter(Personne per) {
////
////        String req2 = "Insert into personne values(" + per.id + "," + per.prenom + "," + per.nom + "," + per.age + ")";
////
////        try {
////            Statement ste = con.createStatement();
////
////            ste.executeUpdate(req2);
////
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////
////
////    }
////
////
////    public ArrayList<Personne> afficherAll() throws SQLException {
////        ArrayList<Personne>  personnes = new ArrayList<>();
////        Connection conn = Utils.DatabaseConnection.getConnection();
////
////        try {
////            Statement ste = conn.createStatement();
////            String req = "select * from personne";
////            ResultSet res = ste.executeQuery(req);
////
////            while (res.next()) {
////
////                Personne personne = new Personne(
////                        res.getInt("personneId"),
////                        res.getString("nomPersonne"),
////                        res.getString("prenomPersonne"),
////                        res.getInt("agePersonne")
////
////                );
////
////                personnes.add(personne);
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////
////        return personnes;
////
////    }
//
//
//}
