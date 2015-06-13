package com.example.beacon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class Home extends ActionBarActivity{
Post posts;
	private Button MApp;

	private TextView userEmailView;
	private TextView userGenderView;
	private TextView userNameView;
	private ProfilePictureView userProfilePictureView;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		MApp = (Button)findViewById(R.id.button1);
	    userProfilePictureView = (ProfilePictureView) findViewById(R.id.userProfilePicture);
	    userNameView = (TextView) findViewById(R.id.userName);
	    userGenderView = (TextView) findViewById(R.id.userGender);
	    userEmailView = (TextView) findViewById(R.id.userEmail);
	    updateViewsWithProfileInfo();

    MApp.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(Home.this,MapActivity.class));
			finish();
		}
    });
	}
	
	private void updateViewsWithProfileInfo() {
	   ParseUser user = ParseUser.getCurrentUser();
	   userEmailView.setText(user.getEmail());
	   userNameView.setText(user.getUsername().toString());
		if (user.has("profile")) {
	      JSONObject userProfile = user.getJSONObject("profile");
	      try {
	        
	        if (userProfile.has("facebookId")) {
	          userProfilePictureView.setProfileId(userProfile.getString("facebookId"));
	        } else {
	          // Show the default, blank user profile picture
	          userProfilePictureView.setProfileId(null);
	        }
	        if (userProfile.has("gender")) {
	          userGenderView.setText(userProfile.getString("gender"));
	        } else {
	          userGenderView.setText("");
	        }
	        } catch (JSONException e) {
	        Log.d(Application.APPTAG, "Error parsing saved user data.");
	      }
	    }
	  }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings).setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
              startActivity(new Intent(Home.this, Edit.class));
              return true;
            }
          });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
