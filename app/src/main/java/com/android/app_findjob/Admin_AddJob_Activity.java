package com.android.app_findjob;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.android.app_findjob.databinding.ActivityAdminAddEmployerBinding;
import com.android.app_findjob.databinding.ActivityAdminAddJobBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Job;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

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

    }

    private void getListDataFirebase() {
    }

}