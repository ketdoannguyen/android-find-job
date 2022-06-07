package com.android.app_findjob;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.app_findjob.databinding.ActivityDetailJobBinding;
import com.android.app_findjob.databinding.ActivityLoginBinding;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.view.HomeActivity;

public class DetailJobActivity extends AppCompatActivity {
    private ActivityDetailJobBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        binding.btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class));
        });

        Intent intent = getIntent();
        Job value1 = intent.getParcelableExtra("Job");
        System.out.println(value1.getName());


    }

    private void init(){
        binding=ActivityDetailJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}