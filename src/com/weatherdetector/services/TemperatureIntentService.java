package com.weatherdetector.services;

import java.util.ArrayList;

import com.weatherdetector.Entities.Temperature;
import com.weatherdetector.activities.PakistanCitiesTemperature.MyBroadcastReceiver;
import com.weatherdetector.utilities.CityTemperatureAsyncTask;
import com.weatherdetector.utilities.ServiceHandler;
import com.weatherdetector.utilities.TempJsonParsing;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class TemperatureIntentService extends IntentService{
	
	

	
	private ArrayList<Temperature> weatherinfo;
	public static final String ACTION_MyIntentService = "IntentService";
	private Temperature weatherTemperatures;
	public TemperatureIntentService() {
		super("TempListIntentService");
		// TODO Auto-generated constructor stub
		 
		
	}
	/*Handler handler = new Handler();
	Runnable runnable = new Runnable() {
	        public void run() {
	            sendBroadcast();
	        }
	};*/

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		showTemperatureDetail();
		
		//runnable.run();
	}
	
	private void showTemperatureDetail()
	{
		
		weatherinfo = new ArrayList<Temperature>();
		for(int i=0;i<5;i++)
		{ 		
				if(i==0)
				{
					fillobj("Lahore");
				}
				else if(i==1)
				{
					fillobj("Islamabad");
				}
				else if(i==2)
				{
					fillobj("Karachi");
				}
				else if(i==3)
				{
					fillobj("Peshawar");
				}
				else if(i==4)
				{
					fillobj("Queta");
				}
			
		}
		
		
		  
	}	
	private void fillobj(String city)
	{
		ServiceHandler sh;
		String jsonStr;
		String	response;
			try{
					String url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+city+"%2Cnull%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
					//https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22+params[0]+%2Cnull%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys
					 sh = new ServiceHandler();
					jsonStr = sh.makeServiceCall(url,ServiceHandler.GET);
						response = jsonStr;
					TempJsonParsing tjp = new TempJsonParsing(response,city);
					tjp.getTempObj();
					weatherTemperatures=tjp.getWeatherTemperature();
					//weatherinfo.add(weatherTemperatures);

				
				
				Intent broadcastIntent = new Intent();
	           broadcastIntent.setAction(ACTION_MyIntentService );
	           broadcastIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	           broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
	           Bundle mBundle = new Bundle(); 
	           
	                   mBundle.putSerializable("SER_KEY",weatherTemperatures);
	                   broadcastIntent.putExtras(mBundle);
	          
	           
	       
	           sendBroadcast(broadcastIntent);
			}
			catch(Exception e)
			{
				e.getMessage();
			}
		 
		
	}
	
	
	
	/*public void sendBroadcast()
	{
		// If your value is still null, run the runnable again
		if (weatherinfo.equals("null"))
		{
		    handler.postDelayed(runnable, 1000);
		    Log.d("temp of lahore","in ok");
		
		}
		else{
		//Here where it is expected to send the broadcast
			 Intent broadcastIntent = new Intent();
	           broadcastIntent.setAction(ACTION_MyIntentService );
	           broadcastIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	           broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
	           Bundle mBundle = new Bundle(); 
	           
	                   mBundle.putSerializable("SER_KEY",weatherTemperatures);
	                   broadcastIntent.putExtras(mBundle);
	          
	           
	          
	           sendBroadcast(broadcastIntent);
		          
			}
	}	*/

}
