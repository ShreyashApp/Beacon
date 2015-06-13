package com.example.beacon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class Login extends Activity {
	  
	  private EditText usernameEditText;
	  private EditText passwordEditText;
	 
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.login); 
	    Parse.initialize(this, "l6T0bp7JueCIrJw6wCoUFhUOm7krsztfwfaiRcYS",
	            "KgoZKKN0nrhwKhw1NGrHLLbVO8WTQqWnmJm3kBAy");
	    
	    Button actionButton = (Button) findViewById(R.id.loginauto);
	    usernameEditText = (EditText) findViewById(R.id.Userlogin);
	    passwordEditText = (EditText) findViewById(R.id.passwordlogin);
	    	    
	    actionButton.setOnClickListener(new View.OnClickListener() {
	      public void onClick(View view) {
	        login();
	      }
	    });	    
	   }

	  private void login() {
	    String username = usernameEditText.getText().toString().trim();
	    String password = passwordEditText.getText().toString().trim();

	    // Validate the log in data
	    boolean validationError = false;
	    StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
	    if (username.length() == 0) {
	      validationError = true;
	      validationErrorMessage.append(getString(R.string.error_blank_username));
	    }
	    if (password.length() == 0) {
	      if (validationError) {
	        validationErrorMessage.append(getString(R.string.error_join));
	      }
	      validationError = true;
	      validationErrorMessage.append(getString(R.string.error_blank_password));
	    }
	    validationErrorMessage.append(getString(R.string.error_end));

	    // If there is a validation error, display the error
	    if (validationError) {
	      Toast.makeText(Login.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
	          .show();
	      return;
	    }

	    // Set up a progress dialog
	    final ProgressDialog dialog = new ProgressDialog(Login.this);
	    dialog.setMessage(getString(R.string.progress_login));
	    dialog.show();
	    // Call the Parse login method
	    ParseUser.logInInBackground(username, password, new LogInCallback() {
	      @Override
	      public void done(ParseUser user, ParseException e) {
	        dialog.dismiss();
	        if (e != null) {
	          // Show the error message
	          Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
	        } else {
	          // Start an intent for the dispatch activity
	          Intent intent = new Intent(Login.this,DispatchActivity.class);
	          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
	          startActivity(intent);
	        }
	      }
	    });
	  }
}
