package com.android.app_findjob.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.app_findjob.databinding.ActivityDetailEmployerBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.EmployerFollow;
import com.android.app_findjob.model.Job;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailEmployerActivity extends AppCompatActivity {
    private ActivityDetailEmployerBinding binding;
    private ArrayList<EmployerFollow> list;
    private int idEmployerIntent;
    private  DatabaseReference mDatabaseFollow ;
    private boolean checkEmployer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailEmployerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        idEmployerIntent = intent.getIntExtra("IDEmployer", -1);
        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabaseFollow = FirebaseDatabase.getInstance().getReference("UserActivity").child(userAuth.getUid()).child("EmployerFollow");
        getListDataFirebase(userAuth.getUid());
        binding.btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class));
        });


        binding.btnFollow.setOnClickListener(view -> {
            if (!checkEmployer) {
                binding.btnFollow.setText("BỎ THEO DÕI");
                checkEmployer = true ;
                Toast.makeText(this,"Theo dõi nhà tuyển dụng thành công",Toast.LENGTH_SHORT).show();
                EmployerFollow mEmployerFollow = new EmployerFollow();
                Boolean check = false ;
                for (EmployerFollow e : list) {
                    if (e.getEmployerID() == idEmployerIntent) {
                        mEmployerFollow.setId(e.getId());
                        check = true ;
                    }
                }
                if(!check){
                    if (list.size() == 0) {
                        mEmployerFollow.setId(0);
                    } else {
                        mEmployerFollow.setId(list.get(list.size() - 1).getId() + 1);
                    }
                }
                mEmployerFollow.setEmployerID(idEmployerIntent);
                mDatabaseFollow.child(mEmployerFollow.getId() + "").setValue(mEmployerFollow);
                list.add(mEmployerFollow);
            } else{
                binding.btnFollow.setText("THEO DÕI");
                checkEmployer = false ;
                Toast.makeText(this,"Bỏ theo dõi nhà tuyển dụng thành công",Toast.LENGTH_SHORT).show();
                for (EmployerFollow employer : list) {
                    if (employer.getEmployerID() == idEmployerIntent) {
                        mDatabaseFollow.child(employer.getId()+"").removeValue();
                        list.remove(employer);
                        return;
                    }
                }

            }

        });
        DatabaseReference mDatabaseEmployer = FirebaseDatabase.getInstance().getReference("Employer/" + idEmployerIntent);
        mDatabaseEmployer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotEmployer) {
                Employer employer = (Employer) dataSnapshotEmployer.getValue(Employer.class);
                System.out.println(employer.getImg());
                Picasso.get()
                        .load(employer.getImg())
                        .into(binding.imgViewEmployer);
                binding.txtNameEmployer.setText(employer.getName());
                binding.txtActiveTimeEmployer.setText(employer.getActiveTime());
                binding.txtCityEmployer.setText(employer.getCity());
                binding.txtFollerEmployer.setText(employer.getFollower() + "");
                binding.txtWebsiteEmployer.setText(employer.getWebsite());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void getListDataFirebase(String idUser) {
        list = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("UserActivity").child(idUser).child("EmployerFollow");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EmployerFollow employerFollow = (EmployerFollow) postSnapshot.getValue(EmployerFollow.class);
                    if (employerFollow.getEmployerID() == idEmployerIntent) {
                        binding.btnFollow.setText("BỎ THEO DÕI");
                        checkEmployer = true ;
                    }
                    list.add(employerFollow);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}