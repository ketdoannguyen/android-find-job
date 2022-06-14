package com.android.app_findjob.view.admin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app_findjob.R;
import com.android.app_findjob.databinding.FragmentBlogBinding;
import com.android.app_findjob.databinding.FragmentHomeAdminBinding;
import com.android.app_findjob.databinding.FragmentNotificationsBinding;


public class HomeAdminFragment extends Fragment {
    private FragmentHomeAdminBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
}