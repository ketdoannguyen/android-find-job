package com.android.app_findjob.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListEmployerHomeAdapter;
import com.android.app_findjob.adapter.ListJobHomeAdapter;
import com.android.app_findjob.adapter.ListNotificationAdapter;
import com.android.app_findjob.databinding.FragmentNotificationsBinding;
import com.android.app_findjob.databinding.FragmentProfileBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.model.Notification;
import com.android.app_findjob.model.User;
import com.android.app_findjob.ui.notifications.NotificationsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private DatePickerDialog.OnDateSetListener setListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnJobSave.setOnClickListener(view -> {
            Fragment mFragment = new JobFragment();
            getFragmentManager().beginTransaction().replace(R.id.nav_profile, mFragment).commit();
        });

        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        binding.tvNameAccount.setText(mFirebaseUser.getDisplayName());
        binding.tvEmail.setText(mFirebaseUser.getEmail());
        binding.btnUpdateProfile.setOnClickListener(view -> {
            showDialogUpdateProfile();
        });
        binding.btnLogOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showDialogUpdateProfile() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_profile);

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
        EditText txtName = dialog.findViewById(R.id.txtName);
        EditText txtEmail = dialog.findViewById(R.id.txtEmail);
        EditText txtAddress = dialog.findViewById(R.id.txtAddress);
        Button btnUpdateProfile = dialog.findViewById(R.id.btnUpdateProfile);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        txtEmail.setText(firebaseUser.getEmail());
        DatabaseReference realtimeDatabase = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        realtimeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = (User) snapshot.getValue(User.class);
                txtBirthday.setText(user.getBirthday());
                txtPhone.setText(user.getPhone());
                txtName.setText(user.getFullname());
                txtAddress.setText(user.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
            if(txtBirthday.getText().equals("") || txtPhone.getText().equals("") || txtName.getText().equals("") || txtAddress.getText().equals("")){
                Toast.makeText(getContext(),"Please fill in your personal information",Toast.LENGTH_SHORT).show();
            }else{
                realtimeDatabase.child("fullname").setValue(txtName.getText().toString());
                realtimeDatabase.child("birthday").setValue(txtBirthday.getText().toString());
                realtimeDatabase.child("phone").setValue(txtPhone.getText().toString());
                realtimeDatabase.child("address").setValue(txtAddress.getText().toString());
                Toast.makeText(getContext(),"Updated personal information successful",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}