package com.example.javefx_project_v3.Entitys;

public class LoggedIn {

    private int UserID ;
    private String Nom ;
    private String Prenom ;
    private String Email ;
    private  String MotDePasse ;
    private TypeUSer TypeUtilisateur ;

    public LoggedIn(int userID, String nom, String prenom, String email,String motDePasse, TypeUSer typeUtilisateur) {
        UserID = userID;
        Nom = nom;
        Prenom = prenom;
        Email = email;
        MotDePasse = motDePasse;
        TypeUtilisateur = typeUtilisateur;
    }

    public int getUserID() {
        return UserID;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getEmail() {
        return Email;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public TypeUSer getTypeUtilisateur() {
        return TypeUtilisateur;
    }


    // Add other user-related fields

    // Constructors, getters, and setters
}