package com.example.myaddressbook_2301908343.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myaddressbook_2301908343.MainActivity;
import com.example.myaddressbook_2301908343.R;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmployeeDetailFragment extends Fragment implements OnMapReadyCallback {

    private TextView tvEmployeeName, tvEmployeeCity, tvEmployeePhone, tvEmployeeMemberDate, getTvEmployeeEmail;
    private TextView tvAddToAddressBook;
    private GoogleMap map;
    ArrayList<Employee> employeeList;
    private ProgressBar pbLoading;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_detail, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Detail Employee");

        tvEmployeeName = view.findViewById(R.id.tvEmployeeName);
        tvEmployeeCity = view.findViewById(R.id.tvEmployeeCity);
        tvEmployeePhone = view.findViewById(R.id.tvEmployeePhone);
        tvEmployeeMemberDate = view.findViewById(R.id.tvEmployeeMemberDate);
        getTvEmployeeEmail = view.findViewById(R.id.tvEmployeeEmail);
        tvAddToAddressBook = view.findViewById(R.id.tvAddToMyAddressBook);
        pbLoading = view.findViewById(R.id.pbLoading);
        employeeList = new ArrayList<>();

        dbHelper = new DatabaseHelper(this.getContext());

        SharedPreference sharedPreference = new SharedPreference(getActivity());
        Employee employee = sharedPreference.loadSelectedEmployee();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/stage2/people/" + employee.getEmployeeId() + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI placeHolderApi = retrofit.create(JsonPlaceHolderAPI.class);
        Call<JSONResponse> call = placeHolderApi.getEmployees();

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                try {
                    JSONResponse jsonResponse = response.body();
                    employeeList = new ArrayList<>(Arrays.asList(jsonResponse.getEmployees()));

                    tvEmployeeName.setText(employeeList.get(0).getEmployeeName().getFirst() + " " + employeeList.get(0).getEmployeeName().getLast());
                    tvEmployeeCity.setText(employeeList.get(0).getEmployeeLocation().getCity() + ", " + employeeList.get(0).getEmployeeLocation().getCountry());
                    tvEmployeePhone.setText(employeeList.get(0).getPhone() + " / " + employeeList.get(0).getCell());
                    getTvEmployeeEmail.setText(employeeList.get(0).getEmail());

                    String memberDate = employeeList.get(0).getRegistered().getDate();
                    SimpleDateFormat formatinDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    Date date = formatinDate.parse(memberDate);
                    formatinDate = new SimpleDateFormat("MMMM yyyy");
                    memberDate = formatinDate.format(date);

                    tvEmployeeMemberDate.setText(memberDate);

                    pbLoading.setVisibility(View.GONE);
                }catch (Exception e) {
                    e.printStackTrace();
                    pbLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvAddToAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean isExist = isAddressBookExist(Integer.toString(employeeList.get(0).getEmployeeId()));

                if(isExist) {
                    Toast.makeText(getContext(), "The employee already add to address book", Toast.LENGTH_LONG).show();
                } else {
                    dbHelper.insertAddressBook(
                            Integer.toString(employee.getEmployeeId()),
                            employee.getEmployeeName().getFirst(),
                            employee.getEmployeeName().getLast(),
                            employee.getEmployeeLocation().getCity(),
                            employee.getEmployeeLocation().getCountry(),
                            employee.getPhone(),
                            employee.getCell(),
                            employee.getEmail(),
                            employee.getEmployeePicture().getMedium());

                    Toast.makeText(getContext(), "Success add employee", Toast.LENGTH_LONG).show();

                    FragmentManager fm = getParentFragmentManager();
                    fm.beginTransaction().replace(R.id.fragment_container, (Fragment)new MyAddressBookFragment()).addToBackStack(null).commit();
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        SharedPreference sharedPreference = new SharedPreference(getActivity());
        Employee employee = sharedPreference.loadSelectedEmployee();

        map = googleMap;
         double latitude = Double.parseDouble(employee.getEmployeeLocation().getCoordinates().getLatitude());
         double longitude = Double.parseDouble(employee.getEmployeeLocation().getCoordinates().getLongitude());
        LatLng location = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(location).title(employee.getEmployeeLocation().getCity()));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));

    }


    private Boolean isAddressBookExist(String id){
        Cursor cursor = dbHelper.getAddressBook(id);

        if(cursor.getCount() <= 0) {
            return false;
        }

        return true;
    }
}