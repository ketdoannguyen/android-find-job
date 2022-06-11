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

import com.android.app_findjob.R;
import com.android.app_findjob.databinding.ActivityLoginBinding;
import com.android.app_findjob.databinding.ActivityRegisterBinding;
import com.android.app_findjob.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding ;
    private ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        binding.txtLinkLogin.setOnClickListener(view -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        });

        binding.btnSignup.setOnClickListener(view -> {
            onClickLogin();
        });
    }
    private void init(){
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        onChangedText();
    }

    private void onClickLogin(){
        String name = binding.txtNameRegis.getText().toString();
        System.out.println(name);
        String email = binding.txtEmailRegis.getText().toString();
        String pass = binding.txtPassRegis.getText().toString();
        String checkName = checkName(name);
        String checkEmail = checkEmail(email);
        String checkPass = checkPass(pass);
        if (!checkEmail.equals("") || !checkPass.equals("") || !checkName.equals("")) {
            binding.txtMessageName.setText(checkName);
            binding.txtMessageEmail.setText(checkEmail);
            binding.txtMessagePass.setText(checkPass);
        }else if (!binding.checkboxRobot.isChecked()) {
            binding.txtMessagePass.setText("You are a robot ?");
        }else {
            progressDialog.setTitle("Registering ...");
            progressDialog.show();
            sendDataAuth(name , email, pass);
        }
    }
    private void sendDataAuth(String name , String email , String pass){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            mFirebaseUser.sendEmailVerification();

                            // add user to realtime database
                            DatabaseReference realtimeDatabase = FirebaseDatabase.getInstance().getReference("User");
                            User user = new User(mFirebaseUser.getUid().toString(),name,email,pass);
                            realtimeDatabase.child(mFirebaseUser.getUid()).setValue(user);

                            Toast.makeText(RegisterActivity.this, "     Register succes!!\nPlease verify your email",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Register failed !!",Toast.LENGTH_SHORT).show();
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
    private String checkName( String name){
        String result= "";
        if (TextUtils.isEmpty(name)) {
            result = "Name cannot be empty";
        } else if (name.length() < 3) {
            result = "Name length should be 3 characters";
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
        binding.txtEmailRegis.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                String email = binding.txtEmailRegis.getText().toString();
                String checkEmail= checkEmail(email);
                binding.txtMessageEmail.setText(checkEmail);
            }
        });
        binding.txtPassRegis.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                String Pass = binding.txtPassRegis.getText().toString();
                String checkPass= checkPass(Pass);
                binding.txtMessagePass.setText(checkPass);
            }
        });
        binding.txtNameRegis.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                String name = binding.txtNameRegis.getText().toString();
                String checkName= checkName(name);
                binding.txtMessageName.setText(checkName);
            }
        });
    }

}