package com.android.app_findjob.Rss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.app_findjob.R;

import java.util.ArrayList;

public class Blog_RSS extends AppCompatActivity {

    RecyclerView rv;
    ListView listView;
    ArrayList<String> arrayTitle, arrayImage,arrayAuthor,arrayType,arrayLink;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_rss);
        listView = (ListView) findViewById(R.id.lv_RSS);
        arrayTitle = new ArrayList<>();
        arrayLink=new ArrayList<>();
        adapter = new ArrayAdapter(Blog_RSS.this, android.R.layout.simple_list_item_1,arrayTitle);
        new ReadRSS(this,listView).execute("https://itviec.com/blog/feed/");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Blog_RSS.this, Web_Blog.class);
                intent.putExtra("LinkTinTuc",arrayLink.get(i));
                startActivity(intent);
            }
        });

    }
}