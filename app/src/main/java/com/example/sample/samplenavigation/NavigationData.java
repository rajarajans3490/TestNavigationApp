package com.example.sample.samplenavigation;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Rajarajan Selvaraj on 21/01/17.
 */

public class NavigationData {

 @SerializedName("id")
 private String id;
 @SerializedName("name")
 private String name;
 @SerializedName("fromcentral")
 private Place place;
 @SerializedName("location")
 private LocationValues location;

  public NavigationData(){
      id = "";
      name = "";
      place = new Place();
      location = new LocationValues();
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

    public Place getPlace(){
        return place;
    }

    public LocationValues getLocation(){
        return location;
    }

    public void setPlace(Place place){
        this.place = place;
    }

    public void setLocation(LocationValues location){
        this.location = location;
    }

    public class Place {
        @SerializedName("car")
        private String car;
        @SerializedName("train")
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

        @Override
        public String toString(){
            return "[" + "car : " + getCarValue()+ " train: " + getTrainValue() + "]";
        }
    }

    public class LocationValues {
        @SerializedName("latitude")
        private Double latitude;
        @SerializedName("longitude")
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

        @Override
        public String toString(){
            return "[" + "latitude : " + getLatitude()+ " longitude: " + getLongitude() + "]";
        }
    }

    @Override
    public String toString(){
        return "[" + "id : " + getId()+ " name: " + getName() + " fromcentral: " + getPlace() + " location: " + getLocation()+ "]";
    }
}
