package com.android.app_findjob.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.adapter.ListJobHomeAdaterr;
import com.android.app_findjob.databinding.FragmentHomeBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.ui.home.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rView ;
    private ArrayList<Job> jobList ;
    private ListJobHomeAdaterr jobAdapter ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        showJobHome();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showJobHome(){
        rView = binding.listJob ;
        jobList = new ArrayList<>();
        Job job1 = new Job(0,"Java Developer","Java MySQL Spring English","~3000$","Da Nang",new Employer("https://itviec.com/rails/active_storage/representations/proxy/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBMmZvSXc9PSIsImV4cCI6bnVsbCwicHVyIjoiYmxvYl9pZCJ9fQ==--a72d8a6545664966af9f6674fde5e1164b55ced4/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaDdCem9MWm05eWJXRjBPZ2wzWldKd09oSnlaWE5wZW1WZmRHOWZabWwwV3dkcEFhb3ciLCJleHAiOm51bGwsInB1ciI6InZhcmlhdGlvbiJ9fQ==--a364054a300021d6ece7f71365132a9777e89a21/Logo%20MB%20he%20mau%20RGB%2001.png"));
        Job job2 = new Job(1,"UI Frontend Engineer","HTTML5 CSS JavaScript","3000$-5000$","TP HCM",new Employer("https://itviec.com/rails/active_storage/representations/proxy/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBODF1SkE9PSIsImV4cCI6bnVsbCwicHVyIjoiYmxvYl9pZCJ9fQ==--595dd5debbc6d4f7b9b22ddeca4bb33da94b25ca/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaDdCem9MWm05eWJXRjBPZ2wzWldKd09oSnlaWE5wZW1WZmRHOWZabWwwV3dkcEFhb3ciLCJleHAiOm51bGwsInB1ciI6InZhcmlhdGlvbiJ9fQ==--a364054a300021d6ece7f71365132a9777e89a21/Naver_Logo(2)-white.png"));
        jobList.add(job1);
        jobList.add(job2);
        jobAdapter = new ListJobHomeAdaterr(getContext(),jobList);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        rView.setLayoutManager(layout);
        rView.setAdapter(jobAdapter);
    }
}