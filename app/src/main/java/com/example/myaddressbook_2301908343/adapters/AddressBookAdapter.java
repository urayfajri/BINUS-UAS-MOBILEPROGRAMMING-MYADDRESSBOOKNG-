package com.example.myaddressbook_2301908343.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myaddressbook_2301908343.R;
import com.example.myaddressbook_2301908343.models.Employee;

import java.util.List;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.AddressBookViewHolder> {

    private List<Employee> employees;
    // private ClickableEmployee listener;
    private Context context;

    public AddressBookAdapter(List<Employee> employees, Context context) {
        this.employees = employees;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressBookAdapter.AddressBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.address_book_layout, parent, false );
        return new AddressBookAdapter.AddressBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookAdapter.AddressBookViewHolder holder, int position) {
        Employee employee = employees.get(position);

        holder.tvEmployeeName.setText(employee.getEmployeeName().getFirst() + " " + employee.getEmployeeName().getLast());
        Glide.with(context).load(employee.getEmployeePicture().getMedium()).into(holder.ivEmployeeImage);
        holder.tvEmployeeCity.setText(employee.getEmployeeLocation().getCity() + ", " + employee.getEmployeeLocation().getCountry());

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(Telephony.Sms.getDefaultSmsPackage(context));
                if (launchIntent != null) {
                    context.startActivity(launchIntent);
                } else {
                    Toast.makeText(view.getContext(), "There is no package available in android", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                if (launchIntent != null) {
                    context.startActivity(launchIntent);
                } else {
                    Toast.makeText(view.getContext(), "There is no package available in android", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class AddressBookViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llAddressBook;
        private ImageView ivEmployeeImage;
        private TextView tvEmployeeName, tvEmployeeCity;
        private Button btnCall, btnEmail;


        public AddressBookViewHolder(@NonNull View itemView) {
            super(itemView);
            llAddressBook = itemView.findViewById(R.id.llAdrressBook);
            ivEmployeeImage = itemView.findViewById(R.id.ivEmployeeImage);
            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            tvEmployeeCity = itemView.findViewById(R.id.tvEmployeeCity);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnEmail = itemView.findViewById(R.id.btnEmail);
        }
    }
}
