package com.example.user.task12.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.task12.R;
import com.example.user.task12.contact.Contact;
import com.example.user.task12.diary.Diary;
import com.example.user.task12.more.More;
import com.example.user.task12.users.ListUsers;
import com.example.user.task12.users.Users;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class Home extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private String FIREBASE_URL;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private String from;
    private ListView listView;
    private TextView myName;
    ArrayList<Users> listData=new ArrayList<Users>();
    HomeUserAdapter itemAdapter=null;
    ImageButton bntCamera,bntCamera2;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFB567")));
        getData();
        bntCamera=(ImageButton)findViewById(R.id.bntCamera);
        bntCamera2=(ImageButton)findViewById(R.id.bntCamera2);
        bntCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });
        bntCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });
        myName=(TextView)findViewById(R.id.home_my_name);
        myName.setText(from);
        listView=(ListView)findViewById(R.id.listUsersHome);
        mFirebaseRef = new Firebase(FIREBASE_URL).child("Users");
        startLoadData();
        itemAdapter=new HomeUserAdapter(Home.this,R.layout.user_home,listData);
        listView.setAdapter(itemAdapter);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private void camera() {
        new AlertDialog.Builder(Home.this).setTitle("Sorry").setMessage("Coming soon.")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    private void startLoadData() {
        Query query = mFirebaseRef.orderByKey();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                Map<String, Object> value1 = (Map<String, Object>) snapshot.getValue();
                Users users=new Users(value1.get("name").toString(),value1.get("pass").toString(),
                        value1.get("email").toString());
                if (!users.getName().toString().equals(from))listData.add(users);
                itemAdapter=new HomeUserAdapter(Home.this,R.layout.user_home,listData);
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

    private void getData(){
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        FIREBASE_URL=bundle.getString("URL");
        from=bundle.getString("from");
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1: mTitle="OWS CHAT";
                break;
            case 2:
                Intent intentContact=new Intent(Home.this,Contact.class);
                Bundle bundleContact=new Bundle();
                bundleContact.putString("from",from);
                bundleContact.putString("URL",FIREBASE_URL);
                intentContact.putExtra("DATA", bundleContact);
                startActivity(intentContact);
                break;
            case 3:
                new AlertDialog.Builder(Home.this).setTitle("SORRY").setMessage("Coming soon.")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                break;
            case 4:
                Intent intentMessages=new Intent(Home.this, ListUsers.class);
                Bundle bundle=new Bundle();
                bundle.putString("from",from);
                bundle.putString("URL",FIREBASE_URL);
                intentMessages.putExtra("DATA",bundle);
                startActivity(intentMessages);
                break;
            case 5:
                new AlertDialog.Builder(Home.this).setTitle("SORRY").setMessage("Coming soon.")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                break;
            case 6:
                Intent intentTimeLine=new Intent(Home.this, Diary.class);
                Bundle bundleTimeLine=new Bundle();
                bundleTimeLine.putString("from",from);
                bundleTimeLine.putString("URL",FIREBASE_URL);
                intentTimeLine.putExtra("DATA",bundleTimeLine);
                startActivity(intentTimeLine);
                break;
            case 7:
                Intent intentMore=new Intent(Home.this, More.class);
                Bundle bundleMore=new Bundle();
                bundleMore.putString("from",from);
                bundleMore.putString("URL",FIREBASE_URL);
                intentMore.putExtra("DATA",bundleMore);
                startActivity(intentMore);
                break;
            case 8:
                Home.this.finish();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Home) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}

