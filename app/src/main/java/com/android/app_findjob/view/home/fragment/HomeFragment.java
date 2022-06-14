package com.android.app_findjob.view.home.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListEmployerHomeAdapter;
import com.android.app_findjob.adapter.ListJobHomeAdapter;
import com.android.app_findjob.databinding.FragmentHomeBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rView1, rView2;
    private ArrayList<Job> jobList;
    private ListJobHomeAdapter jobAdapter;
    private ArrayList<Employer> employerList;
    private ListEmployerHomeAdapter employerAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        showEmployerHome();
        showJobHome();

        binding.txtSearch.setOnClickListener(view -> {
            showDialogSearch();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showJobHome() {
        rView1 = binding.listJob;
        jobList = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Job");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotJob) {
                jobList.removeAll(jobList);
                for (DataSnapshot postSnapshotJob : dataSnapshotJob.getChildren()) {
                    Job job = (Job) postSnapshotJob.getValue(Job.class);
                    DatabaseReference mDatabaseEmployer = FirebaseDatabase.getInstance().getReference("Employer/" + job.getEmployerID());
                    mDatabaseEmployer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotEmployer) {
                            Employer employer = (Employer) dataSnapshotEmployer.getValue(Employer.class);

                            job.setEmployer(employer);
                            jobList.add(job);

                            jobAdapter = new ListJobHomeAdapter(getContext(), jobList);
                            LinearLayoutManager layout = new LinearLayoutManager(getContext());
                            rView1.setLayoutManager(layout);
                            rView1.setAdapter(jobAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void showEmployerHome() {
        rView2 = binding.recyclerViewEmployer;
        employerList = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Employer");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                employerList.removeAll(employerList);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Employer employer = (Employer) postSnapshot.getValue(Employer.class);
                    employerList.add(employer);
                }
                employerAdapter = new ListEmployerHomeAdapter(getContext(), employerList);
                LinearLayoutManager layout = new LinearLayoutManager(getContext());
                layout.setOrientation(RecyclerView.HORIZONTAL);
                rView2.setLayoutManager(layout);
                rView2.setAdapter(employerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void showDialogSearch() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_search);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.TOP;

//        EditText editText = dialog.findViewById(R.id.layout);
        dialog.show();
    }
}