package com.currencyconvertor.BroadcastReceiver;

import java.util.HashMap;
import java.util.Iterator;

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
  private HashMap<Handler, Integer> mHandlers = new HashMap<Handler, Integer>();
	@Override
	public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
		    status = NetworkUtil.getConnectivityStatus(context);
		    MainActivity obj = new MainActivity();
		    obj.internetLableBar(status);
		   /*  Iterator<Handler> it = mHandlers.keySet().iterator();
			while (it.hasNext()) {
				Handler target = it.next();
				Message message = Message.obtain(target, mHandlers.get(target));
				Log.d("check", "not getting");
				target.sendMessage(message);
			}*/
			
		   
	}
	
	public void registerHandler(Handler target,int what) {
		//mHandlers.put(target, what);
		mHandlers.put(target, what);

	}
	public void unregisterHandler(Handler target) {
		mHandlers.remove(target);
	}

	public void unregisterALLHandlers() {
		mHandlers.clear();
	}

	
	
	
	 
}
