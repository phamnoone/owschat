package com.example.user.task12.diary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.user.task12.R;
import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by USER on 5/24/2015.
 */
public class AddDiary extends ActionBarActivity {

    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private String from;
    EditText txtAddDiary;
    private String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_diary);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = AddDiary.this.getSupportActionBar();
        actionBar.setTitle("ADD DIARY");
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
        mFirebaseRef = new Firebase(FIREBASE_URL).child("diary");
        txtAddDiary=(EditText)findViewById(R.id.txtAddDiary);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_diary, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_post:
                post();
                return true;
            case R.id.action_cancle:
                close();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
    private void post(){
        Random r=new Random();
        String id=""+r.nextInt(100000);
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        stringDate=dateFormat.format(date);
        Diar diar=new Diar(id,from,txtAddDiary.getText().toString(),stringDate);
        mFirebaseRef.push().setValue(diar);
        AddDiary.this.finish();
    }
    private void close(){
        AddDiary.this.finish();
    }
}
