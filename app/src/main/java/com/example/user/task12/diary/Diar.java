package com.example.user.task12.diary;

import java.io.Serializable;

/**
 * Created by USER on 5/24/2015.
 */
public class Diar implements Serializable {
    private String id;
    private String from;
    private String diar;
    private String date;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Diar() {
    }
    Diar(String id,String from, String diar,String date) {
        this.id=id;

        this.from = from;
        this.diar = diar;
        this.date=date;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDiar() {

        return diar;
    }

    public String getFrom() {

        return from;
    }
}
