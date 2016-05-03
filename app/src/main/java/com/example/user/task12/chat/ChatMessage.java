package com.example.user.task12.chat;

/**
 * Created by USER on 5/20/2015.
 */
public class ChatMessage {
    public boolean left;
    public String message;

    @SuppressWarnings("unused")
    public ChatMessage() {
    }
    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
}
