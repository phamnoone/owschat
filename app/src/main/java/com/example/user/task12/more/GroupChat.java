package com.example.user.task12.more;

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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by USER on 6/4/2015.
 */
public class GroupChat extends ActionBarActivity {
    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private String from;
    private ListView listView;
    ArrayList<Group> listData= new ArrayList<Group>();
    GroupChatAdapter itemAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_chat);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = GroupChat.this.getSupportActionBar();
        actionBar.setTitle("GROUP CHAT");
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
        mFirebaseRef = new Firebase(FIREBASE_URL).child("GroupChat");
        listView=(ListView)findViewById(R.id.listGroupChat);
        startLoadData();
        itemAdapter=new GroupChatAdapter(GroupChat.this,R.layout.group,listData);
        listView.setAdapter(itemAdapter);
        setClickList();

    }

    private void setClickList() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group group=listData.get(position);
                Intent intent=new Intent(GroupChat.this,MessagesGroup.class);
                Bundle bundleComt=new Bundle();
                bundleComt.putString("URL",FIREBASE_URL);
                bundleComt.putString("from", from);
                bundleComt.putSerializable("group", group);
                intent.putExtra("DATA", bundleComt);
                startActivity(intent);
            }
        });
    }

    private void startLoadData() {
        Query query = mFirebaseRef.orderByKey();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                Map<String, Object> value1 = (Map<String, Object>) snapshot.getValue();
                Group group=new Group(value1.get("id").toString(),value1.get("date").toString());
                listData.add(group);
                itemAdapter=new GroupChatAdapter(GroupChat.this,R.layout.group,listData);
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
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_add:
                add();
                return true;
            case R.id.action_close:
                GroupChat.this.finish();
                return true;
            default:return super.onOptionsItemSelected(item);
        }


    }
    private void add(){
        Intent intentAddDiery=new Intent(GroupChat.this,CreatGroupChat.class);
        Bundle bundle=new Bundle();
        bundle.putString("URL",FIREBASE_URL);
        bundle.putString("from",from);
        intentAddDiery.putExtra("DATA",bundle);
        startActivity(intentAddDiery);

    }
}
