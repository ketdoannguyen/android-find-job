package com.android.app_findjob.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListJobHomeAdapter;
import com.android.app_findjob.databinding.ActivityDetailJobBinding;
import com.android.app_findjob.databinding.ActivityLoginBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.EmployerFollow;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.model.JobFollow;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailJobActivity extends AppCompatActivity {
    private ActivityDetailJobBinding binding ;
    private ArrayList<JobFollow> list;
    private int idJobIntent;
    private boolean checkHeart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        binding.btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class));
        });

        Intent intent = getIntent();
        int idEmployerIntent = intent.getIntExtra("IDEmployer",-1);
        idJobIntent = intent.getIntExtra("IDJob",-1);
        String imgEmployerIntent = intent.getStringExtra("ImgEmployer");
        String nameEmployerIntent = intent.getStringExtra("NameEmployer");

        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        getListDataFirebase(userAuth.getUid());

        binding.layoutTop.setOnClickListener(view -> {
            Intent i = new Intent(this, DetailEmployerActivity.class);
            Bundle mBundle  = new Bundle();
            mBundle.putInt("IDEmployer",idEmployerIntent);
            i.putExtras(mBundle);
            startActivity(i);
        });
        binding.btnApply.setOnClickListener(view -> {
            showDialogApply();
        });



        Picasso.get()
                .load(imgEmployerIntent)
                .into(binding.imgViewEmployer);
        binding.txtNameEmployer.setText(nameEmployerIntent);

        DatabaseReference mDatabaseEmployer = FirebaseDatabase.getInstance().getReference("Job/" + idJobIntent);
        mDatabaseEmployer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotEmployer) {
                Job job = (Job) dataSnapshotEmployer.getValue(Job.class);
                binding.txtNameJob.setText(job.getName());
                binding.txtSalaryJob.setText(job.getSalary());
                binding.txtVacanciesJob.setText(job.getVacancies());
                binding.txtSkillJob.setText(job.getSkill());
                binding.txtAddressJob.setText(job.getAddress());
                binding.txtTime.setText(job.getTime());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        binding.btnHeart.setOnClickListener(view -> {
            DatabaseReference mDatabaseFollow = FirebaseDatabase.getInstance().getReference("UserActivity").child(userAuth.getUid()).child("JobFollow");
            if (!checkHeart) {
                binding.btnHeart.setImageResource(R.drawable.heart1);
                checkHeart = true;
                JobFollow mJobFollow = new JobFollow();
                Boolean check = false ;
                for (JobFollow e : list) {
                    if (e.getJobID() == idJobIntent) {
                        mJobFollow.setId(e.getId());
                        check = true ;
                    }
                }
                if(!check){
                    if (list.size() == 0) {
                        mJobFollow.setId(0);
                    } else {
                        mJobFollow.setId(list.get(list.size() - 1).getId() + 1);
                    }
                }
                mJobFollow.setJobID(idJobIntent);
                mDatabaseFollow.child(mJobFollow.getId() + "").setValue(mJobFollow);
                list.add(mJobFollow);
                Toast.makeText(this,"Đã thêm công việc này vào mục yêu thích",Toast.LENGTH_SHORT).show();
                return;
            } else {
                binding.btnHeart.setImageResource(R.drawable.heart);
                checkHeart = false;
                Toast.makeText(this,"Đã xóa công việc này khỏi mục yêu thích",Toast.LENGTH_SHORT).show();
                for (JobFollow jobFollow : list) {
                    if (jobFollow.getJobID() == idJobIntent) {
                        mDatabaseFollow.child(jobFollow.getId()+"").removeValue();
                        list.remove(jobFollow);
                        return;
                    }
                }
            }
        });
    }

    private void init(){
        binding=ActivityDetailJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    private void getListDataFirebase(String idUser) {
        list = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("UserActivity").child(idUser).child("JobFollow");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    JobFollow jobFollow = (JobFollow) postSnapshot.getValue(JobFollow.class);
                    if (jobFollow.getJobID() == idJobIntent) {
                        binding.btnHeart.setImageResource(R.drawable.heart1);
                        checkHeart = true;
                    }
                    list.add(jobFollow);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void showDialogApply() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_apply);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        TextView tvEmployer = dialog.findViewById(R.id.tvEmployer);
        TextView tvJob =dialog.findViewById(R.id.tvJob);

        tvJob.setText(binding.txtNameJob.getText());
        tvEmployer.setText(binding.txtNameEmployer.getText());

//        EditText editText = dialog.findViewById(R.id.layout);
        dialog.show();
    }
}