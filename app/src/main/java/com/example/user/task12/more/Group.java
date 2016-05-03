package com.example.user.task12.more;

import java.io.Serializable;

/**
 * Created by USER on 6/4/2015.
 */
public class Group implements Serializable {
    String id;
    String date;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Group() {
    }
    Group(String id,String date) {
        this.id=id;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public String getId() {

        return id;
    }
}
