package com.example.beacon;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Activity which starts an intent for either the logged in (MainActivity) or logged out
 * (SignUpOrLoginActivity) activity.
 */
public class DispatchActivity extends Activity {

  public DispatchActivity() {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ParseObject.registerSubclass(Warnings.class);
    ParseObject.registerSubclass(Post.class);
    ParseObject.registerSubclass(Message.class);
    ParseObject.registerSubclass(User.class);
    Parse.initialize(this," ");
    ParseFacebookUtils.initialize("");
    // Check if there is current user info
    if (ParseUser.getCurrentUser() != null) {
      // Start an intent for the logged in activity
      startActivity(new Intent(this, StartFragment.class));
      finish();
    } else {
      // Start and intent for the logged out activity
      startActivity(new Intent(this, MainActivity.class));
      finish();
    }
  }

}