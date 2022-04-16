package com.example.myaddressbook_2301908343.interfaces;

import com.example.myaddressbook_2301908343.models.Employee;
import com.example.myaddressbook_2301908343.responses.JSONResponse;
import com.example.myaddressbook_2301908343.sessions.SharedPreference;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderAPI {
    final String studentNIM ="2301908343";
    final String studentName = "Uray%20Muhamad%20Noor%20Fajri%20Widiansyah";

    @GET("?nim=" + studentNIM + "&nama=" + studentName)
    Call<JSONResponse> getEmployees();
}
