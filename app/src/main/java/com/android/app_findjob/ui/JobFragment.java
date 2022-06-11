package com.android.app_findjob.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListJobHomeAdapter;
import com.android.app_findjob.databinding.FragmentJobBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.model.JobActive;
import com.android.app_findjob.ui.dashboard.DashboardViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobFragment extends Fragment {

    private FragmentJobBinding binding;
    private RecyclerView rView;
    private ArrayList<Job> jobList;
    private ListJobHomeAdapter jobAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentJobBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnJobApply.setOnClickListener(view -> {
            showJobHome("JobApply");
            binding.btnJobApply.setTextColor(Color.parseColor("#FF6600"));
            binding.btnJobFollow.setTextColor(Color.parseColor("#666666"));
            binding.textView.setText("JOB APPLIED ");
        });
        binding.btnJobFollow.setOnClickListener(view -> {
            showJobHome("JobFollow");
            binding.btnJobFollow.setTextColor(Color.parseColor("#FF6600"));
            binding.btnJobApply.setTextColor(Color.parseColor("#666666"));
            binding.textView.setText("JOB FOLLOWED");
        });
        showJobHome("JobFollow");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showJobHome(String s) {
        rView = binding.listJob;
        jobList = new ArrayList<>();
        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabaseJobHeart = FirebaseDatabase.getInstance().getReference("UserActivity").child(userAuth.getUid()).child(s);
        mDatabaseJobHeart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotJob) {
                for(DataSnapshot postSnapshotJob : dataSnapshotJob.getChildren()){
                    JobActive jobActive = (JobActive) postSnapshotJob.getValue(JobActive.class);
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Job").child(jobActive.getJobID()+"");
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Job job = (Job) snapshot.getValue(Job.class);
                            DatabaseReference mDatabaseEmployer = FirebaseDatabase.getInstance().getReference("Employer/" + job.getEmployerID());
                            mDatabaseEmployer.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshotEmployer) {
                                    Employer employer = (Employer) dataSnapshotEmployer.getValue(Employer.class);

                                    job.setEmployer(employer);
                                    job.setStatus(jobActive.getStatus());
                                    job.setTimeApply(jobActive.getTimeApply());

                                    jobList.add(job);
                                    if(jobList.size() != 0){
                                        jobAdapter = new ListJobHomeAdapter(getContext(), jobList);
                                        LinearLayoutManager layout = new LinearLayoutManager(getContext());
                                        rView.setLayoutManager(layout);
                                        rView.setAdapter(jobAdapter);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}