package com.android.app_findjob.view.admin.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app_findjob.Admin_AddJob_Activity;
import com.android.app_findjob.adapter.ListJobHomeAdapter;
import com.android.app_findjob.databinding.FragmentManageJobAdminBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Job;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageJobAdminFragment extends Fragment {

    private RecyclerView rView;
    private ArrayList<Job> JobList;
    private ListJobHomeAdapter JobAdapter;
    private FragmentManageJobAdminBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManageJobAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        showJobHome();

        binding.btnAddJob.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), Admin_AddJob_Activity.class));
        });
        return root;
    }

    private void showJobHome() {

        rView = binding.listManageJob;
        JobList = new ArrayList<>();

        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabaseJobHeart = FirebaseDatabase.getInstance().getReference("Job");
        mDatabaseJobHeart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotJob) {
                JobList.removeAll(JobList);
                for (DataSnapshot postSnapshotJob : dataSnapshotJob.getChildren()) {
                    Job job = (Job) postSnapshotJob.getValue(Job.class);
                    DatabaseReference mDatabaseEmployer = FirebaseDatabase.getInstance().getReference("Employer/" + job.getEmployerID());
                    mDatabaseEmployer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotEmployer) {
                            Employer employer = (Employer) dataSnapshotEmployer.getValue(Employer.class);

                            job.setEmployer(employer);
                            JobList.add(job);

                            JobAdapter = new ListJobHomeAdapter(getContext(), JobList,true);
                            LinearLayoutManager layout = new LinearLayoutManager(getContext());
                            rView.setLayoutManager(layout);
                            rView.setAdapter(JobAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
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