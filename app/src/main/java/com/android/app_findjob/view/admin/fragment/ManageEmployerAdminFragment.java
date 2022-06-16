package com.android.app_findjob.view.admin.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app_findjob.Admin_AddEmployer_Activity;
import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListEmployerFollowAdapter;
import com.android.app_findjob.databinding.FragmentManageBlogAdminBinding;
import com.android.app_findjob.databinding.FragmentManageEmployerAdminBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.EmployerFollow;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ManageEmployerAdminFragment extends Fragment {
    private FragmentManageEmployerAdminBinding binding;
    private RecyclerView rView;
    private ArrayList<Employer> employerList;
    private ListEmployerFollowAdapter employerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManageEmployerAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        showEmployerHome();

        binding.btnAdd.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), Admin_AddEmployer_Activity.class));
        });

        return root;
    }
    private void showEmployerHome() {
        rView = binding.listEmployerFollow;
        employerList = new ArrayList<>();

        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabaseJobHeart = FirebaseDatabase.getInstance().getReference("Employer");
        mDatabaseJobHeart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotJob) {
                for (DataSnapshot postSnapshotJob : dataSnapshotJob.getChildren()) {
                    Employer employer = (Employer) postSnapshotJob.getValue(Employer.class);
                    employerList.add(employer);

                    employerAdapter = new ListEmployerFollowAdapter(getContext(), employerList , true);

                    GridLayoutManager layout = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                    rView.setLayoutManager(layout);
                    rView.setAdapter(employerAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}