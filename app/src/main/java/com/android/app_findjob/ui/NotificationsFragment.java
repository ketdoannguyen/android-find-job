package com.android.app_findjob.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_findjob.R;
import com.android.app_findjob.adapter.ListEmployerHomeAdapter;
import com.android.app_findjob.adapter.ListJobHomeAdapter;
import com.android.app_findjob.adapter.ListNotificationAdapter;
import com.android.app_findjob.databinding.FragmentNotificationsBinding;
import com.android.app_findjob.model.Employer;
import com.android.app_findjob.model.Job;
import com.android.app_findjob.model.Notification;
import com.android.app_findjob.ui.notifications.NotificationsViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView rView;
    private ArrayList<Notification> list;
    private ListNotificationAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.imgMenu.setOnClickListener(view -> {
            MainMenu(view);

        });

        showNotification();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showNotification() {
        rView = binding.recyclerNotification;
        list = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Notifications");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.removeAll(list);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Notification notification  = (Notification) postSnapshot.getValue(Notification .class);
                    list.add(notification);
                }
                adapter = new ListNotificationAdapter(getContext(), list);
                LinearLayoutManager layout = new LinearLayoutManager(getContext());
                rView.setLayoutManager(layout);
                rView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void MainMenu(View v)  {
        PopupMenu popup = new PopupMenu(getContext(),v, Gravity.RIGHT);
//        popup.inflate(R.menu.main_menu_notification);
//
//        Menu menu = popup.getMenu();
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon",boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.main_menu_notification, popup.getMenu());

        // com.android.internal.view.menu.MenuBuilder
        // Register Menu Item Click event.
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClicked(item);
            }
        });
        popup.show();
    }
    private boolean menuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRead:
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Notifications");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Notification notification  = (Notification) postSnapshot.getValue(Notification .class);
                            notification.setStatus("read");
                            mDatabase.child(notification.getId()+"").setValue(notification);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Toast.makeText(getContext(),"Mark read all success",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuSetting:
                Toast.makeText(getContext(), "Setting", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}