package com.android.app_findjob.view.home.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListEmployerFollowAdapter;
import com.android.app_findjob.adapter.ListEmployerHomeAdapter;
import com.android.app_findjob.adapter.ListJobHomeAdapter;
import com.android.app_findjob.databinding.FragmentEmployerFollowBinding;
import com.android.app_findjob.databinding.FragmentNotificationsBinding;
import com.android.app_findjob.databinding.FragmentSettingBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.EmployerFollow;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.model.JobActive;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EmployerFollowFragment extends Fragment {
    private FragmentEmployerFollowBinding binding;

    private RecyclerView rView;
    private ArrayList<Employer> employerList;
    private ListEmployerFollowAdapter employerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEmployerFollowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        showEmployerHome();
        return root;
    }

    private void showEmployerHome() {
        rView = binding.listEmployerFollow;
        employerList = new ArrayList<>();

        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabaseJobHeart = FirebaseDatabase.getInstance().getReference("UserActivity").child(userAuth.getUid()).child("EmployerFollow");
        mDatabaseJobHeart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotJob) {
                for (DataSnapshot postSnapshotJob : dataSnapshotJob.getChildren()) {
                    EmployerFollow mEmployerFollow = (EmployerFollow) postSnapshotJob.getValue(EmployerFollow.class);
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Employer").child(mEmployerFollow.getEmployerID() + "");
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Employer employer = (Employer) dataSnapshot.getValue(Employer.class);
                            employerList.add(employer);

                            employerAdapter = new ListEmployerFollowAdapter(getContext(), employerList , false);

                            GridLayoutManager layout = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                            rView.setLayoutManager(layout);
                            rView.setAdapter(employerAdapter);
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