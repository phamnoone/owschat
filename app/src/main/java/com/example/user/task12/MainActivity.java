package com.example.user.task12;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;


public class MainActivity extends ActionBarActivity {

    private static final String FIREBASE_URL = "https://zolla.firebaseio.com";
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private int x=0;
    ImageButton bntLog,bntFacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.log_in);
        mFirebaseRef = new Firebase(FIREBASE_URL).child("Users");
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Setup our Firebase mFirebaseRef
        bntLog=(ImageButton)findViewById(R.id.bntlog);
        bntFacebook=(ImageButton)findViewById(R.id.bntFacebook);
        bntFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle("SORRY").setMessage("Comming soon")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        bntLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LogIn.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}
