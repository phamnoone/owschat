package com.example.user.task12.signed_up;

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
 * Created by USER on 5/23/2015.
 */
public class SignedUp extends ActionBarActivity{
    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private String name,pass,pas,email;
    private int temp=0,checkPass,checkName;
    Button bntSign;
    EditText signName,signPass,signPas,signEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signed_up);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = SignedUp.this.getSupportActionBar();
        actionBar.setTitle("SIGNED UP");
        bntSign=(Button)findViewById(R.id.bntSign);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("Users");
        bntSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                checkName=CheckName(name);
                new AlertDialog.Builder(SignedUp.this).setTitle("Confirm").setMessage("Are you true")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkName=CheckName(name);
                                CheckSignedUp();
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    private void getData() {
        signName=(EditText)findViewById(R.id.signName);
        signPass=(EditText)findViewById(R.id.signPass);
        signPas=(EditText)findViewById(R.id.signPas);
        signEmail=(EditText)findViewById(R.id.signEmail);
        name=signName.getText().toString();
        pass=signPass.getText().toString();
        pas=signPas.getText().toString();
        email=signEmail.getText().toString();
    }

    private void CheckSignedUp(){

        if (name.equals("")||pass.equals("")||pas.equals("")||email.equals(""))
            Toast.makeText(SignedUp.this,"Ban Phai Nhap Day Du Thong Tin",Toast.LENGTH_LONG).show();
        else if (!pass.equals(pas)) Toast.makeText(SignedUp.this,"Mat Khau Phai Giong Nhau!",Toast.LENGTH_LONG).show();
        else if (pass.length()<6) Toast.makeText(SignedUp.this,"Mat Khau Qua Ngan!",Toast.LENGTH_LONG).show();
        else if (checkName==1) {Toast.makeText(SignedUp.this,"Tai Khoan Da Ton Tai!",Toast.LENGTH_LONG).show();
            temp=0;
        }
        else {
            Users user = new Users(name, pass, email);
            mFirebaseRef.push().setValue(user);
            new AlertDialog.Builder(SignedUp.this).setTitle("Succeed")
                    .setMessage("Account has been activated")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            SignedUp.this.finish();
                        }
                    }).show();

        }

    }
    private int CheckName(String name){
        Query query = mFirebaseRef.orderByChild("name").equalTo(name);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                temp = 1;
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
                    //Toast.makeText(SignedUp.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(SignedUp.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(SignedUp.this).setTitle("CHECK CONNECT").setMessage("No connect.")
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
        SignedUp.this.finish();
    }

}
