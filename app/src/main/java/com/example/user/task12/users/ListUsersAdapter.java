package com.example.user.task12.users;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.user.task12.R;
import com.firebase.client.Query;

/**
 * Created by USER on 5/16/2015.
 */
public class ListUsersAdapter extends FirebaseListAdapter<Users> {


    private String id;
    private String from;
    private String to;


    public ListUsersAdapter (Query ref, Activity activity, int layout,String from,String to,String id) {
        super(ref, Users.class, layout, activity);
        this.id= id;
        this.from=from;
        this.to=to;
    }



    @Override
    protected void populateView(View view, Users users) {
        String name = users.getName();
        TextView txtid = (TextView) view.findViewById(R.id.id);
        txtid.setText(name);
    }
}
