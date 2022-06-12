package com.android.app_findjob.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.app_findjob.Rss.Blog_RSS;
import com.android.app_findjob.databinding.FragmentBlogBinding;

public class BlogFragment extends Fragment {
    private FragmentBlogBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBlogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent i = new Intent(getContext(),Blog_RSS.class);
        startActivity(i);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void showBlog()
    {
    }


}