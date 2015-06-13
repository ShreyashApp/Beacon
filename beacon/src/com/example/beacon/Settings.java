package com.example.beacon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Activity that displays the settings screen.
 */
public class Settings extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    Parse.initialize(this, "l6T0bp7JueCIrJw6wCoUFhUOm7krsztfwfaiRcYS",
            "KgoZKKN0nrhwKhw1NGrHLLbVO8WTQqWnmJm3kBAy");
    // Set up the log out button click handler
	Button logout = (Button)findViewById(R.id.logout);
	Button listview = (Button)findViewById(R.id.List);
	listview.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Settings.this,Listvew.class);
			startActivity(intent);
			finish();
		}
	});
    logout.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        // Call the Parse log out method
        ParseUser.logOut();
        // Start and intent for the dispatch activity
        Intent intent = new Intent(Settings.this, DispatchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
      }
    });
  }
}