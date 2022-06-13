package com.android.app_findjob.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.view.DetailEmployerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListEmployerFollowAdapter extends RecyclerView.Adapter<ListEmployerFollowAdapter.ListEmployerFollowViewHolder> {
    private Context mContext ;
    private ArrayList<Employer> employerList ;

    public ListEmployerFollowAdapter(Context mContext , ArrayList<Employer> employerList) {
        this.mContext = mContext;
        this.employerList = employerList;
    }

    @NonNull
    @Override
    public ListEmployerFollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_employer_follow_item, parent, false);

        return new ListEmployerFollowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListEmployerFollowViewHolder holder, int position) {
        Employer employer = employerList.get(position);
        Picasso.get()
                .load(employer.getImg())
                .into(holder.img);
        holder.txtName.setText(employer.getName());

        holder.layout.setOnClickListener(view ->{
            Intent i = new Intent(mContext, DetailEmployerActivity.class);
            Bundle mBundle  = new Bundle();
            mBundle.putInt("IDEmployer",employer.getEmployerID());
            i.putExtras(mBundle);
            mContext.startActivity(i);
        });

    }


    @Override
    public int getItemCount() {
        return employerList.size();
    }

    class ListEmployerFollowViewHolder extends RecyclerView.ViewHolder {
        private ImageView img ;
        private TextView txtName ;
        private LinearLayout layout;
        public ListEmployerFollowViewHolder(@NonNull View v) {
            super(v);
            layout = v.findViewById(R.id.layoutEmployerFollow);
            img = v.findViewById(R.id.img);
            txtName = v.findViewById(R.id.txt_name);

        }

    }
}
