package com.example.myaddressbook_2301908343.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myaddressbook_2301908343.MainActivity;
import com.example.myaddressbook_2301908343.R;
import com.example.myaddressbook_2301908343.adapters.EmployeeAdapter;
import com.example.myaddressbook_2301908343.database.DatabaseHelper;
import com.example.myaddressbook_2301908343.interfaces.JsonPlaceHolderAPI;
import com.example.myaddressbook_2301908343.models.Employee;
import com.example.myaddressbook_2301908343.models.EmployeeLocation;
import com.example.myaddressbook_2301908343.models.EmployeeLocationCoordinate;
import com.example.myaddressbook_2301908343.models.EmployeeName;
import com.example.myaddressbook_2301908343.models.EmployeePicture;
import com.example.myaddressbook_2301908343.models.EmployeeRegistered;
import com.example.myaddressbook_2301908343.responses.JSONResponse;
import com.example.myaddressbook_2301908343.sessions.SharedPreference;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeSearchFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Employee> employeeList;
    private ProgressBar pbLoading;
    private TextView tvSearchEmployee;
    private EditText etSearchEmployee;
    private ImageView ivSearchEmployee;
    private DatabaseHelper dbHelper;
    private LinearLayout llEmployeeEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employee_search, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Search Employee");

        recyclerView = view.findViewById(R.id.rvEmployees);
        pbLoading = view.findViewById(R.id.pbLoading);
        employeeList = new ArrayList<>();
        tvSearchEmployee = view.findViewById(R.id.tvSearchEmployee);
        etSearchEmployee = view.findViewById(R.id.etSearchEmployee);
        ivSearchEmployee = view.findViewById(R.id.ivSearchEmployee);
        llEmployeeEmpty = view.findViewById(R.id.llEmployeeEmpty);

        dbHelper = new DatabaseHelper(this.getContext());

        linearLayoutManager = new LinearLayoutManager(this.getContext());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/stage2/people/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI placeHolderApi = retrofit.create(JsonPlaceHolderAPI.class);
        Call<JSONResponse> call = placeHolderApi.getEmployees();

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                employeeList = new ArrayList<>(Arrays.asList(jsonResponse.getEmployees()));

                SharedPreference sharedPreference = new SharedPreference(getActivity());
                Boolean isEmployeeTableExist = sharedPreference.loadEmployeeTable();

                if (isEmployeeTableExist == false) {
                    for (int i=0;i<employeeList.size();i++) {
                        dbHelper.insertImployee(
                                Integer.toString(employeeList.get(i).getEmployeeId()),
                                employeeList.get(i).getEmployeeName().getFirst(),
                                employeeList.get(i).getEmployeeName().getLast(),
                                employeeList.get(i).getEmployeeLocation().getCity(),
                                employeeList.get(i).getEmployeeLocation().getCountry(),
                                employeeList.get(i).getPhone(),
                                employeeList.get(i).getCell(),
                                employeeList.get(i).getRegistered().getDate(),
                                employeeList.get(i).getEmail(),
                                employeeList.get(i).getEmployeePicture().getMedium(),
                                employeeList.get(i).getEmployeeLocation().getCoordinates().getLatitude(),
                                employeeList.get(i).getEmployeeLocation().getCoordinates().getLongitude());
                    }
                    sharedPreference.saveEmployeeTable();
                }
                putDataIntoRecyclerView(employeeList);

                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
            }
        });

        tvSearchEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeList = new ArrayList<>();
                if(!etSearchEmployee.getText().toString().isEmpty()) {
                    String search = etSearchEmployee.getText().toString();
                    getSearchEmployee(search);
                }else {
                   getAllEmployees();
                }

                if(employeeList.size() == 0) {
                    llEmployeeEmpty.setVisibility(View.VISIBLE);
                } else {
                    llEmployeeEmpty.setVisibility(View.GONE);
                }

                putDataIntoRecyclerView(employeeList);
            }
        });

        return view;
    }

    private void putDataIntoRecyclerView(List<Employee> employeeList) {
        EmployeeAdapter adapter = new EmployeeAdapter(employeeList, this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getAllEmployees() {
        Cursor cursor = dbHelper.getAllEmployees();

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
            EmployeeLocationCoordinate employeeLocationCoordinate = new EmployeeLocationCoordinate();
            employeeLocationCoordinate.setLatitude(cursor.getString(11));
            employeeLocationCoordinate.setLongitude(cursor.getString(12));
            employeeLocation.setCoordinates(employeeLocationCoordinate);
            employee.setEmployeeLocation(employeeLocation);


            employee.setPhone(cursor.getString(6));
            employee.setCell(cursor.getString(7));

            EmployeeRegistered employeeRegistered= new EmployeeRegistered();
            employeeRegistered.setDate(cursor.getString(8));
            employee.setRegistered(employeeRegistered);

            employee.setEmail(cursor.getString(9));

            EmployeePicture employeePicture= new EmployeePicture();
            employeePicture.setMedium(cursor.getString(10));
            employee.setEmployeePicture(employeePicture);

            employeeList.add(employee);
        }

    }

    private void getSearchEmployee(String input){
        Cursor cursor = dbHelper.getSearchEmployee(input);

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
            EmployeeLocationCoordinate employeeLocationCoordinate = new EmployeeLocationCoordinate();
            employeeLocationCoordinate.setLatitude(cursor.getString(11));
            employeeLocationCoordinate.setLongitude(cursor.getString(12));
            employeeLocation.setCoordinates(employeeLocationCoordinate);
            employee.setEmployeeLocation(employeeLocation);


            employee.setPhone(cursor.getString(6));
            employee.setCell(cursor.getString(7));

            EmployeeRegistered employeeRegistered= new EmployeeRegistered();
            employeeRegistered.setDate(cursor.getString(8));
            employee.setRegistered(employeeRegistered);

            employee.setEmail(cursor.getString(9));

            EmployeePicture employeePicture= new EmployeePicture();
            employeePicture.setMedium(cursor.getString(10));
            employee.setEmployeePicture(employeePicture);

            employeeList.add(employee);
        }
    }
}