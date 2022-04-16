package com.example.myaddressbook_2301908343.models;

public class EmployeeLocation {
    private String city;
    private String state;
    private String country;
    private EmployeeLocationCoordinate coordinates;

    public EmployeeLocationCoordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(EmployeeLocationCoordinate coordinates) {
        this.coordinates = coordinates;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
