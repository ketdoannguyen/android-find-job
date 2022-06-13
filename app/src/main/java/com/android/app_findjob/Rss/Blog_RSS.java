package com.android.app_findjob.Rss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListBlogAdapter;
import com.android.app_findjob.adapter.ListEmployerHomeAdapter;
import com.android.app_findjob.adapter.ListJobHomeAdapter;
import com.android.app_findjob.databinding.ActivityBlogRssBinding;
import com.android.app_findjob.databinding.ActivityHomeBinding;
import com.android.app_findjob.model.Blog;
import com.android.app_findjob.model.Employer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Blog_RSS extends AppCompatActivity {


    private RecyclerView rView;
    private ArrayList<Blog> blogList;
    private ListBlogAdapter blogAdapter;

    private ActivityBlogRssBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogRssBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        blogList = new ArrayList<>();
        showData();

    }

    private void showData(){
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
                blogAdapter = new ListBlogAdapter(Blog_RSS.this,blogList);
                LinearLayoutManager layout = new LinearLayoutManager(Blog_RSS.this);
                rView.setLayoutManager(layout);
                rView.setAdapter(blogAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}