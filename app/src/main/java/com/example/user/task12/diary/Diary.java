package com.example.user.task12.diary;

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
 * Created by USER on 5/24/2015.
 */
public class Diary extends ActionBarActivity {
    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private String from;
    ListView listView;
    ArrayList<Diar> listData= new ArrayList<Diar>();
    DiaryAdapter itemAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diars);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = Diary.this.getSupportActionBar();
        actionBar.setTitle("DIARY");
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
        mFirebaseRef = new Firebase(FIREBASE_URL).child("diary");
        listView=(ListView)findViewById(R.id.listDiaries);
        startLoadData();
        itemAdapter=new DiaryAdapter(Diary.this,R.layout.diar,listData);
        listView.setAdapter(itemAdapter);
        setClickList();

    }

    private void setClickList() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Diar diar=listData.get(position);
                Intent intentComt=new Intent(Diary.this,DiaryComments.class);
                Bundle bundleComt=new Bundle();
                bundleComt.putString("URL",FIREBASE_URL);
                bundleComt.putString("from",from);
                bundleComt.putSerializable("diary", diar);
                intentComt.putExtra("DATA",bundleComt);
                startActivity(intentComt);
            }
        });
    }

    private void startLoadData() {
        Query query = mFirebaseRef.orderByKey();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                Map<String, Object> value1 = (Map<String, Object>) snapshot.getValue();
                Diar diar=new Diar(value1.get("id").toString(),value1.get("from").toString(),
                        value1.get("diar").toString(),value1.get("date").toString());
                listData.add(diar);
                itemAdapter=new DiaryAdapter(Diary.this,R.layout.diar,listData);
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
                Diary.this.finish();
                return true;
            default:return super.onOptionsItemSelected(item);
        }


    }
    private void add(){
        Intent intentAddDiery=new Intent(Diary.this,AddDiary.class);
        Bundle bundle=new Bundle();
        bundle.putString("URL",FIREBASE_URL);
        bundle.putString("from",from);
        intentAddDiery.putExtra("DATA",bundle);
        startActivity(intentAddDiery);

    }
}
