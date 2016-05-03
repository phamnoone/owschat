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
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.task12.R;

import java.util.Random;

/**
 * Created by USER on 6/5/2015.
 */
public class GuessNumberGame extends ActionBarActivity {
    private String FIREBASE_URL;
    private String from;
    Button bntOK;
    EditText txtNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_number_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = GuessNumberGame.this.getSupportActionBar();
        bntOK=(Button)findViewById(R.id.bntOKGuessNumberGame);
        txtNumber=(EditText)findViewById(R.id.txtGuessNumberGame);
        actionBar.setTitle("GUESS NUMBER GAME");
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
        bntOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=rand(1,3);
                String j=i+"";
                if (txtNumber.getText().toString().equals(j)) Toast.makeText(GuessNumberGame.this,"TRUE",Toast.LENGTH_LONG).show();
                else Toast.makeText(GuessNumberGame.this,"FALSE",Toast.LENGTH_LONG).show();
            }
        });

    }

    public static int rand(int min, int max) {
        try {
            Random rn = new Random();
            int range = max - min + 1;
            int randomNum = min + rn.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
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
        GuessNumberGame.this.finish();
    }
}
