package com.android.app_findjob.view.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.app_findjob.R;
import com.android.app_findjob.databinding.ActivityAdminBinding;
import com.android.app_findjob.databinding.ActivityDetailEmployerBinding;
import com.android.app_findjob.model.User;
import com.android.app_findjob.view.admin.fragment.HomeAdminFragment;
import com.android.app_findjob.view.admin.fragment.ManageBlogAdminFragment;
import com.android.app_findjob.view.admin.fragment.ManageEmployerAdminFragment;
import com.android.app_findjob.view.admin.fragment.ManageJobAdminFragment;
import com.android.app_findjob.view.admin.fragment.ManageUserAdminFragment;
import com.android.app_findjob.view.home.fragment.SettingFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActivityAdminBinding binding;

    private static final int FRAGMENT_HOME = 0 ;
    private static final int FRAGMENT_JOB = 1 ;
    private static final int FRAGMENT_EMPLOYER = 2 ;
    private static final int FRAGMENT_BLOG = 3 ;
    private static final int FRAGMENT_USER = 4;

    private int mCurrentFragment = FRAGMENT_HOME ;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.navigation_view_open,R.string.navigation_view_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navigationView.setNavigationItemSelectedListener(this);

        Intent i = getIntent();
        String check = i.getStringExtra("AddEmployer");
        if(check!=null && check.equals("true")){
            replaceFragment(new ManageEmployerAdminFragment());
            mCurrentFragment = FRAGMENT_EMPLOYER;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home_admin){
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeAdminFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        }else if(id == R.id.nav_job){
            if(mCurrentFragment != FRAGMENT_JOB){
                replaceFragment(new ManageJobAdminFragment());
                mCurrentFragment = FRAGMENT_JOB;
            }
        }else if(id == R.id.nav_employer){
            if(mCurrentFragment != FRAGMENT_EMPLOYER){
                replaceFragment(new ManageEmployerAdminFragment());
                mCurrentFragment = FRAGMENT_EMPLOYER;
            }
        }else if(id == R.id.nav_blog){
            if(mCurrentFragment != FRAGMENT_BLOG){
                replaceFragment(new ManageBlogAdminFragment());
                mCurrentFragment = FRAGMENT_BLOG;
            }
        }else if(id == R.id.nav_user){
            if(mCurrentFragment != FRAGMENT_USER){
                replaceFragment(new ManageUserAdminFragment());
                mCurrentFragment = FRAGMENT_USER;
            }
        }else if(id == R.id.nav_logout){

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment mFragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.context_fragment,mFragment);
        transaction.commit();
    }

    //    public void SetName(String name)
//    {
//        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_txt_admin);
//        navUsername.setText(name);
//    }
//
//    public void SetNaviGation()
//    {
//        final Toolbar toolbar=findViewById(R.id.toolbar_drawer);
//        setSupportActionBar(toolbar);
//        drawerLayout=findViewById(R.id.layoutChinh);
//////        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.mo,R.string.dong);
////        drawerLayout.addDrawerListener(toggle);
////        toggle.syncState();
//
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id=item.getItemId();
////                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////                if(id==R.id.nav_DangNhap) {
////                    if(tk==null) startActivity(new Intent(getApplicationContext(), DangNhap.class));
////                    else
////                    {
////                        Intent dn = new Intent(MainActivity.this, MainActivity.class);
////                        dn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                        Toast.makeText(MainActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
////                        startActivity(dn);
////                    }
////                    drawerLayout.closeDrawer(GravityCompat.START);
////                    return true;
////                }
//
////                if(id==R.id.nav_DangXuat_admin) {
////                    Intent dn = new Intent(MainActivity.this, MainActivity.class);
////                    dn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                    Toast.makeText(MainActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
////                    startActivity(dn);
////                    drawerLayout.closeDrawer(GravityCompat.START);
////                    return true;
////                }
////
////                if(id == R.id.nav_home || id==R.id.nav_home_admin)
////                {
////                    Fragment fragment = new Home_Fragment();
////                    transaction.replace(R.id.noiDung, fragment);
////                }
////                if(id == R.id.navigation_job)
////                {
////                    Fragment fragment= new Manage_Job_Fragment(tk);
////                    transaction.replace(R.id.noiDung, fragment);
////                }
////                if(id == R.id.nav_)
////                {
////                    Fragment fragment= new Manage_Company_Fragment();
////                    transaction.replace(R.id.noiDung, fragment);
////                }
////                if(id == R.id.navigation_blog)
////                {
////                    Fragment fragment= new Manage_Blog_Fragment();
////                    transaction.replace(R.id.noiDung, fragment);
////                }
////                if(id == R.id.nav_profile)
////                {
////                    Fragment fragment= new Manage_Profile_Fragment();
////                    transaction.replace(R.id.noiDung, fragment);
////                }
//
//
//
//                transaction.commit();
//                drawerLayout.closeDrawer(GravityCompat.START);
//                return true;
//            }
//        });
//    }
}