package com.mmt.mmtApp.service;

public class CityEdge {
    private Long destinationCityId;
    private double travelTime; // Time in hours
    private double fare;

    public String mode;

    public CityEdge(){

        this.mode = "No";

    }

    public CityEdge(Long destinationCityId, double travelTime, double fare) {
        this.destinationCityId = destinationCityId;
        this.travelTime = travelTime;
        this.fare = fare;

    }

    public CityEdge(Long destinationCityId, double travelTime, double fare,String mode) {
        this.destinationCityId = destinationCityId;
        this.travelTime = travelTime;
        this.fare = fare;
        this.mode = mode;

    }
    public Long getDestinationCityId() {
        return this.destinationCityId;
    }

    public double getTravelTime() {
        return this.travelTime;
    }

    public double getFare() {
        return this.fare;
    }

    public String getMode() {
        return this.mode;
    }

    public void setDestinationCityId(Long nextCityId) {
        this.destinationCityId = nextCityId;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    // Getters for destinationCityId, travelTime, and fare
}
