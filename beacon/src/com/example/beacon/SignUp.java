package com.example.beacon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends Activity{

	  // UI references.
	  private EditText usernameEditText;
	  private EditText passwordEditText;
	  private EditText passwordAgainEditText;
	  private EditText genderedittext;
	  private EditText emailedittext;
	  private EditText birthdayedittext;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.activity_signup);

	    // Set up the signup form.
	    usernameEditText = (EditText) findViewById(R.id.username_edit_text);
	    genderedittext = (EditText) findViewById(R.id.sign_gender);
	    emailedittext = (EditText)findViewById(R.id.sign_email);
	    birthdayedittext = (EditText)findViewById(R.id.sign_birthday);
	    passwordEditText = (EditText) findViewById(R.id.password_edit_text);
	    passwordAgainEditText = (EditText) findViewById(R.id.password_again_edit_text);
	    passwordAgainEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	      @Override
	      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	        if (actionId == R.id.edittext_action_signup ||
	            actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
	          signup();
	          return true;
	        }
	        return false;
	      }
	    });

	    // Set up the submit button click handler
	    Button mActionButton = (Button) findViewById(R.id.action_button);
	    mActionButton.setOnClickListener(new View.OnClickListener() {
	      public void onClick(View view) {
	        signup();
	      }
	    });
	  }

	  private void signup() {
	    String username = usernameEditText.getText().toString().trim();
	    String password = passwordEditText.getText().toString().trim();
	    String passwordAgain = passwordAgainEditText.getText().toString().trim();
	    String gender = genderedittext.getText().toString().trim();
	    String email = emailedittext.getText().toString().trim();
	    String Birthday = birthdayedittext.getText().toString().trim();
	    
	    // Validate the sign up data
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
	    if (!password.equals(passwordAgain)) {
	      if (validationError) {
	        validationErrorMessage.append(getString(R.string.error_join));
	      }
	      validationError = true;
	      validationErrorMessage.append(getString(R.string.error_mismatched_passwords));
	    }
	    validationErrorMessage.append(getString(R.string.error_end));

	    // If there is a validation error, display the error
	    if (validationError) {
	      Toast.makeText(SignUp.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
	          .show();
	      return;
	    }

	    // Set up a progress dialog
	    final ProgressDialog dialog = new ProgressDialog(SignUp.this);
	    dialog.setMessage(getString(R.string.progress_signup));
	    dialog.show();

		  User user = new User();
		  user.setUser(username);
		  ParseACL acl = new ParseACL();
		  // Give public read access
		  acl.setPublicReadAccess(true);
		  user.setACL(acl);
		  user.saveInBackground();
	    // Set up a new Parse user
        ParseUser currentUser = new ParseUser();
	    currentUser.setUsername(username);
	    currentUser.setPassword(password);
	    currentUser.setEmail(email);
	    JSONObject userProfile = new JSONObject();
        try {                   
            // Populate the JSON object 
            if (gender != null) {
                userProfile.put("gender", gender);
            }
            if(Birthday!=null){
            	userProfile.put("Birthday",Birthday);
            }
            // Now add the data to the UI elements
            // ...
            // Save the user profile info in a user property
            currentUser.put("profile", userProfile);
            currentUser.saveInBackground();

        } catch (JSONException e) {
            Log.d(Application.APPTAG,
                    "Error parsing returned user data.");
        }


	    // Call the Parse signup method
	    currentUser.signUpInBackground(new SignUpCallback() {
	      @Override
	      public void done(ParseException e) {
	        dialog.dismiss();
	        if (e != null) {
	          // Show the error message
	          Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
	        } else {
	          // Start an intent for the home activity
	          Intent intent = new Intent(SignUp.this, Home.class);
	          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
	          startActivity(intent);
	        }
	      }
	    });
	  }

}
