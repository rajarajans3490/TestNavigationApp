package com.example.sample.samplenavigation;


import java.util.ArrayList;

/**
 * Created by Rajarajan Selvaraj on 21/01/17.
 */

public class NavigationData {

 private String id;
 private String name;
 private ArrayList<Place> place;
 private ArrayList<LocationValues> location;

  public NavigationData(){
      id = "";
      name = "";
      place = null;
      location = null;
   }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<Place> getPlace(){
        return place;
    }

    public ArrayList<LocationValues> getLocation(){
        return location;
    }

    public void setPlace(ArrayList<Place> place){
        this.place = place;
    }

    public void setLocation(ArrayList<LocationValues> location){
        this.location = location;
    }

    public class Place {
        private String car;
        private String train;
        public Place(){
            car = "";
            train = "";
        }


        public String getCarValue() {
            return car;
        }
        public void setCarValue(String car) {
            this.car = car;
        }
        public String getTrainValue() {
            return train;
        }
        public void setTrainValue(String train) {
            this.train = train;
        }
    }

    public class LocationValues {
        private Double latitude;
        private Double longitude;
        public LocationValues(){
            latitude = null;
            longitude = null;
        }
        public Double getLatitude() {
            return latitude;
        }
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
        public Double getLongitude() {
            return longitude;
        }
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }

    /*@Override
    public String toString(){
        return "[" + "id : " + getId()+ " name: " + getName() + " fromcentral:" "]";
    }*/
}
