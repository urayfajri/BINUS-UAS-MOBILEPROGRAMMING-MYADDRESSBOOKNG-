package com.example.myaddressbook_2301908343.models;

import com.google.gson.annotations.SerializedName;

public class Employee {

    private int employeeId;
    @SerializedName("name")
    private EmployeeName employeeName;
    @SerializedName("location")
    private EmployeeLocation employeeLocation;
    private String phone;
    private String cell;
    @SerializedName("picture")
    private EmployeePicture employeePicture;
    private EmployeeRegistered registered;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeName getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(EmployeeName employeeName) {
        this.employeeName = employeeName;
    }

    public EmployeeLocation getEmployeeLocation() {
        return employeeLocation;
    }

    public void setEmployeeLocation(EmployeeLocation employeeLocation) {
        this.employeeLocation = employeeLocation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public EmployeePicture getEmployeePicture() {
        return employeePicture;
    }

    public void setEmployeePicture(EmployeePicture employeePicture) {
        this.employeePicture = employeePicture;
    }

    public EmployeeRegistered getRegistered() {
        return registered;
    }

    public void setRegistered(EmployeeRegistered registered) {
        this.registered = registered;
    }
}
