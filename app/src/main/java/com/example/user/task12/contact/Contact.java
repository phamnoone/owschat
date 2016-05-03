package com.example.user.task12.contact;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.user.task12.R;
import com.example.user.task12.users.Users;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by USER on 6/16/2015.
 */
public class Contact extends ActionBarActivity{
    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private String from;
    private ListView listView;
    ArrayList<Users> listData=new ArrayList<Users>();
    ContactAdapter itemAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.con_tact);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        getData();
        listView = (ListView) findViewById(R.id.listViewContact);
        mFirebaseRef = new Firebase(FIREBASE_URL).child("Users");
        startLoadData();
        itemAdapter = new ContactAdapter(Contact.this, R.layout.contact, listData);
        listView.setAdapter(itemAdapter);
    }
    private void getData(){
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
    }
    private void startLoadData() {
        Query query = mFirebaseRef.orderByKey();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                Map<String, Object> value1 = (Map<String, Object>) snapshot.getValue();
                Users users = new Users(value1.get("name").toString(), value1.get("pass").toString(),
                        value1.get("email").toString());
                if (!users.getName().toString().equals(from)) listData.add(users);
                itemAdapter = new ContactAdapter(Contact.this, R.layout.contact, listData);
                listView.setAdapter(itemAdapter);
                itemAdapter.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        listView.setSelection(itemAdapter.getCount() - 1);
                    }
                });

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
                Contact.this.finish();
                return true;
            default:return super.onOptionsItemSelected(item);
        }


    }
}
