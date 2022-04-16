package com.example.myaddressbook_2301908343.responses;

import com.example.myaddressbook_2301908343.models.Employee;

public class JSONResponse {

    private Employee[] employees;

    public Employee[] getEmployees() {
        return employees;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }
}
