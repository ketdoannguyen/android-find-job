package com.android.app_findjob;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.android.app_findjob.databinding.ActivityAdminAddEmployerBinding;
import com.android.app_findjob.databinding.ActivityAdminAddJobBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.view.admin.AdminActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Admin_AddJob_Activity extends AppCompatActivity {
    private ActivityAdminAddJobBinding binding;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {

            imageUri = data.getData();
            binding.img.setImageURI(imageUri);

        }
    }

}