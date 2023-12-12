package Entitys;

public class Plat {
    int id ;
    String nom;
    String description ;
    int restaurantID;

    public Plat(int id,int RestaurantId , String nom, String description){
        this.id =  id ;
        this.nom = nom;
        this.description = description;
        this.restaurantID = RestaurantId;

    }

    public int getId(){
        return this.id ;
    }
    public String getNom(){
        return this.nom;
    }
    public int getRestaurantID(){
        return 1;
    }
    public String getDescription(){
        return this.description;
    }
    void setId(int id ){
        this.id = id ;
    }
    void setNom(String nom){
        this.nom = nom;
    }
    void setDescription(String description){
        this.description = description;
    }
}
