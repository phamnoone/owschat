package com.example.user.task12.more;

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

import com.example.user.task12.R;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

/**
 * Created by USER on 5/24/2015.
 */
public class More extends ActionBarActivity{
    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private String from;
    Button bntGame,bntAcc,bntGroupChat;

    private void TakeButton(){
        bntGame=(Button)findViewById(R.id.bntGame);
        bntAcc=(Button)findViewById(R.id.bntAcc);
        bntGroupChat=(Button)findViewById(R.id.bntGroupChat);

    }
    private void ClickBuuton(){
        bntAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAcc=new Intent(More.this,Acc.class);
                Bundle bundle=new Bundle();
                bundle.putString("URL",FIREBASE_URL);
                bundle.putString("from", from);
                intentAcc.putExtra("DATA", bundle);
                startActivity(intentAcc);
            }
        });
        bntGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGroupChat=new Intent(More.this,GroupChat.class);
                Bundle bundle=new Bundle();
                bundle.putString("URL",FIREBASE_URL);
                bundle.putString("from", from);
                intentGroupChat.putExtra("DATA", bundle);
                startActivity(intentGroupChat);
            }
        });
        bntGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenGame=new Intent(More.this,Game.class);
                Bundle bundle=new Bundle();
                bundle.putString("URL",FIREBASE_URL);
                bundle.putString("from", from);
                intenGame.putExtra("DATA", bundle);
                startActivity(intenGame);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = More.this.getSupportActionBar();
        actionBar.setTitle("MORE");
        getData();
        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("Users");
        TakeButton();
        ClickBuuton();

    }

    private void getData(){
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
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
        More.this.finish();
    }
}


