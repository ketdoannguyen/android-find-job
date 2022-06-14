package com.android.app_findjob.view.admin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app_findjob.R;
import com.android.app_findjob.databinding.FragmentManageBlogAdminBinding;
import com.android.app_findjob.databinding.FragmentManageEmployerAdminBinding;


public class ManageEmployerAdminFragment extends Fragment {
    private FragmentManageEmployerAdminBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManageEmployerAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
}