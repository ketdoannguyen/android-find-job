package com.android.app_findjob.view.admin.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListAdminUserAdapter;
import com.android.app_findjob.adapter.ListEmployerFollowAdapter;
import com.android.app_findjob.databinding.FragmentManageJobAdminBinding;
import com.android.app_findjob.databinding.FragmentManageUserAdminBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.EmployerFollow;
import com.android.app_findjob.model.User;
import com.android.app_findjob.view.home.activity.LoginActivity;
import com.android.app_findjob.view.home.activity.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executor;

public class ManageUserAdminFragment extends Fragment {
    private FragmentManageUserAdminBinding binding;

    private RecyclerView rView;
    private ArrayList<User> userList;
    private ListAdminUserAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManageUserAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnAdd.setOnClickListener(view -> {
            showDialogUpdateProfile();
        });
        showEmployerHome();
        return root;
    }

    private void showEmployerHome() {
        rView = binding.listEmployerFollow;
        userList = new ArrayList<>();

        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabaseJobHeart = FirebaseDatabase.getInstance().getReference("User");
        mDatabaseJobHeart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotJob) {
                userList.removeAll(userList);
                for (DataSnapshot postSnapshotJob : dataSnapshotJob.getChildren()) {
                    User user = (User) postSnapshotJob.getValue(User.class);
                    userList.add(user);

                    mAdapter = new ListAdminUserAdapter(getContext(), userList);

                    GridLayoutManager layout = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                    rView.setLayoutManager(layout);
                    rView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showDialogUpdateProfile() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_user_admin);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        TextView txtBirthday = dialog.findViewById(R.id.txtBirthday);
        EditText txtPhone = dialog.findViewById(R.id.txtPhone);
        EditText txtNameAccount = dialog.findViewById(R.id.txtNameAccount);
        EditText txtPass= dialog.findViewById(R.id.txtPass);
        EditText txtName = dialog.findViewById(R.id.txtName);
        EditText txtEmail = dialog.findViewById(R.id.txtEmail);
        EditText txtAddress = dialog.findViewById(R.id.txtAddress);
        Button btnUpdateProfile = dialog.findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setText("ADD USER");


        txtBirthday.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1 ;
                    String date = day+"/"+month+"/"+year;
                    txtBirthday.setText(date);
                }
            },year,month,day);
            datePickerDialog.show();
        });
        btnUpdateProfile.setOnClickListener(view -> {
            String birthday = txtBirthday.getText().toString() ;
            String phone = txtPhone.getText().toString() ;
            String nameAccount = txtNameAccount.getText().toString() ;
            String pass = txtPass.getText().toString() ;
            String name = txtName.getText().toString() ;
            String email = txtEmail.getText().toString() ;
            String address = txtAddress.getText().toString() ;

            if(birthday.equals("") || phone.equals("") || nameAccount.equals("")||
                    name.equals("") || address.equals("") ||
                    email.equals("") || pass.equals("")){
                Toast.makeText(getContext(),"Please fill in your personal information",Toast.LENGTH_SHORT).show();
            }else if (pass.length() < 6) {
                Toast.makeText(getContext(),"Password length should be 6 characters",Toast.LENGTH_SHORT).show();
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(),"Invalid Email",Toast.LENGTH_SHORT).show();
            }
            else{
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    mFirebaseUser.updateProfile(
                                            new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(nameAccount)
                                                    .build()
                                    );
                                    mFirebaseUser.sendEmailVerification();

                                    // add user to realtime database
                                    DatabaseReference realtimeDatabase = FirebaseDatabase.getInstance().getReference("User");
                                    User user = new User(mFirebaseUser.getUid(),nameAccount,email,pass,name,birthday,phone,address);
                                    realtimeDatabase.child(mFirebaseUser.getUid()).setValue(user);

                                    Toast.makeText(requireContext(), "Add user success",Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(requireContext(), "Add user failed",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}