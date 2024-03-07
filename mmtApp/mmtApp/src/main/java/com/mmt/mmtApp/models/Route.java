package com.mmt.mmtApp.models;


import jakarta.persistence.*;
@Entity
@Table(name = "routedata")
public class Route {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_city_id")
    private Long sourceCityId;

    @Column(name = "destination_city_id")
    private Long destinationCityId;

    @Column(name = "mode_of_transport")
    private String modeOfTransport;

    @Column(name = "travel_time")
    private double travelTime;

    @Column(name = "fare")
    private double fare;

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", sourceCityId=" + sourceCityId +
                ", destinationCityId=" + destinationCityId +
                ", mode='" + modeOfTransport + '\'' +
                ", travelTime=" + travelTime +
                ", fare=" + fare +
                '}';
    }

    public Long getSourceCityId() {
        return sourceCityId;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDestination() {
        return this.destinationCityId;

    }

    public void setDestination(Long destination) {
        this.destinationCityId = destination;
    }

    public Long getSource() {
        return this.sourceCityId;
    }

    public void setSource(Long source) {
        this.sourceCityId = source;
    }

    public double getTravelTimeInHours() {
        return this.travelTime;
    }

    public void setTravelTimeInHours(double travelTimeInHours) {
        this.travelTime = travelTimeInHours;
    }

    public String getMode() {
        return  this.modeOfTransport;
    }

    public void setMode(String mode) {
        this.modeOfTransport = mode;
    }

    public void setFare(double fare) {
       this.fare = fare;
    }


    // Constructors, getters, setters
}