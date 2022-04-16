package com.example.myaddressbook_2301908343.sessions;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myaddressbook_2301908343.models.Employee;
import com.example.myaddressbook_2301908343.models.EmployeeLocation;
import com.example.myaddressbook_2301908343.models.EmployeeLocationCoordinate;
import com.example.myaddressbook_2301908343.models.EmployeeName;
import com.example.myaddressbook_2301908343.models.EmployeePicture;
import com.example.myaddressbook_2301908343.models.EmployeeRegistered;

import java.util.Set;

public class SharedPreference {
    private SharedPreferences sharedPreferences;

    public SharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public void saveEmployeeTable() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("EmployeeTable", true );
        editor.apply();
    }

    public Boolean loadEmployeeTable() {
        Boolean isExist =sharedPreferences.getBoolean("EmployeeTable", false);
        return isExist;
    }

    public void saveSelectedEmployee(Employee employee) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Employee ID", employee.getEmployeeId());
        editor.putString("Employee Full Name", employee.getEmployeeName().getFirst() + " " +employee.getEmployeeName().getLast());
        editor.putString("Employee First Name", employee.getEmployeeName().getFirst());
        editor.putString("Employee Last Name", employee.getEmployeeName().getLast());
        editor.putString("Employee City", employee.getEmployeeLocation().getCity());
        editor.putString("Employee Country", employee.getEmployeeLocation().getCountry());
        editor.putString("Employee Phone", employee.getPhone());
        editor.putString("Employee Cell", employee.getCell());
        editor.putString("Employee Member", employee.getRegistered().getDate());
        editor.putString("Employee Picture", employee.getEmployeePicture().getMedium());
        editor.putString("Employee Email", employee.getEmail());
        editor.putString("Employee Latitude Coordinate", employee.getEmployeeLocation().getCoordinates().getLatitude());
        editor.putString("Employee Longitude Coordinate", employee.getEmployeeLocation().getCoordinates().getLongitude());
        editor.apply();
    }

    public Employee loadSelectedEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(sharedPreferences.getInt("Employee ID", 0));

        EmployeeName employeeName = new EmployeeName();
        employeeName.setFirst(sharedPreferences.getString("Employee First Name", ""));
        employeeName.setLast(sharedPreferences.getString("Employee Last Name", ""));
        employee.setEmployeeName(employeeName);

        EmployeeLocation employeeLocation= new EmployeeLocation();
        employeeLocation.setCity(sharedPreferences.getString("Employee City", ""));
        employeeLocation.setCountry(sharedPreferences.getString("Employee Country", ""));
        EmployeeLocationCoordinate employeeLocationCoordinate = new EmployeeLocationCoordinate();
        employeeLocationCoordinate.setLatitude(sharedPreferences.getString("Employee Latitude Coordinate", ""));
        employeeLocationCoordinate.setLongitude(sharedPreferences.getString("Employee Longitude Coordinate", ""));
        employeeLocation.setCoordinates(employeeLocationCoordinate);
        employee.setEmployeeLocation(employeeLocation);

        EmployeePicture employeePicture= new EmployeePicture();
        employeePicture.setMedium(sharedPreferences.getString("Employee Picture", ""));
        employee.setEmployeePicture(employeePicture);

        EmployeeRegistered employeeRegistered= new EmployeeRegistered();
        employeeRegistered.setDate(sharedPreferences.getString("Employee Member", ""));
        employee.setRegistered(employeeRegistered);

        employee.setPhone(sharedPreferences.getString("Employee Phone", ""));
        employee.setCell(sharedPreferences.getString("Employee Cell", ""));
        employee.setEmail(sharedPreferences.getString("Employee Email", ""));

        return employee;
    }

}

