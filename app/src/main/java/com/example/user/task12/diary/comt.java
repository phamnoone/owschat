package com.example.user.task12.diary;

/**
 * Created by USER on 5/26/2015.
 */
public class comt {
    private String id;
    private String from;
    private String cmt;
    private String date;
    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private comt() {
    }
    comt(String id,String from, String cmt,String date) {
        this.id=id;
        this.from = from;
        this.cmt = cmt;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public String getCmt() {
        return cmt;
    }

    public String getFrom() {

        return from;
    }

    public String getId() {

        return id;
    }
}
