package com.example.myaddressbook_2301908343.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myaddressbook_2301908343.R;
import com.example.myaddressbook_2301908343.fragments.EmployeeDetailFragment;
import com.example.myaddressbook_2301908343.fragments.MyAddressBookFragment;
import com.example.myaddressbook_2301908343.interfaces.ClickableEmployee;
import com.example.myaddressbook_2301908343.models.Employee;
import com.example.myaddressbook_2301908343.sessions.SharedPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> employees;
    // private ClickableEmployee listener;
    private Context context;

    public EmployeeAdapter(List<Employee> employees, Context context) {
        this.employees = employees;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeAdapter.EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.employee_layout, parent, false );
        return new EmployeeAdapter.EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeViewHolder holder, int position) {
       try {
           Employee employee = employees.get(position);

           holder.tvEmployeeName.setText(employee.getEmployeeName().getFirst() + " " + employee.getEmployeeName().getLast());
           Glide.with(context).load(employee.getEmployeePicture().getMedium()).into(holder.ivEmployeeImage);
           holder.tvEmployeeCity.setText(employee.getEmployeeLocation().getCity() + ", " + employee.getEmployeeLocation().getCountry());
           holder.tvEmployeePhone.setText(employee.getPhone() + " / " + employee.getCell());

           String memberDate = employee.getRegistered().getDate();
           SimpleDateFormat formatinDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
           Date date = formatinDate.parse(memberDate);
           formatinDate = new SimpleDateFormat("MMMM yyyy");
           memberDate = formatinDate.format(date);

           holder.tvEmployeeMemberDate.setText(memberDate);
           holder.llEmployee.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                SharedPreference sharedPreference = new SharedPreference(context);
                sharedPreference.saveSelectedEmployee(employee);

                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                   fm.beginTransaction().replace(R.id.fragment_container, (Fragment)new EmployeeDetailFragment()).addToBackStack(null).commit();
               }
           });
       }catch (Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llEmployee;
        private ImageView ivEmployeeImage;
        private TextView tvEmployeeName, tvEmployeeCity, tvEmployeePhone, tvEmployeeMemberDate;


        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            llEmployee = itemView.findViewById(R.id.llEmployee);
            ivEmployeeImage = itemView.findViewById(R.id.ivEmployeeImage);
            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            tvEmployeeCity = itemView.findViewById(R.id.tvEmployeeCity);
            tvEmployeePhone = itemView.findViewById(R.id.tvEmployeePhone);
            tvEmployeeMemberDate = itemView.findViewById(R.id.tvEmployeeMemberDate);
        }
    }
}
