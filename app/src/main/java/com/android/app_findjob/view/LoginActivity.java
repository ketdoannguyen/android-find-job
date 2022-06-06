package com.android.app_findjob.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Toast;

import com.android.app_findjob.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding ;
    private ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        binding.txtLinkSignup.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });
        binding.txtForgotPass.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
            startActivity(i);
        });

        binding.btnLogin.setOnClickListener(view -> {
            onClickLogin();
        });
    }

    private void init(){
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        onChangedText();
    }

    private void onClickLogin(){
        String email = binding.txtEmail.getText().toString();
        String pass = binding.txtPass.getText().toString();
        String checkEmail = checkEmail(email);
        String checkPass = checkPass(pass);
        if (!checkEmail.equals("") || !checkPass.equals("")) {
            binding.txtMessageEmail.setText(checkEmail);
            binding.txtMessagePass.setText(checkPass);
        } else {
            progressDialog.setTitle("Logged in ...");
            progressDialog.show();
            sendDataAuth(email, pass);
        }
    }
    private void sendDataAuth(String email , String pass){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login succes",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Email or password is incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private String checkEmail( String email){
        String result= "";
        if (TextUtils.isEmpty(email)) {
            result = "Email cannot be empty";
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            result = "Invalid Email";
        }
        return result;
    }

    private String  checkPass(String pass) {
        String result= "";
        if (TextUtils.isEmpty(pass)) {
            result = "Password cannot be empty";
        } else if (pass.length() < 6) {
            result = "Password length should be 6 characters";
        }
        return result;
    }

    private void onChangedText() {
        binding.txtEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                String email = binding.txtEmail.getText().toString();
                String checkEmail= checkEmail(email);
                binding.txtMessageEmail.setText(checkEmail);
            }
        });
        binding.txtPass.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                String Pass = binding.txtPass.getText().toString();
                String checkPass= checkPass(Pass);
                binding.txtMessagePass.setText(checkPass);
            }
        });
    }

}