package com.example.javefx_project_v3.Entitys;

public class Users {
    public int UserID ;
    public String Nom ;
    public String Prenom ;
    public String Email ;
    public  String MotDePasse ;
    public TypeUSer TypeUtilisateur ;

    public Users(int userID, String nom, String prenom, String email, String motDePasse, TypeUSer typeUtilisateur) {
        UserID = userID;
        Nom = nom;
        Prenom = prenom;
        Email = email;
        MotDePasse = motDePasse;
        TypeUtilisateur = typeUtilisateur;
    }

    public Users() {

    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public TypeUSer getTypeUtilisateur() {
        return TypeUtilisateur;
    }

    public void setTypeUtilisateur(TypeUSer typeUtilisateur) {
        TypeUtilisateur = typeUtilisateur;
    }

    @Override
    public String toString() {
        return "Users{" +
                "UserID=" + UserID +
                ", Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Email='" + Email + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                ", TypeUtilisateur='" + TypeUtilisateur + '\'' +
                '}';
    }
}
