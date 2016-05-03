package com.example.user.task12.forgot_pass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.task12.R;
import com.example.user.task12.users.Users;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by USER on 5/24/2015.
 */
public class ForgotPass extends ActionBarActivity {
    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private String name,pass,email;
    private int temp=0,checkEmail=0,checkName;
    private Button bntOK;
    private EditText txtname,txtemail;
    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = ForgotPass.this.getSupportActionBar();
        actionBar.setTitle("FORGOT PASSWORD");
        txtname=(EditText)findViewById(R.id.takePassName);
        txtemail=(EditText)findViewById(R.id.takePassEmail);
        bntOK=(Button)findViewById(R.id.bntOK);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        mFirebaseRef = new Firebase(FIREBASE_URL).child("Users");
        bntOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=txtname.getText().toString();
                email=txtemail.getText().toString();
                checkName= CheckName(name,email);

                new AlertDialog.Builder(ForgotPass.this).setTitle("Take Password").setMessage("Are you true")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkName=CheckName(name,email);
                                TakePass();
                                checkName=0;
                                temp=0;
                                dialog.cancel();
                            }
                        }).show();
            }
        });

    }



    private void TakePass() {


        if (checkName==0) Toast.makeText(ForgotPass.this,"Thong Tin Khong Chinh Xac!",Toast.LENGTH_LONG).show();
        else {
            new AlertDialog.Builder(ForgotPass.this).setTitle("Confirm")
                    .setMessage("Your Password:\""+pass+"\"")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ForgotPass.this.finish();
                        }
                    }).show();
        };
        temp=0;
    }

    private int CheckName(String name, final String email){
        Query query = mFirebaseRef.orderByChild("name").equalTo(name);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> data =  (Map<String, Object>) dataSnapshot.getValue();
                if (email.equals(data.get("email").toString())) {
                    pass = data.get("pass").toString();
                    temp = 1;
                    data.clear();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return temp;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    //Toast.makeText(ForgotPass.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(ForgotPass.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(ForgotPass.this).setTitle("CHECK CONNECT").setMessage("No connect")
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
        getMenuInflater().inflate(R.menu.menu_simple, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_close:
                close();
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }


    private void close(){
        ForgotPass.this.finish();
    }
}
