package com.example.user.task12.diary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.task12.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by USER on 5/26/2015.
 */
public class DiaryComments extends ActionBarActivity{

    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private String from;
    private ListView listView;
    ArrayList<comt> listData= new ArrayList<comt>();
    AdapterComt itemAdapter=null;
    private TextView txtDiary,txtDiaryFrom,txtDiaryDate;
    private Diar diar;
    private String id;
    private ImageButton bntsend1;
    private EditText txtComnt;
    private String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_comments);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        android.support.v7.app.ActionBar actionBar = DiaryComments.this.getSupportActionBar();
        actionBar.setTitle("COMMENTS");
        getButton();
        getData();
        mFirebaseRef = new Firebase(FIREBASE_URL).child("comments");
        startLoadData();
        itemAdapter=new AdapterComt(DiaryComments.this,R.layout.comment_layout,listData);
        listView.setAdapter(itemAdapter);
        clickButton();
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(itemAdapter);
        //to scroll the list view to bottom on data change
        itemAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                itemAdapter.notifyDataSetChanged();
                listView.setSelection(itemAdapter.getCount() - 1);
            }
        });

    }

    private void clickButton() {
        txtComnt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });
        bntsend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void getButton() {
        txtDiary=(TextView)findViewById(R.id.txtDiary);
        txtDiaryFrom=(TextView)findViewById(R.id.txtDiaryFrom);
        txtDiaryDate=(TextView)findViewById(R.id.txtDiaryDate);
        listView=(ListView)findViewById(R.id.listcomments);
        bntsend1=(ImageButton)findViewById(R.id.bntsend1);
        txtComnt=(EditText)findViewById(R.id.txtcomt);
    }

    private void sendMessage() {
        String input = txtComnt.getText().toString();
        if(!input.equals("")) {
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            stringDate=dateFormat.format(date);
            comt com=new comt(id,from,input,stringDate);
            mFirebaseRef.push().setValue(com);
            txtComnt.setText("");
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    private void getData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
        diar= (Diar)bundle.getSerializable("diary");
        txtDiaryFrom.setText(diar.getFrom().toString());
        txtDiary.setText(diar.getDiar().toString());
        txtDiaryDate.setText(diar.getDate().toString());
        id=diar.getId();
    }


    private void startLoadData() {
        Query query = mFirebaseRef.orderByChild("id").equalTo(id);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                Map<String, Object> value1 = (Map<String, Object>) snapshot.getValue();
                comt comt = new comt(id, value1.get("from").toString(), value1.get("cmt").toString(), value1.get("date").toString());
                listData.add(comt);
                itemAdapter = new AdapterComt(DiaryComments.this, R.layout.comment_layout, listData);
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
                close();
                return true;
            default:return super.onOptionsItemSelected(item);
        }


    }
    private void close(){
        DiaryComments.this.finish();
    }
}
