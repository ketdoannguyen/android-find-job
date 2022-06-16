package com.android.app_findjob.adapter;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.User;
import com.android.app_findjob.view.home.activity.DetailEmployerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ListEmployerFollowAdapter extends RecyclerView.Adapter<ListEmployerFollowAdapter.ListEmployerFollowViewHolder> {
    private Context mContext ;
    private ArrayList<Employer> employerList ;
    private Boolean isAdmin ;

    public ListEmployerFollowAdapter(Context mContext , ArrayList<Employer> employerList , Boolean isAdmin) {
        this.mContext = mContext;
        this.employerList = employerList;
        this.isAdmin = isAdmin ;
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
            if(!isAdmin){
                Intent i = new Intent(mContext, DetailEmployerActivity.class);
                Bundle mBundle  = new Bundle();
                mBundle.putInt("IDEmployer",employer.getEmployerID());
                i.putExtras(mBundle);
                mContext.startActivity(i);
            }else{
                holder.showMenu(view,employer);
            }
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
        public void showMenu(View v, Employer employer) {
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
                    return menuItemClicked(item, employer);
                }
            });
            popup.show();
        }

        private boolean menuItemClicked(MenuItem item, Employer employer) {
            switch (item.getItemId()) {
                case R.id.menuDetail:
                    break;
                case R.id.menuEdit:
                    break;
                case R.id.menuDelete:
                    DatabaseReference mDatabaseJobHeart = FirebaseDatabase.getInstance().getReference("Employer").child(employer.getEmployerID()+"");
                    mDatabaseJobHeart.removeValue();
                    Toast.makeText(mContext, "Delete employer success", Toast.LENGTH_SHORT).show();
                    employerList.remove(employer);
                    break;
            }
            return true;
        }


    }

}
