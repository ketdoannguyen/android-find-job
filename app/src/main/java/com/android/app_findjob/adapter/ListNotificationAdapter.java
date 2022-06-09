package com.android.app_findjob.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.model.Notification;
import com.android.app_findjob.view.DetailJobActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListNotificationAdapter extends RecyclerView.Adapter<ListNotificationAdapter.ListNotificationViewHolder> {
    private Context mContext ;
    private ArrayList<Notification> notificationList ;

    public ListNotificationAdapter(Context mContext , ArrayList<Notification> notificationList) {
        this.mContext = mContext;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ListNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_notification, parent, false);

        return new ListNotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);

        holder.txtTitle.setText(notification.getTitle());
        holder.txtTime.setText(notification.getTime());
        holder.txtDetail.setText(notification.getDetail());

        holder.layout.setOnClickListener(view -> {
            holder.showMenu(view,notification);
        });

        if(notification.getStatus().equals("unread")){
            holder.btnStatus.setVisibility(View.VISIBLE);
        }else{
            holder.btnStatus.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class ListNotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle,txtDetail,txtTime;
        private RelativeLayout layout ;
        private ImageButton btnStatus ;
        public ListNotificationViewHolder(@NonNull View v) {
            super(v);
            layout = v.findViewById(R.id.layoutNotification);
            txtTime = v.findViewById(R.id.txt_time);
            txtTitle = v.findViewById(R.id.txt_title);
            txtDetail = v.findViewById(R.id.txt_detail);
            btnStatus = v.findViewById(R.id.btn_status);
        }
        private void showMenu(View v,Notification notification){
            PopupMenu popup = new PopupMenu(mContext,v);
            popup.inflate(R.menu.show_menu_notification);

            Menu menu = popup.getMenu();
            // com.android.internal.view.menu.MenuBuilder

            // Register Menu Item Click event.
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return menuItemClicked(item,notification);
                }
            });
            popup.show();
        }
        private boolean menuItemClicked(MenuItem item,Notification notification) {
            switch (item.getItemId()) {
                case R.id.menuDetail:
                    Toast.makeText(mContext, "Detail", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menuRead:
                    DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference("Notifications/"+notification.getId()+"");
                    notification.setStatus("read");
                    mDatabase1.setValue(notification);
                    btnStatus.setVisibility(View.GONE);
                    Toast.makeText(mContext,"Read success",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menuDelete:
                    DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("Notifications/"+notification.getId()+"");
                    mDatabase2.removeValue();
                    Toast.makeText(mContext,"Delete success",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menuReport:
                    Toast.makeText(mContext,"Report success",Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    }


}
