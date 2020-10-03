package com.example.meetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private Toolbar mtoolbar;
    private ViewPager myviewpager;
    private TabLayout mytablayout;
    private TabsAccessorAdapter mytabaccessoradapter;
    private static final String TAG="ERROR";
    private FirebaseAuth.AuthStateListener mAuthlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mtoolbar=(Toolbar)findViewById(R.id.maintoolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Log.i(TAG,"ONCREATE");
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        myviewpager=(ViewPager) findViewById(R.id.main_tabs_pager);
        mytabaccessoradapter=new TabsAccessorAdapter(getSupportFragmentManager());
        myviewpager.setAdapter(mytabaccessoradapter);

        mytablayout=(TabLayout)findViewById(R.id.main_tabs);
        mytablayout.setupWithViewPager(myviewpager);
        //mytablayout.getTabAt(0).setIcon(R.drawable.chaticon);
        //mytablayout.getTabAt(1).setIcon(R.drawable.contactico);
        getpermissions();
    }

    private void getpermissions() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS},1);
        }

    }


    @Override
 protected void onStart() {
        super.onStart();
   if(FirebaseAuth.getInstance().getCurrentUser()==null)
    {
        sendUserToLoginActivity();
    }

    }

    private void sendUserToLoginActivity() {

        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class
        );
        startActivity(loginIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.options_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.find_friends)
        {

        }
        if(item.getItemId()==R.id.setting)
        {

        }
        if(item.getItemId()==R.id.signout)
        {
            mAuth.signOut();
            sendUserToLoginActivity();
        }
        return true;
    }

    

}