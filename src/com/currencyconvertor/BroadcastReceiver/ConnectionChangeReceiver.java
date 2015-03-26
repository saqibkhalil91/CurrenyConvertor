package com.currencyconvertor.BroadcastReceiver;

import java.util.HashMap;

import com.currencyconvertor.activities.MainActivity;
import com.currencyconvertor.utilities.NetworkUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public  class ConnectionChangeReceiver extends BroadcastReceiver{
  private int status;
  HashMap< Handler, Integer> mhash;
  Message msg;
  Handler handler;

	@Override
	public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
		    status = NetworkUtil.getConnectivityStatus(context);
		
		    mhash= new HashMap<Handler, Integer>();
		    Message msg = Message.obtain(); // Creates an new Message instance
			   msg.obj = status+""; // Put the string into Message, into "obj" field.
			 this.handler.sendMessage(msg);
			
		  Log.d("status", status+"");
	}
	
	public void registerHandler(Handler handler, int i) {
		// TODO Auto-generated method stub
			this.handler = handler;
		

	}
	
	
	 
}
