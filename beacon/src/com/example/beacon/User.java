package com.example.beacon;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;

/**
 * Created by Shreyash Appikatla on 11-06-2015.
 */
@ParseClassName("User")
public class User extends ParseObject implements Serializable {

    public User(){

    }

    public String getUser() {
        return getString("user");
    }

    public void setUser(String name) {
        put("user", name);
    }

    public static ParseQuery<User> getQuery() {
        return ParseQuery.getQuery(User.class);
    }

}
