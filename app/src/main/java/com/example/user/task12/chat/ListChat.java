package com.example.user.task12.chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 5/16/2015.
 */
public class ListChat extends ActionBarActivity{
    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private String from;
    private String to;
    private EditText inputText;
    private ImageButton bntSend,bntWC;
    ListView listChat;
    ChatArrayAdapter adapter=null;
    List<ChatMessage> list=new ArrayList<ChatMessage>();
    ChatMessage message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.list_chat);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent=getIntent();
        Bundle bundle=new Bundle();
        bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
        to=bundle.getString("to");
        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("messages");
        listChat=(ListView)findViewById(R.id.listViewChat);
        adapter=new ChatArrayAdapter(ListChat.this,R.layout.activity_chat_singlemessage,list);
        listChat.setAdapter(adapter);
        inputText = (EditText) findViewById(R.id.txtMess);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });
        bntWC=(ImageButton)findViewById(R.id.bntWCListChat);
        bntWC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ListChat.this).setTitle("SORRY").setMessage("Coming soon.")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        bntSend= (ImageButton) findViewById(R.id.bntSend);
        bntSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        startLoadData();
        listChat.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listChat.setAdapter(adapter);

        //to scroll the list view to bottom on data change
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                adapter.notifyDataSetChanged();
                listChat.setSelection(adapter.getCount() - 1);
            }
        });

    }


    public void startLoadData(){
        Query query = mFirebaseRef.orderByKey();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                Map<String, Object> value1 = (Map<String, Object>) snapshot.getValue();
                if (value1.get("from").equals(from) && value1.get("to").equals(to)) {
                    message = new ChatMessage(false, value1.get("mess").toString());
                    list.add(message);
                    adapter=new ChatArrayAdapter(ListChat.this,R.layout.activity_chat_singlemessage,list);
                    listChat.setAdapter(adapter);
                    adapter.registerDataSetObserver(new DataSetObserver() {
                        @Override
                        public void onChanged() {
                            super.onChanged();
                            listChat.setSelection(adapter.getCount() - 1);
                        }
                    });
                } else if (value1.get("from").equals(to) && value1.get("to").equals(from)) {
                    message = new ChatMessage(true, value1.get("mess").toString());
                    list.add(message);
                    adapter=new ChatArrayAdapter(ListChat.this,R.layout.activity_chat_singlemessage,list);
                    listChat.setAdapter(adapter);
                    adapter.registerDataSetObserver(new DataSetObserver() {
                        @Override
                        public void onChanged() {
                            super.onChanged();
                            listChat.setSelection(adapter.getCount() - 1);
                        }
                    });
                }
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
    public void onStart() {
        super.onStart();

        adapter=new ChatArrayAdapter(ListChat.this,R.layout.activity_chat_singlemessage,list);
        listChat.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listChat.setSelection(adapter.getCount() - 1);
            }
        });
        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    //Toast.makeText(ListChat.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(ListChat.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(ListChat.this).setTitle("CHECK CONNECT").setMessage("No connect")
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


    public void sendMessage(){


        String input = inputText.getText().toString();
        if(!input.equals("")) {
            Message message = new Message( from, to,input);
            mFirebaseRef.push().setValue(message);
            inputText.setText("");
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
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
        ListChat.this.finish();
    }
}
