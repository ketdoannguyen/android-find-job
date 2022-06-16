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
import com.android.app_findjob.view.admin.AdminActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Admin_AddEmployer_Activity extends AppCompatActivity {
    private ActivityAdminAddEmployerBinding binding ;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddEmployerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.btnBack.setOnClickListener(view -> {
            Intent i = new Intent(this,AdminActivity.class);
                i.putExtra("AddEmployer","true");
            startActivity(i);
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
            if(count.get() == 0){
                binding.txtDes1.setVisibility(View.VISIBLE);
            }
            else if(count.get() == 1){
                binding.txtDes2.setVisibility(View.VISIBLE);
            }
            else if(count.get() == 2){
                binding.txtDes3.setVisibility(View.VISIBLE);
            }
            count.set(count.get()+1);
        });
    }
    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("Employer/"+fileName);


        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        System.out.println(uri.toString());
                        binding.img.setImageURI(null);
                        Toast.makeText(Admin_AddEmployer_Activity.this,"Add employer success",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(Admin_AddEmployer_Activity.this,"Failed to Upload",Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

        binding.imgEmployer.setVisibility(View.GONE);
        binding.img.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            binding.img.setImageURI(imageUri);

        }
    }
}