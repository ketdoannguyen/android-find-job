package com.android.app_findjob.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.model.Employer;
import com.android.app_findjob.view.home.activity.DetailEmployerActivity;
import com.android.app_findjob.view.home.activity.DetailJobActivity;
import com.android.app_findjob.R;
import com.android.app_findjob.model.Job;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ListJobHomeAdapter extends RecyclerView.Adapter<ListJobHomeAdapter.ListJobHomeViewHolder> {
    private Context mContext ;
    private ArrayList<Job> jobList ;
    private Boolean isAdmin;

    public ListJobHomeAdapter(Context mContext , ArrayList<Job> jobList , Boolean isAdmin) {
        this.mContext = mContext;
        this.jobList = jobList;
        this.isAdmin = isAdmin;
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
            if(!isAdmin){
                Intent i = new Intent(mContext, DetailJobActivity.class);
                Bundle mBundle  = new Bundle();
                mBundle.putInt("IDJob",job.getId());
                mBundle.putInt("IDEmployer",job.getEmployerID());
                mBundle.putString("ImgEmployer",job.getEmployer().getImg());
                mBundle.putString("NameEmployer",job.getEmployer().getName());
                i.putExtras(mBundle);
                mContext.startActivity(i);
            }else{
                holder.showMenu(view,job);
            }

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
        public void showMenu(View v, Job job) {
            PopupMenu popup = new PopupMenu(mContext, v, Gravity.RIGHT);

            try {
                Field[] fields = popup.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popup);
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            popup.getMenuInflater().inflate(R.menu.show_menu_user, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return menuItemClicked(item, job);
                }
            });
            popup.show();
        }

        private boolean menuItemClicked(MenuItem item, Job job) {
            switch (item.getItemId()) {
                case R.id.menuDetail:
                    break;
                case R.id.menuEdit:
                    break;
                case R.id.menuDelete:
                    DatabaseReference mDatabaseJobHeart = FirebaseDatabase.getInstance().getReference("Employer").child(job.getId()+"");
                    mDatabaseJobHeart.removeValue();
                    Toast.makeText(mContext, "Delete job success", Toast.LENGTH_SHORT).show();
                    jobList.remove(job);
                    break;
            }
            return true;
        }

    }
}
