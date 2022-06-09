package com.android.app_findjob.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.view.DetailEmployerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListEmployerHomeAdapter extends RecyclerView.Adapter<ListEmployerHomeAdapter.ListEmployerHomeViewHolder> {
    private Context mContext ;
    private ArrayList<Employer> employerList ;

    public ListEmployerHomeAdapter(Context mContext , ArrayList<Employer> employerList) {
        this.mContext = mContext;
        this.employerList = employerList;
    }

    @NonNull
    @Override
    public ListEmployerHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_employer_item_home, parent, false);

        return new ListEmployerHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListEmployerHomeViewHolder holder, int position) {
        Employer employer = employerList.get(position);
        Picasso.get()
                .load(employer.getImg())
                .into(holder.img);

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

    class ListEmployerHomeViewHolder extends RecyclerView.ViewHolder {
        private ImageView img ;
        private RelativeLayout layout ;
        public ListEmployerHomeViewHolder(@NonNull View v) {
            super(v);
            layout = v.findViewById(R.id.layout_employer);
            img = v.findViewById(R.id.img_employer);
        }

    }
}
