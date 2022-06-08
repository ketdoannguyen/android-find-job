package com.android.app_findjob;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.android.app_findjob.databinding.ActivityDetailJobBinding;
import com.android.app_findjob.databinding.ActivityLoginBinding;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.view.HomeActivity;

public class DetailJobActivity extends AppCompatActivity {
    private ActivityDetailJobBinding binding ;
    private LinearLayout layoutTop ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        binding.btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class));
        });

        binding.layoutTop.setOnClickListener(view -> {
            Intent i = new Intent(this, DetailEmployerActivity.class);
            Bundle mBundle  = new Bundle();
            mBundle.putString("NameEmployer",binding.txtNameEmployer.getText().toString());
            i.putExtras(mBundle);
            startActivity(i);
        });

        Intent intent = getIntent();
        int value1 = intent.getIntExtra("ID",-1);
        System.out.println(value1);


    }

    private void init(){
        binding=ActivityDetailJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}