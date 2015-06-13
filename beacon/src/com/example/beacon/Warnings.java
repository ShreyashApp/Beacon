package com.example.beacon;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Data model for a Warning.
 */
@ParseClassName("Warnings")
public class Warnings extends ParseObject {
	
	public Warnings(){
		
	}
	
  public String getText() {
    return getString("text");
  }

  public void setText(String test) {
    put("text", test);
  }
  
  public ParseUser getUser() {
    return getParseUser("user");
  }

  public void setUser(ParseUser name) {
    put("user", name);
  }
  

  public ParseGeoPoint getLocation() {
    return getParseGeoPoint("location");
  }

  public void setLocation(ParseGeoPoint vadd) {
    put("location", vadd);
  }

  public static ParseQuery<Warnings> getQuery() {
    return ParseQuery.getQuery(Warnings.class);
  }

}
