package com.android.app_findjob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.app_findjob.databinding.ActivityDetailEmployerBinding;
import com.android.app_findjob.databinding.ActivityDetailJobBinding;
import com.android.app_findjob.view.HomeActivity;

public class DetailEmployerActivity extends AppCompatActivity {
    private ActivityDetailEmployerBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailEmployerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class));
        });
    }
}