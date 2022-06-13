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

import com.android.app_findjob.model.Blog;
import com.android.app_findjob.view.DetailJobActivity;
import com.android.app_findjob.R;
import com.android.app_findjob.model.Job;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListJobHomeAdapter extends RecyclerView.Adapter<ListJobHomeAdapter.ListJobHomeViewHolder> {
    private Context mContext ;
    private ArrayList<Job> jobList ;

    public ListJobHomeAdapter(Context mContext , ArrayList<Job> jobList) {
        this.mContext = mContext;
        this.jobList = jobList;
    }


    @NonNull
    @Override
    public ListJobHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_job_item_home, parent, false);

        return new ListJobHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListJobHomeViewHolder holder, int position) {
        Job job = jobList.get(position);
        Picasso.get()
                .load(job.getEmployer().getImg())
                .into(holder.img);
        holder.txtName.setText(job.getName());
        holder.txtSalary.setText(job.getSalary());
        holder.txtSkill.setText(job.getSkill());
        holder.txtCity.setText(job.getCity());

        holder.layout.setOnClickListener(view ->{
            Intent i = new Intent(mContext, DetailJobActivity.class);
            Bundle mBundle  = new Bundle();
            mBundle.putInt("IDJob",job.getId());
            mBundle.putInt("IDEmployer",job.getEmployerID());
            mBundle.putString("ImgEmployer",job.getEmployer().getImg());
            mBundle.putString("NameEmployer",job.getEmployer().getName());
            i.putExtras(mBundle);
            mContext.startActivity(i);
        });

        if(job.getStatus() != null && !job.getStatus().equals("")){
            holder.layoutApply.setVisibility(View.VISIBLE);
            holder.txtStatus.setText(job.getStatus());
            holder.txtTimeApply.setText(job.getTimeApply());
        }else{
            holder.layoutApply.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return jobList.size();
    }

    class ListJobHomeViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName,txtSalary,txtSkill,txtCity,txtStatus,txtTimeApply;
        private ImageView img ;
        private RelativeLayout layout ;
        private LinearLayout layoutApply ;
        public ListJobHomeViewHolder(@NonNull View v) {
            super(v);
            layout = v.findViewById(R.id.lyViewListPink);
            img = v.findViewById(R.id.imgViewEmployer);
            txtName = v.findViewById(R.id.txtNameJob);
            txtSalary = v.findViewById(R.id.txtSalaryJob);
            txtSkill = v.findViewById(R.id.txtSkillJob);
            txtCity = v.findViewById(R.id.txtCityJob);
            layoutApply = v.findViewById(R.id.layoutApply);
            txtStatus = v.findViewById(R.id.txtStatus);
            txtTimeApply= v.findViewById(R.id.txtTimeApply);
        }

    }
}
