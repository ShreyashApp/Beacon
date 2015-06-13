package com.example.beacon;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Data model for a post.
 */
@ParseClassName("Post")
public class Post extends ParseObject {
  
	public String getText() {
    return getString("text");
  }

  public void setText(String Sam) {
    put("text", Sam);
  }
  
  public ParseUser getUser() {
    return getParseUser("user");
  }

  public void setUser(ParseUser value) {
    put("user", value);
  }
  

  public ParseGeoPoint getLocation() {
    return getParseGeoPoint("location");
  }

  public void setLocation(ParseGeoPoint value) {
    put("location", value);
  }

  public static ParseQuery<Post> getQuery() {
    return ParseQuery.getQuery(Post.class);
  }

}
