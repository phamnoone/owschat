package com.example.user.task12.chat;

/**
 * Created by USER on 5/16/2015.
 */
public class Message {
    private String from;
    private String to;
    private String mess;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Message() {
    }

    Message(String from, String to,String mess) {
        this.from = from;
        this.to = to;
        this.mess=mess;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {

        return to;
    }

    public String getMess() {

        return mess;
    }
}
