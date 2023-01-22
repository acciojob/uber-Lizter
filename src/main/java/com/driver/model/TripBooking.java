package com.driver.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

public class TripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tripBookingId;

    private String fromLocation;

    private String toLocation;

    private int distanceInKm;

    @Enumerated(value= EnumType.STRING)
    private String TripStatus;

    private int bill;

    @Enumerated(value = EnumType.STRING)
    private TripStatus tripStatus;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Driver driver;


    public TripBooking() {
    }

    public TripBooking(int tripBookingId, String fromLocation, String toLocation, int distanceInKm, String tripStatus, int bill, Customer customer, Driver driver) {
        this.tripBookingId = tripBookingId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
        TripStatus = tripStatus;
        this.bill = bill;
        this.customer = customer;
        this.driver = driver;
    }

    public TripBooking(String toLocation, String fromLocation, int distanceInKm, TripStatus tripStatus) {
        this.toLocation = toLocation;
        this.fromLocation = fromLocation;
        this.distanceInKm = distanceInKm;
        this.tripStatus = tripStatus;
    }


    public int getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(int tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(int distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public String getTripStatus() {
        return TripStatus;
    }

    public void setTripStatus(String tripStatus) {
        TripStatus = tripStatus;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}