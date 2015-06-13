package com.example.beacon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.beacon.MapActivity.ErrorDialogFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class Listvew extends Activity implements LocationListener,GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{
	  /*
	   * Define a request code to send to Google Play services This code is returned in
	   * Activity.onActivityResult
	   */
	  private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	  /*
	   * Constants for location update parameters
	   */
	  // Milliseconds per second
	  private static final int MILLISECONDS_PER_SECOND = 1000;

	  // The update interval
	  private static final int UPDATE_INTERVAL_IN_SECONDS = 5;

	  // A fast interval ceiling
	  private static final int FAST_CEILING_IN_SECONDS = 1;

	  // Update interval in milliseconds
	  private static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
	      * UPDATE_INTERVAL_IN_SECONDS;

	  // A fast ceiling of update intervals, used when the app is visible
	  private static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
	      * FAST_CEILING_IN_SECONDS;

	  // Maximum results returned from a Parse query
	  private static final int MAX_POST_SEARCH_RESULTS = 20;

	  private Location lastLocation;
	  private Location currentLocation;
	  // A request to connect to Location Services
	  private LocationRequest locationRequest;

	  // Stores the current instantiation of the location client in this object
	  private LocationClient locationClient;
	private ParseQueryAdapter<Post> postsQueryAdapter;
	private ParseQueryAdapter<Warnings> warningQueryAdapter;
	protected Location Loc;
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.list_view);
	 // Create a new global location parameters object
	    locationRequest = LocationRequest.create();

	    // Set the update interval
	    locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

	    // Use high accuracy
	    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	    // Set the interval ceiling to one minute
	    locationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);

	    // Create a new location client, using the enclosing class to handle callbacks.
	    locationClient = new LocationClient(this, this, this);
	    // Set up a customized query
	    ParseQueryAdapter.QueryFactory<Post> factory =
	        new ParseQueryAdapter.QueryFactory<Post>() {
	          public ParseQuery<Post> create() {
	            ParseQuery<Post> query = Post.getQuery();
	            query.include("user");
	            query.orderByDescending("createdAt");
	            query.setLimit(MAX_POST_SEARCH_RESULTS);
	            return query;
	          }
	        };
        
	    // Set up the query adapter
		    postsQueryAdapter = new ParseQueryAdapter<Post>(this, factory) {
			      @Override
			      public View getItemView(Post post, View view, ViewGroup parent) {
			    	  Log.e("hey","sup");
			    	  if (view == null) {
			          view = View.inflate(getContext(), R.layout.postitem, null);
			        }
			        TextView contentView = (TextView) view.findViewById(R.id.content_view);
			        TextView usernameView = (TextView) view.findViewById(R.id.username_view);
			        contentView.setText(post.getText());
			        usernameView.setText(post.getUser().getUsername().toString());
			        return view;
			      }
			    };
			    postsQueryAdapter.setTextKey("text");

	    // Disable automatic loading when the adapter is attached to a view.
	    postsQueryAdapter.setAutoload(false);

	    // Disable pagination, we'll manage the query limit ourselves
	    postsQueryAdapter.setPaginationEnabled(false);

	      
// Attach the query adapter to the view
ListView postsListView = (ListView) findViewById(R.id.listVoew);
postsListView.setAdapter(postsQueryAdapter);
	    
	    ParseQueryAdapter.QueryFactory<Warnings> factory1 =
		        new ParseQueryAdapter.QueryFactory<Warnings>() {
		          public ParseQuery<Warnings> create() {
		            ParseQuery<Warnings> query = Warnings.getQuery();
		            query.include("user");
		            query.orderByDescending("createdAt");
		            query.setLimit(MAX_POST_SEARCH_RESULTS);
		            return query;
		          }
		        };
		        
		        warningQueryAdapter = new ParseQueryAdapter<Warnings>(this, factory1){
		        	@Override
		        	public View getItemView(Warnings post,View view1,ViewGroup parent){
				    	  Log.e("hey","sup");
				    	  if (view1 == null) {
				          view1 = View.inflate(getContext(), R.layout.postitem, null);
				        }
				        TextView contentView = (TextView) view1.findViewById(R.id.content_view);
				        TextView usernameView = (TextView) view1.findViewById(R.id.username_view);
				        contentView.setText(post.getText());
				        usernameView.setText(post.getUser().getUsername().toString());
				        return view1;
		        	}
		        	
		        };

					    warningQueryAdapter.setTextKey("text");

					    // Disable automatic loading when the adapter is attached to a view.
					    warningQueryAdapter.setAutoload(false);

					    // Disable pagination, we'll manage the query limit ourselves
					    warningQueryAdapter.setPaginationEnabled(false);
					    ListView postsListView1 = (ListView) findViewById(R.id.listVaw);
	    postsListView1.setAdapter(warningQueryAdapter);
	    postsListView.setOnItemClickListener(new OnItemClickListener() {
	    	  public void onItemClick(AdapterView< ?> parent, View view, int position, long id) {
	    	    final Post item = postsQueryAdapter.getItem(position);
	    	     double lat = item.getLocation().getLatitude();
	    	     double lng = item.getLocation().getLongitude();
	    	     Loc.setLatitude(lat);
	    	     Loc.setLongitude(lng);
	    	    Intent intent = new Intent(Listvew.this,MapActivity.class);
	    	    intent.putExtra(Application.INTENT_EXTRA_LOCATION,Loc);
	    	    intent.putExtra((String) Application.Call, item.getObjectId());
	    	    intent.putExtra(Application.flag, true);
	    	    startActivity(intent);
	    	    finish();
	    	  }
	    	});
	    			doListQuery();
	}
	@Override
	public void onResume(){
		super.onResume();
		doListQuery();
	}
	  private void doListQuery() {
			Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;
		    // If location info is available, load the data
		    if (myLoc != null) {	        
		      // Refreshes the list view with new data based
		      // usually on updated location data.
		      warningQueryAdapter.loadObjects();
		      postsQueryAdapter.loadObjects();
		    }
		  }
	  private ParseGeoPoint geoPointFromLocation(Location loc) {
		    return new ParseGeoPoint(loc.getLatitude(), loc.getLongitude());
		  }
	  @Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
		    currentLocation = location;
		    if (lastLocation != null
		        && geoPointFromLocation(location)
		        .distanceInKilometersTo(geoPointFromLocation(lastLocation)) < 0.01) {
		      // If the location hasn't changed by more than 10 meters, ignore it.
		      return;
		    }
		    // Update map radius indicator
		    doListQuery();
		}
	  @Override
	  public void onStop() {
	    // If the client is connected
	    if (locationClient.isConnected()) {
	      stopPeriodicUpdates();
	    }

	    // After disconnect() is called, the client is considered "dead".
	    locationClient.disconnect();

	    super.onStop();
	  }

	  /*
	   * Called when the Activity is restarted, even before it becomes visible.
	   */
	  @Override
	  public void onStart() {
	    super.onStart();

	    // Connect to the location services client
	    locationClient.connect();
	  }
	  /*
	   * Verify that Google Play services is available before making a request.
	   * 
	   * @return true if Google Play services is available, otherwise false
	   */
	  private boolean servicesConnected() {
	    // Check that Google Play services is available
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

	    // If Google Play services is available
	    if (ConnectionResult.SUCCESS == resultCode) {
	      if (Application.APPDEBUG) {
	        // In debug mode, log the status
	        Log.d(Application.APPTAG, "Google play services available");
	      }
	      // Continue
	      return true;
	      // Google Play services was not available for some reason
	    } else {
	      // Display an error dialog
	      Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
	      if (dialog != null) {
	        ErrorDialogFragment errorFragment = new ErrorDialogFragment();
	        errorFragment.setDialog(dialog);
	      }
	      return false;
	    }
	  }

	  /*
	   * Called by Location Services when the request to connect the client finishes successfully. At
	   * this point, you can request the current location or start periodic updates
	   */
	  public void onConnected(Bundle bundle) {
	    if (Application.APPDEBUG) {
	      Log.d("Connected to location services", Application.APPTAG);
	    }
	    currentLocation = getLocation();
	    startPeriodicUpdates();
	  }

	  /*
	   * Called by Location Services if the connection to the location client drops because of an error.
	   */
	  public void onDisconnected() {
	    if (Application.APPDEBUG) {
	      Log.d("Disconnected from location services", Application.APPTAG);
	    }
	  }

	  /*
	   * Called by Location Services if the attempt to Location Services fails.
	   */
	  public void onConnectionFailed(ConnectionResult connectionResult) {
	    // Google Play services can resolve some errors it detects. If the error has a resolution, try
	    // sending an Intent to start a Google Play services activity that can resolve error.
	    if (connectionResult.hasResolution()) {
	      try {

	        // Start an Activity that tries to resolve the error
	        connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

	      } catch (IntentSender.SendIntentException e) {

	        if (Application.APPDEBUG) {
	          // Thrown if Google Play services canceled the original PendingIntent
	          Log.d(Application.APPTAG, "An error occurred when connecting to location services.", e);
	        }
	      }
	    } else {
	      // If no resolution is available, display a dialog to the user with the error.
	      showErrorDialog(connectionResult.getErrorCode());
	    }
	  }
	  /*
	   * In response to a request to start updates, send a request to Location Services
	   */
	  private void startPeriodicUpdates() {
	    locationClient.requestLocationUpdates(locationRequest, this);
	  }

	  /*
	   * In response to a request to stop updates, send a request to Location Services
	   */
	  private void stopPeriodicUpdates() {
	    locationClient.removeLocationUpdates(this);
	  }

	  /*
	   * Get the current location
	   */
	  private Location getLocation() {
	    // If Google Play Services is available
	    if (servicesConnected()) {
	      // Get the current location
	      return locationClient.getLastLocation();
	    } else {
	      return null;
	    }
	  }
	  private void showErrorDialog(int errorCode) {
		    // Get the error dialog from Google Play services
		    Dialog errorDialog =
		        GooglePlayServicesUtil.getErrorDialog(errorCode, this,
		            CONNECTION_FAILURE_RESOLUTION_REQUEST);

		    // If Google Play services can provide an error dialog
		    if (errorDialog != null) {

		      // Create a new DialogFragment in which to show the error dialog
		      ErrorDialogFragment errorFragment = new ErrorDialogFragment();

		      // Set the dialog in the DialogFragment
		      errorFragment.setDialog(errorDialog);
		    }
		  }

		  /*
		   * Define a DialogFragment to display the error dialog generated in showErrorDialog.
		   */
}
