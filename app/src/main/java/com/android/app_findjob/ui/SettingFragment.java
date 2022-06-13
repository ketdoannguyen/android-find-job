package com.android.app_findjob.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app_findjob.R;
import com.android.app_findjob.databinding.FragmentProfileBinding;
import com.android.app_findjob.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.linkBack.setOnClickListener(view -> {
//            requireActivity().getSupportFragmentManager().popBackStack();
            Fragment mFragment = new ProfileFragment();
            getFragmentManager().beginTransaction().replace(R.id.setting, mFragment).commit();
        });



        return  root;
    }
}