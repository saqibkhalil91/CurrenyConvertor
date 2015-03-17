package com.tessiting.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
 
public class ConnectionDetector {
 
    private Context _context;
 
    public ConnectionDetector(Context context){
        this._context = context;
    }
 
    /**
     * Checking for all possible internet providers
     * **/
    public boolean isConnectingToInternet(){
    	ConnectivityManager cm =
		    (ConnectivityManager) this._context.getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo netInfo = cm.getActiveNetworkInfo();
		  if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		   return true;
		  }
		  return false;
		 }
}
