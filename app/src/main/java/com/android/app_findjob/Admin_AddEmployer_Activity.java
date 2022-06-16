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
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.EmployerFollow;
import com.android.app_findjob.model.describe;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Admin_AddEmployer_Activity extends AppCompatActivity {
    private ActivityAdminAddEmployerBinding binding;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    private ArrayList<Employer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddEmployerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getListDataFirebase();

        binding.btnBack.setOnClickListener(view -> {
            intent();
        });
        binding.imgEmployer.setOnClickListener(view -> {
            selectImage();
        });

        binding.img.setOnClickListener(view -> {
            selectImage();
        });

        binding.btnAddEmployer.setOnClickListener(view -> {
            uploadImage();
        });
        AtomicInteger count = new AtomicInteger();
        binding.btnPlus.setOnClickListener(view -> {
            if (count.get() == 0) {
                binding.txtDes1.setVisibility(View.VISIBLE);
            } else if (count.get() == 1) {
                binding.txtDes2.setVisibility(View.VISIBLE);
            } else if (count.get() == 2) {
                binding.txtDes3.setVisibility(View.VISIBLE);
            }
            count.set(count.get() + 1);
        });
    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();

        String city = binding.txtCity.getText().toString();
        String web = binding.txtWebsite.getText().toString();
        String name = binding.txtName.getText().toString();
        String activeTime = binding.txtActiveTime.getText().toString();
        String address = binding.txtAddress.getText().toString();
        String des1 = binding.txtDes1.getText().toString();
        String des2 = binding.txtDes2.getText().toString();
        String des3 = binding.txtDes3.getText().toString();

        if (city.equals("") || web.equals("") || name.equals("") || activeTime.equals("") || address.equals("")) {
            Toast.makeText(this, "Please fill in your personal information", Toast.LENGTH_SHORT).show();
        } else {
            Employer mEmployer = new Employer();
            if (list.size() == 0) {
                mEmployer.setEmployerID(0);
            } else {
                mEmployer.setEmployerID(list.get(list.size() - 1).getEmployerID() + 1);
            }
            mEmployer.setCity(city);
            mEmployer.setWebsite(web);
            mEmployer.setName(name);
            mEmployer.setActiveTime(activeTime);
            mEmployer.setAddress(address);
            mEmployer.setDescribe(new describe(des1, des2, des3));

            storageReference = FirebaseStorage.getInstance().getReference("Employer/" + name);
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isComplete()) ;
                            Uri uri = uriTask.getResult();
                            mEmployer.setImg(uri.toString());
                            DatabaseReference realtimeDatabase = FirebaseDatabase.getInstance().getReference("Employer");
                            realtimeDatabase.child(mEmployer.getEmployerID() + "").setValue(mEmployer);
                            Toast.makeText(Admin_AddEmployer_Activity.this, "Add employer success", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            intent();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Admin_AddEmployer_Activity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void intent(){
        Intent i = new Intent(this, AdminActivity.class);
        i.putExtra("AddEmployer", "true");
        startActivity(i);
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);

        binding.imgEmployer.setVisibility(View.GONE);
        binding.img.setVisibility(View.VISIBLE);

    }

    private void getListDataFirebase() {
        list = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Employer");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Employer employer = (Employer) postSnapshot.getValue(Employer.class);
                    list.add(employer);
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