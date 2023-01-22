package com.driver.model;


import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table
public class Cab{
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int perKmRate;


    boolean available;

    @OneToOne
    @JoinColumn
    Driver driver;

    public Cab(int perKmRate, boolean available) {
        this.perKmRate=perKmRate;
        this.available=available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Cab() {
    }



    public int getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}