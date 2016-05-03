package com.example.user.task12.more;

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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by USER on 6/4/2015.
 */
public class CreatGroupChat extends ActionBarActivity {
    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private String id;
    private int temp=0,checkPass,checkid;
    private String from;
    Button bntCreat;
    EditText txtIDGroupChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_chat);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = CreatGroupChat.this.getSupportActionBar();
        actionBar.setTitle("CREAT GROUP CHAT");
        bntCreat=(Button)findViewById(R.id.bntCreat);
        txtIDGroupChat=(EditText)findViewById(R.id.txtIDGroupChat);
        getData();
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("GroupChat");
        bntCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=txtIDGroupChat.getText().toString();
                checkid=CheckId(id);
                new AlertDialog.Builder(CreatGroupChat.this).setTitle("Confirm").setMessage("Are you true")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkid=CheckId(id);
                                CheckCreat();
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    private void getData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
    }


    private void CheckCreat(){
        if (id.equals("")) Toast.makeText(CreatGroupChat.this,"Ban Can Nhap Thong Tin Day Du!",Toast.LENGTH_LONG).show();
        else if (checkid==1) {Toast.makeText(CreatGroupChat.this,"Nhom Da Ton Tai!",Toast.LENGTH_LONG).show();
            checkid=0;
            temp=0;
        }
        else {
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            String stringDate=dateFormat.format(date);
            Group group=new Group(id,stringDate);
            mFirebaseRef.push().setValue(group);
            new AlertDialog.Builder(CreatGroupChat.this).setTitle("Succeed")
                    .setMessage("Group has been created")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            CreatGroupChat.this.finish();
                        }
                    }).show();

        }

    }
    private int CheckId(String id){
        Query query = mFirebaseRef.orderByChild("id").equalTo(id);
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
                    Toast.makeText(CreatGroupChat.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreatGroupChat.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
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
        CreatGroupChat.this.finish();
    }

}
