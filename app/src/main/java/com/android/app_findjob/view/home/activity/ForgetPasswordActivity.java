package com.android.app_findjob.view.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.app_findjob.databinding.ActivityForgetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ActivityForgetPasswordBinding binding ;
    private ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        binding.txtLinkSignup.setOnClickListener(view -> {
            Intent i = new Intent(ForgetPasswordActivity.this, RegisterActivity.class);
            startActivity(i);
        });
        binding.btnBack.setOnClickListener(view -> {
            Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
            startActivity(i);
        });

        binding.btnConfirm.setOnClickListener(view -> {
            onClickConfirm();
        });

    }

    private void init(){
        binding=ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(this);
    }

    private void onClickConfirm() {
        String email = binding.txtEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Sending password reset link");
            progressDialog.show();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgetPasswordActivity.this, "Email sent , check your mailbox", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                            }else{
                                Toast.makeText(ForgetPasswordActivity.this, "Try again !!", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}