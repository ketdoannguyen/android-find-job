package com.android.app_findjob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.app_findjob.databinding.ActivityAdminAddEmployerBinding;
import com.android.app_findjob.databinding.ActivityAdminAddJobBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.model.describe;
import com.android.app_findjob.model.request;
import com.android.app_findjob.view.admin.AdminActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Admin_AddJob_Activity extends AppCompatActivity {
    private ActivityAdminAddJobBinding binding;
    private ArrayList<Job> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddJobBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getListDataFirebase();

        binding.btnBack.setOnClickListener(view -> {
            intent();
        });

        binding.btnAddJob.setOnClickListener(view -> {
            intent();
        });

        AtomicInteger count1 = new AtomicInteger();
        binding.btnPlusDes.setOnClickListener(view -> {
            if (count1.get() == 0) {
                binding.txtDes1.setVisibility(View.VISIBLE);
            } else if (count1.get() == 1) {
                binding.txtDes2.setVisibility(View.VISIBLE);
            } else if (count1.get() == 2) {
                binding.txtDes3.setVisibility(View.VISIBLE);
            }
            count1.set(count1.get() + 1);
        });

        AtomicInteger count2 = new AtomicInteger();
        binding.btnPlusRq.setOnClickListener(view ->{
            if (count2.get()==0){
                binding.txtReq1.setVisibility(View.VISIBLE);
            }
            else if (count2.get()==1){
                binding.txtReq2.setVisibility(View.VISIBLE);
            }
            else if(count2.get()==2){
                binding.txtReq3.setVisibility(View.VISIBLE);
            }
            else if (count2.get()==3)
            {
                binding.txtReq4.setVisibility(View.VISIBLE);
            }
            count2.set(count2.get()+1);
        });
    }
    private void intent(){
        Intent i = new Intent(this, AdminActivity.class);
        i.putExtra("AddnewJob", "true");
        startActivity(i);
    }
    private void addJob(){
        String name = binding.txtNameJob.getText().toString();
        String address = binding.txtaddress.getText().toString();
        String employer = binding.txtEmployerid.getText().toString();
        String salary = binding.txtSalary.getText().toString();
        String skill = binding.txtSkill.getText().toString();
        String des1 = binding.txtDes1.getText().toString();
        String des2 = binding.txtDes2.getText().toString();
        String des3 = binding.txtDes3.getText().toString();
        String req1 = binding.txtReq1.getText().toString();
        String req2 = binding.txtReq2.getText().toString();
        String req3 = binding.txtReq3.getText().toString();
        String req4 = binding.txtReq4.getText().toString();


        if (employer.equals("") || salary.equals("") || name.equals("") || skill.equals("") || address.equals("")) {
            Toast.makeText(this, "Please fill in your personal information", Toast.LENGTH_SHORT).show();
        } else {
            Job job = new Job();
            if (list.size() == 0) {
                job.setId(0);
            } else {
                job.setId(list.get(list.size() - 1).getEmployerID() + 1);
            }
            job.setName(name);
            job.setAddress(address);
            job.setEmployerID(1);
            job.setSalary(salary);
            job.setSkill(skill);
            job.setDescribe(new describe(des1, des2, des3));
            job.setRequest(new request(req1, req2, req3,req4));
            DatabaseReference realtimeDatabase = FirebaseDatabase.getInstance().getReference("Job");
            realtimeDatabase.child(job.getId() + "").setValue(job);
            Toast.makeText(Admin_AddJob_Activity.this, "Add job success", Toast.LENGTH_SHORT).show();
        }
    }
    private void getListDataFirebase() {
        list = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Job");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Job job = (Job) postSnapshot.getValue(Job.class);
                    list.add(job);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}