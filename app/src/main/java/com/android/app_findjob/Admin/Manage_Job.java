package com.android.app_findjob.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListJobHomeAdapter;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.model.request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class Manage_Job extends Fragment{
    Button btnThem;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Food");
    ArrayList<Job> lst;
    RecyclerView recyclerView;
    public Manage_Job() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manage_job, container, false);

        btnThem = view.findViewById(R.id.btnThemMonAn);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    startActivity(new Intent(getContext(), AddJob.class));
            }
        });

        recyclerView = view.findViewById(R.id.list_Monan);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lst = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String address= ds.child("address").getValue(String.class);
                    String city = ds.child("city").getValue(String.class);
                    String describe = ds.child("describe").getValue(String.class);
                    int employerID =Integer.valueOf(ds.child("employerID").getValue(String.class))  ;

                    int id= Integer.valueOf(ds.child("id").getValue(String.class)) ;
                    String name = ds.child("name").getValue(String.class);
//                    request request =ds.child("request").getValue(String.class);
                    String salary = ds.child("salary").getValue(String.class);
                    String skill = ds.child("skill").getValue(String.class);
                    String time = ds.child("time").getValue(String.class);
                    String vacancies = ds.child("vacancies").getValue(String.class);

//                    Job _newJob = new Job(id,employerID,name,skill,salary,address, city,vacancies, describe,request,time);
//                    lst.add(_newJob);
                }
                Toast.makeText(getContext(), lst.size()+"", Toast.LENGTH_SHORT).show();
                ListJobHomeAdapter adapter = new ListJobHomeAdapter(getContext(),lst);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                String item = lst.get(itemPosition).getName();
                Toast.makeText(getContext(), item, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
