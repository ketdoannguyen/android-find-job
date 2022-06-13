package com.android.app_findjob.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.adapter.ListBlogAdapter;
import com.android.app_findjob.databinding.FragmentBlogBinding;
import com.android.app_findjob.model.Blog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlogFragment extends Fragment {
    private FragmentBlogBinding binding;
    private RecyclerView rView;
    private ArrayList<Blog> blogList;
    private ListBlogAdapter blogAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBlogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        Intent i = new Intent(getContext(),Blog_RSS.class);
//        startActivity(i);
        blogList = new ArrayList<>();
        showBlog();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showBlog(){
        rView = binding.listBlog;
        blogList = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Blog");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                blogList.removeAll(blogList);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Blog blog = (Blog) postSnapshot.getValue(Blog.class);
                    blogList.add(blog);
                }
                blogAdapter = new ListBlogAdapter(getContext(),blogList);
                LinearLayoutManager layout = new LinearLayoutManager(getContext());
                rView.setLayoutManager(layout);
                rView.setAdapter(blogAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}