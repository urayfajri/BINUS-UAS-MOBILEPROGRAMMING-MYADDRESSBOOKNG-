package com.example.myaddressbook_2301908343.fragments;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myaddressbook_2301908343.MainActivity;
import com.example.myaddressbook_2301908343.R;
import com.example.myaddressbook_2301908343.adapters.AddressBookAdapter;
import com.example.myaddressbook_2301908343.adapters.EmployeeAdapter;
import com.example.myaddressbook_2301908343.database.DatabaseHelper;
import com.example.myaddressbook_2301908343.models.Employee;
import com.example.myaddressbook_2301908343.models.EmployeeLocation;
import com.example.myaddressbook_2301908343.models.EmployeeLocationCoordinate;
import com.example.myaddressbook_2301908343.models.EmployeeName;
import com.example.myaddressbook_2301908343.models.EmployeePicture;
import com.example.myaddressbook_2301908343.models.EmployeeRegistered;
import com.example.myaddressbook_2301908343.sessions.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class MyAddressBookFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Employee> employeeList;
    private LinearLayout llAddressBookEmpty;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_address_book, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");

        recyclerView = view.findViewById(R.id.rvAddressBooks);
        employeeList = new ArrayList<>();

        llAddressBookEmpty = view.findViewById(R.id.llAddressBookEmpty);

        dbHelper = new DatabaseHelper(this.getContext());

        linearLayoutManager = new LinearLayoutManager(this.getContext());

        getAllAddressBooks();
        if(employeeList.size() == 0) {
            llAddressBookEmpty.setVisibility(View.VISIBLE);
        } else {
            llAddressBookEmpty.setVisibility(View.GONE);
        }

        putDataIntoRecyclerView(employeeList);

        return view;
    }

    private void putDataIntoRecyclerView(List<Employee> employeeList) {
        AddressBookAdapter adapter = new AddressBookAdapter(employeeList, this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getAllAddressBooks() {
        Cursor cursor = dbHelper.getAllAddressBooks();

        if(cursor.getCount() <= 0) {
            return;
        }

        while(cursor.moveToNext()) {
            Employee employee = new Employee();

            employee.setEmployeeId(cursor.getInt(0));

            EmployeeName employeeName = new EmployeeName();
            employeeName.setFirst(cursor.getString(2));
            employeeName.setLast(cursor.getString(3));
            employee.setEmployeeName(employeeName);

            EmployeeLocation employeeLocation= new EmployeeLocation();
            employeeLocation.setCity(cursor.getString(4));
            employeeLocation.setCountry(cursor.getString(5));
            employee.setEmployeeLocation(employeeLocation);


            employee.setPhone(cursor.getString(6));
            employee.setCell(cursor.getString(7));
            employee.setEmail(cursor.getString(8));

            EmployeePicture employeePicture= new EmployeePicture();
            employeePicture.setMedium(cursor.getString(9));
            employee.setEmployeePicture(employeePicture);

            employeeList.add(employee);
        }
    }
}