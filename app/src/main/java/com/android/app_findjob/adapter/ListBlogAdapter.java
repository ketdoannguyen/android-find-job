package com.android.app_findjob.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.Rss.Web_Blog;
import com.android.app_findjob.model.Blog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListBlogAdapter extends RecyclerView.Adapter<ListBlogAdapter.ListBlogViewHolder> {
    private ArrayList<Blog> BlogList ;
    private Context mContext ;

    public ListBlogAdapter(Context mContext , ArrayList<Blog> BlogList) {
        this.mContext = mContext;
        this.BlogList = BlogList;
    }


    @NonNull
    @Override
    public ListBlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_blog_item, parent, false);

        return new ListBlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBlogViewHolder holder, int position) {
        Blog Blog = BlogList.get(position);

        Picasso.get()
                .load(Blog.getImg())
                .into(holder.img);
        holder.txtTitle.setText(Blog.getTitle());

        holder.layutBlog.setOnClickListener(view -> {
            Intent intent=new Intent(mContext, Web_Blog.class);
            intent.putExtra("LinkTinTuc", Blog.getLink());
            mContext.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return BlogList.size();
    }

    class ListBlogViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private ImageView img;
        private LinearLayout layutBlog ;

        public ListBlogViewHolder(@NonNull View v) {
            super(v);
            txtTitle = v.findViewById(R.id.txt_title);
            img = v.findViewById(R.id.img);
            layutBlog = v.findViewById(R.id.layoutBlog);
        }

    }
}
