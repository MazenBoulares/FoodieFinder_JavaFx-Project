package Entitys;

public class Restaurant {
    private Long restaurantID;
    private String nomRestaurant;
    private String adresseRestaurant;
    private String description;

    private boolean isApproved;
    private Double noteMoyenne;


    private User manager;

    



    public Restaurant() {

    }

    public Restaurant(String nomRestaurant, String adresseRestaurant, String description, Double noteMoyenne
//                      User user

    ) {
        this.nomRestaurant = nomRestaurant;
        this.adresseRestaurant = adresseRestaurant;
        this.description = description;
        this.noteMoyenne = noteMoyenne;
        this.isApproved=false;
       // this.manager=user;
    }


    public Restaurant(Long restaurantID, String nomRestaurant, String adresseRestaurant, String description, Double noteMoyenne) {
        this.restaurantID=restaurantID;
        this.nomRestaurant = nomRestaurant;
        this.adresseRestaurant = adresseRestaurant;
        this.description = description;
        this.noteMoyenne = noteMoyenne;
      this.isApproved=false;
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

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
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
