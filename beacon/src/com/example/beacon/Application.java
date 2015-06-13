package com.example.beacon;


public class Application extends android.app.Application {
  // Debugging switch
  public static final boolean APPDEBUG = false;

  // Debugging tag for the application
  public static final String APPTAG = "Beacon";

  // Used to pass location from MainActivity to PostActivity
  public static final String INTENT_EXTRA_LOCATION = "location";

  private static ConfigHelper configHelper;
  
  public static String flag = "false";
  
  public static Object Call;

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();
    configHelper = new ConfigHelper();
    configHelper.fetchConfigIfNeeded();
  }


  public static ConfigHelper getConfigHelper() {
    return configHelper;
  }

public static float getSearchDistance() {
	// TODO Auto-generated method stub
	return 100f;
}


}