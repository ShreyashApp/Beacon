package com.example.beacon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Warning extends Activity{
	private ParseGeoPoint geopoint;
@Override
protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	Intent intent = getIntent();
    Location location = intent.getParcelableExtra(Application.INTENT_EXTRA_LOCATION);
    geopoint = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
    String text = "Help Me";

    // Set up a progress dialog
    final ProgressDialog dialog = new ProgressDialog(Warning.this);
    dialog.setMessage(getString(R.string.progress_post));
    dialog.show();

    // Create a post.
    Warnings post1 = new Warnings();

    // Set the location to the current user's location
    post1.setLocation(geopoint);
    post1.setText(text);
    post1.setUser(ParseUser.getCurrentUser());
    ParseACL acl = new ParseACL();

    // Give public read access
    acl.setPublicReadAccess(true);
    post1.setACL(acl);

    // Save the post
    post1.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        dialog.dismiss();
        finish();
      }
    });
    }
}

