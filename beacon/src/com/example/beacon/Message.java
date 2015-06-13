package com.example.beacon;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Shreyash Appikatla on 11-06-2015.
 */
@ParseClassName("Message")

public class Message extends ParseObject {

    String title,userId;

    public Message(){

    }

    public Message(String title, String userId) {
        this.title = title;
        this.userId = userId;
    }

    public String getUserId() {
        return getString("userId");
    }

    public String getBody() {
        return getString("body");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setBody(String body) {
        put("body", body);
    }

}