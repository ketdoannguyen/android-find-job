package com.android.app_findjob.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Notification;
import com.android.app_findjob.model.User;
import com.android.app_findjob.view.home.activity.DetailEmployerActivity;
import com.android.app_findjob.view.home.activity.HomeActivity;
import com.android.app_findjob.view.home.activity.LoginActivity;
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
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

public class ListAdminUserAdapter extends RecyclerView.Adapter<ListAdminUserAdapter.ListAdminUserViewHolder> {
    private Context mContext;
    private ArrayList<User> userList;

    public ListAdminUserAdapter(Context mContext, ArrayList<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ListAdminUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_user_item_admin, parent, false);

        return new ListAdminUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdminUserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.txtName.setText(user.getNameAccount());
        holder.txtEmail.setText(user.getEmail());
        holder.layout.setOnClickListener(view -> {
            holder.showMenu(view, user);
        });

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ListAdminUserViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtEmail;
        private LinearLayout layout;

        public ListAdminUserViewHolder(@NonNull View v) {
            super(v);
            layout = v.findViewById(R.id.layout_user_admin);
            txtName = v.findViewById(R.id.txtName);
            txtEmail = v.findViewById(R.id.txtEmail);
        }

        public void showMenu(View v, User user) {
            PopupMenu popup = new PopupMenu(mContext, v, Gravity.RIGHT);

            try {
                Field[] fields = popup.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popup);
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            popup.getMenuInflater().inflate(R.menu.show_menu_user, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return menuItemClicked(item, user);
                }
            });
            popup.show();
        }

        private boolean menuItemClicked(MenuItem item, User user) {
            switch (item.getItemId()) {
                case R.id.menuDetail:
                    showDialogDetail(user);
                    break;
                case R.id.menuEdit:
                    showDialogEdit(user);
                    break;
                case R.id.menuDelete:
                    DatabaseReference mDatabaseJobHeart = FirebaseDatabase.getInstance().getReference("User").child(user.getId());
                    mDatabaseJobHeart.removeValue();

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(user.getEmail(), user.getPass())
                            .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        mFirebaseUser.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(mContext, "Delete success", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });

                    userList.remove(user);
                    break;
            }
            return true;
        }

        private void showDialogDetail(User user) {
            final Dialog dialog = new Dialog(mContext);
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


            TextView txtBirthday = dialog.findViewById(R.id.txtBirthday);
            EditText txtPhone = dialog.findViewById(R.id.txtPhone);
            EditText txtNameAccount = dialog.findViewById(R.id.txtNameAccount);
            EditText txtPass = dialog.findViewById(R.id.txtPass);
            EditText txtName = dialog.findViewById(R.id.txtName);
            EditText txtEmail = dialog.findViewById(R.id.txtEmail);
            EditText txtAddress = dialog.findViewById(R.id.txtAddress);
            Button btnUpdateProfile = dialog.findViewById(R.id.btnUpdateProfile);
            btnUpdateProfile.setVisibility(View.GONE);

            txtBirthday.setFocusable(false);
            txtPhone.setFocusable(false);
            txtNameAccount.setFocusable(false);
            txtPass.setFocusable(false);
            txtName.setFocusable(false);
            txtEmail.setFocusable(false);
            txtAddress.setFocusable(false);

            txtBirthday.setText(user.getBirthday());
            txtPhone.setText(user.getPhone());
            txtNameAccount.setText(user.getNameAccount());
            txtPass.setText(user.getPass());
            txtName.setText(user.getFullname());
            txtEmail.setText(user.getEmail());
            txtAddress.setText(user.getAddress());

            dialog.show();
        }

        private void showDialogEdit(User user) {
            final Dialog dialog = new Dialog(mContext);
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


            TextView txtBirthday = dialog.findViewById(R.id.txtBirthday);
            EditText txtPhone = dialog.findViewById(R.id.txtPhone);
            EditText txtNameAccount = dialog.findViewById(R.id.txtNameAccount);
            EditText txtPass = dialog.findViewById(R.id.txtPass);
            EditText txtName = dialog.findViewById(R.id.txtName);
            EditText txtEmail = dialog.findViewById(R.id.txtEmail);
            EditText txtAddress = dialog.findViewById(R.id.txtAddress);
            Button btnUpdateProfile = dialog.findViewById(R.id.btnUpdateProfile);
            btnUpdateProfile.setText("UPDATE USER");

            txtBirthday.setText(user.getBirthday());
            txtPhone.setText(user.getPhone());
            txtNameAccount.setText(user.getNameAccount());
            txtPass.setText(user.getPass());
            txtName.setText(user.getFullname());
            txtEmail.setText(user.getEmail());
            txtEmail.setFocusable(false);
            txtAddress.setText(user.getAddress());

            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            txtBirthday.setOnClickListener(view -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        txtBirthday.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            });
            btnUpdateProfile.setOnClickListener(view -> {
                String birthday = txtBirthday.getText().toString();
                String phone = txtPhone.getText().toString();
                String nameAccount = txtNameAccount.getText().toString();
                String pass = txtPass.getText().toString();
                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String address = txtAddress.getText().toString();

                if (birthday.equals("") || phone.equals("") || nameAccount.equals("") ||
                        name.equals("") || address.equals("") ||
                        email.equals("") || pass.equals("")) {
                    Toast.makeText(mContext, "Please fill in your personal information", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 6) {
                    Toast.makeText(mContext, "Password length should be 6 characters", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(mContext, "Invalid Email", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    mFirebaseUser.updateProfile(
                            new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nameAccount)
                                    .build()
                    );

                    // add user to realtime database
                    DatabaseReference realtimeDatabase = FirebaseDatabase.getInstance().getReference("User");
                    User mUser = new User(mFirebaseUser.getUid(), nameAccount, email, pass, name, birthday, phone, address);
                    realtimeDatabase.child(user.getId()).setValue(mUser);

                    Toast.makeText(mContext, "Add user success", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            });

            dialog.show();


        }

    }

}
