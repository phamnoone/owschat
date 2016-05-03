package com.example.user.task12.users;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.user.task12.R;
import com.example.user.task12.chat.ListChat;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by USER on 5/16/2015.
 */
public class ListUsers extends ActionBarActivity{
    private String FIREBASE_URL="https://androichat.firebaseio.com";
    private Firebase mFirebaseRef;
    private String id="hieutt";
    private String pass;
    private ValueEventListener mConnectedListener;
    ListView listView;
    private ListUsersAdapter listUsersAdapter;
    private String from;
    private String to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_users);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = ListUsers.this.getSupportActionBar();
        actionBar.setTitle("MESSAGES");
        getData();
        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("Users");
        listView=(ListView)findViewById(R.id.listUsers);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Users user= (Users) listUsersAdapter.getItem(position);
                to=user.getName();
                Bundle bundle=new Bundle();
                bundle.putString("URL",FIREBASE_URL);
                bundle.putString("from",from);
                bundle.putString("to",to);
                Intent intent=new Intent(ListUsers.this,ListChat.class);
                intent.putExtra("DATA",bundle);
                startActivity(intent);
            }
        });
    }

    public void getData(){
        Intent intent=getIntent();
        Bundle bundle=new Bundle();
        bundle=intent.getBundleExtra("DATA");
        from=bundle.getString("from");
        FIREBASE_URL=bundle.getString("URL");

    }

        @Override
    public void onStart() {
            super.onStart();
            listUsersAdapter=new ListUsersAdapter(mFirebaseRef,this,R.layout.user,from,to,id);
            listView.setAdapter(listUsersAdapter);
            listUsersAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    listView.setSelection(listUsersAdapter.getCount() - 1);
                }
            });
        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    //Toast.makeText(ListUsers.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(ListUsers.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(ListUsers.this).setTitle("CHECK CONNECT").setMessage("No connect")
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
        ListUsers.this.finish();
    }

}
