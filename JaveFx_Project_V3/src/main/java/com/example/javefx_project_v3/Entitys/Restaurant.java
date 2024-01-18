package com.example.javefx_project_v3.Entitys;

public class Restaurant {
    public Long restaurantID;
    public String nomRestaurant;
    public String adresseRestaurant;
    public String description;

    public int isApproved;
    public Double noteMoyenne;


    private User manager;

    



    public Restaurant() {

    }

    public Restaurant(String nomRestaurant, String adresseRestaurant, String description, Double noteMoyenne
//                   User user

    ) {
        this.nomRestaurant = nomRestaurant;
        this.adresseRestaurant = adresseRestaurant;
        this.description = description;
        this.noteMoyenne = noteMoyenne;
        this.isApproved=0;
       // this.manager=user;
    }


    public Restaurant(Long restaurantID, String nomRestaurant, String adresseRestaurant, String description, Double noteMoyenne) {
        this.restaurantID=restaurantID;
        this.nomRestaurant = nomRestaurant;
        this.adresseRestaurant = adresseRestaurant;
        this.description = description;
        this.noteMoyenne = noteMoyenne;
      this.isApproved=0;
    }




    public Restaurant(Long restaurantID, String nomRestaurant, String adresseRestaurant, String description, Double noteMoyenne, int isApproved) {
        this.restaurantID=restaurantID;
        this.nomRestaurant = nomRestaurant;
        this.adresseRestaurant = adresseRestaurant;
        this.description = description;
        this.noteMoyenne = noteMoyenne;
        this.isApproved= isApproved;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Restaurant(Long restaurantID, String nomRestaurant, String adresseRestaurant, String description, Double noteMoyenne, int isApproved, User manager) {
        this.restaurantID=restaurantID;
        this.nomRestaurant = nomRestaurant;
        this.adresseRestaurant = adresseRestaurant;
        this.description = description;
        this.noteMoyenne = noteMoyenne;
        this.isApproved= isApproved;
        this.manager= manager;
    }



    public Long getRestaurantID() {
        //return restaurantID;
        return restaurantID != null ? restaurantID : 0L;
    }

    public void setRestaurantID(Long restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getNomRestaurant() {
        return nomRestaurant;
    }

    public void setNomRestaurant(String nomRestaurant) {
        this.nomRestaurant = nomRestaurant;
    }

    public String getAdresseRestaurant() {
        return adresseRestaurant;
    }

    public void setAdresseRestaurant(String adresseRestaurant) {
        this.adresseRestaurant = adresseRestaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getNoteMoyenne() {
        return noteMoyenne != null ? noteMoyenne : 0.0;
    }

    public void setNoteMoyenne(Double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int x) {
        isApproved = x;
    }



    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantID=" + restaurantID +
                ", nomRestaurant='" + nomRestaurant + '\'' +
                ", adresseRestaurant='" + adresseRestaurant + '\'' +
                ", description='" + description + '\'' +
                ", isApproved=" + isApproved +
                ", noteMoyenne=" + noteMoyenne +
                '}';
    }
}
