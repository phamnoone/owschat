package com.example.user.task12;

/**
 * Created by USER on 6/15/2015.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.user.task12.forgot_pass.ForgotPass;
import com.example.user.task12.home.Home;
import com.example.user.task12.signed_up.SignedUp;
import com.example.user.task12.users.ListUsersAdapter;
import com.example.user.task12.users.Users;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;


public class LogIn extends ActionBarActivity {

    private static final String FIREBASE_URL = "https://ows-chat.firebaseio.com";
    private Firebase mFirebaseRef;
    private String name;
    private String pass;
    private String id="hieutt";
    private ValueEventListener mConnectedListener;
    private int x=0;
    private String from;
    private String to;
    ImageButton bntLogIn,bntSignedUp,bntForgot;
    EditText txtname;
    EditText txtpass;
    private ListUsersAdapter listUsersAdapter;
    List<Users> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("Users");
        txtname=(EditText)findViewById(R.id.txtID);
        txtpass=(EditText)findViewById(R.id.txtPass);
        bntLogIn=(ImageButton)findViewById(R.id.bntLogIn);
        bntSignedUp=(ImageButton)findViewById(R.id.bntSignedUp);
        bntForgot=(ImageButton)findViewById(R.id.bntForgot);
        bntSignedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignedUp();
            }
        });
        bntForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPass();
            }
        });
        bntLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });

    }

    private void LogIn(){
        for (int i=0;i<listUsersAdapter.getCount();i++){
            Users users=(Users) listUsersAdapter.getItem(i);
            if (txtname.getText().toString().equals(users.getName())&&
                    txtpass.getText().toString().equals(users.getPass())) {
                x=x+1;
                from=users.getName();
            }
        }
        if (x!=0){
            Intent intent=new Intent(LogIn.this,Home.class);
            Bundle bundle=new Bundle();
            bundle.putString("from",from);
            bundle.putString("URL",FIREBASE_URL);
            intent.putExtra("DATA", bundle);
            startActivity(intent);
            LogIn.this.finish();
        }
        else {
            new AlertDialog.Builder(LogIn.this).setTitle("SORRY")
                    .setMessage("Wrong account or password . Please re-enter.")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        }
        txtname.setText("");
        txtpass.setText("");
        x=0;
    }

    private void ForgotPass() {
        Intent intent2=new Intent(LogIn.this, ForgotPass.class);
        Bundle bundle2=new Bundle();
        bundle2.putString("URL",FIREBASE_URL);
        intent2.putExtra("DATA",bundle2);
        startActivity(intent2);
    }

    private void SignedUp() {
        Intent intent1=new Intent(LogIn.this,SignedUp.class);
        Bundle bundle1=new Bundle();
        bundle1.putString("URL",FIREBASE_URL);
        intent1.putExtra("DATA",bundle1);
        startActivity(intent1);
    }


    @Override
    public void onStart() {
        super.onStart();
        listUsersAdapter=new ListUsersAdapter(mFirebaseRef,this,R.layout.user,from,to,id);
        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    //Toast.makeText(MainActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(MainActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(LogIn.this).setTitle("CHECK CONNECT").setMessage("No connect")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
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

