package com.example.user.task12.more;

import java.io.Serializable;

/**
 * Created by USER on 6/4/2015.
 */
public class MessGroup implements Serializable{
    private String id;
    private String from;
    private String mess;
    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private MessGroup() {
    }

    MessGroup(String id, String from,String mess) {
        this.from = from;
        this.id= id;
        this.mess=mess;
    }

    public String getMess() {
        return mess;
    }

    public String getFrom() {

        return from;
    }

    public String getId() {

        return id;
    }
}
