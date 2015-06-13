package com.example.beacon;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity{
	private Dialog progressDialog;
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main); 
	    Button signup = (Button)findViewById(R.id.sign_up);
	    Button Login = (Button)findViewById(R.id.Login);
	    Button face = (Button)findViewById(R.id.fb);
	    // Check if there is a currently logged in user
	    // and it's linked to a Facebook account.
	    ParseUser currentUser = ParseUser.getCurrentUser();
	    if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
	      // Go to the user info activity
	      showUserDetailsActivity();
	    }
  /* try{
	    PackageInfo info = getPackageManager().getPackageInfo(
	            "com.example.beacon", //your unique package name here
	            PackageManager.GET_SIGNATURES);
	    for (Signature signature : info.signatures) {
	        MessageDigest md = MessageDigest.getInstance("SHA");
	        md.update(signature.toByteArray());
	        Log.e("keyhash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));// this line  gives your keyhash
	        }
	} catch (NameNotFoundException e) {

	} catch (NoSuchAlgorithmException e) {

	}*/
	    
	    signup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    startActivity(new Intent(MainActivity.this,SignUp.class));
			}
		});
	    
	    Login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,Login.class));
			}
		});    
	    
	    face.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			  progressDialog = ProgressDialog.show(MainActivity.this, "", "Logging in . . .",true);
				    List<String> permissions = Arrays.asList("public_profile", "email");
				    ParseFacebookUtils.logIn(permissions, MainActivity.this, new LogInCallback(){
				        @Override
				        public void done(ParseUser user, ParseException err) {
				          progressDialog.dismiss();
				          if (user == null) {
				            Log.d(Application.APPTAG, "Uh oh. The user cancelled the Facebook login.");
				          } else if (user.isNew()) {
				            Log.d(Application.APPTAG, "User signed up and logged in through Facebook!");
				            Session session = ParseFacebookUtils.getSession();
				            if (session != null && session.isOpened()) {
				                makeMeRequest();
				            }
				          } else {
				            Log.d(Application.APPTAG, "User logged in through Facebook!");
				            Session session = ParseFacebookUtils.getSession();
				            if (session != null && session.isOpened()) {
				                makeMeRequest();
				            }
				          }
				        }
				      });
			}
	    });
	  }
	
	private void makeMeRequest() {		
		// TODO Auto-generated method stub
		 Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
		            new Request.GraphUserCallback() {           
			 @Override
		                public void onCompleted(GraphUser user, Response response) {
		                    if (user != null) { 
		                    	// Create a JSON object to hold the profile info
		                        JSONObject userProfile = new JSONObject();
		                        try {                   
		                            // Populate the JSON object 
		                            userProfile.put("facebookId", user.getId());
		                            if (user.getProperty("gender") != null) {
		                                userProfile.put("gender", user.getProperty("gender"));
		                            }
		                            if(user.getBirthday()!=null){
		                            	userProfile.put("Birthday",(String)user.getBirthday());
		                            }
		                            // Now add the data to the UI elements
		                            // ...
		                            // Save the user profile info in a user property
		                            ParseUser currentUser = ParseUser.getCurrentUser();
		                           	currentUser.setUsername((String)user.getFirstName());	
				                   	currentUser.setEmail((String)user.getProperty("email"));
		                            currentUser.put("profile", userProfile);
		                            currentUser.saveInBackground();
		                        } catch (JSONException e) {
		                            Log.d(Application.APPTAG,
		                                    "Error parsing returned user data.");
		                        }
		    
		                    } else if (response.getError() != null) {
		                        if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY) || 
		                          (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
		                          Log.d(Application.APPTAG, "The facebook session was invalidated." + response.getError());
		                        } else {
		                          Log.d(Application.APPTAG, 
		                            "Some other error: " + response.getError());
		                        }
		                      }
		                    }
		                  }
		                );
            request.executeAsync();
		    showUserDetailsActivity();
	}
	
	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	  }
	  
	public void showUserDetailsActivity(){
	    // Fetch Facebook user info if the session is active
		startActivity(new Intent(MainActivity.this, Home.class));
	finish();
	}

}
