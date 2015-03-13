package com.weatherdetector.utilities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;


import android.widget.Toast;

import com.weatherdetector.Entities.Temperature;


public class CityTemperatureAsyncTask extends AsyncTask<String, Void, Void> {

	ArrayList<Temperature> model_info;
	
	TemperatureInterface mylistner;
	private String response;
	private Context context;
	private String url;
	
	private String woeid;
	private JSONObject jobj;
	private ProgressDialog mProgressDialog;
	Temperature weatherTemperature;
	public CityTemperatureAsyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setTitle("Connecting");
		mProgressDialog.setMessage("Please wait ...");
		mProgressDialog.show();
	}

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub

		url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+params[0]+"%2Cnull%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
		
		ServiceHandler sh = new ServiceHandler();
		try {
			String jsonStr = sh.makeServiceCall(
					url,
					ServiceHandler.GET);
			response = jsonStr;
	
		
			
			if (response != null && response != "") {
				
				jobj = new JSONObject(response);
				JSONObject query =jobj.getJSONObject("query");
				JSONObject results = query.getJSONObject("results");
				JSONObject channel =results.getJSONObject("channel");
				JSONObject item =channel.getJSONObject("item");
				JSONObject condition =item.getJSONObject("condition");
				JSONObject wind =channel.getJSONObject("wind");
				JSONObject atmosphere =channel.getJSONObject("atmosphere");
				
				String weatherseen = condition.getString("text");
				String visibility=atmosphere.getString("visibility");
				String humidity=atmosphere.getString("humidity");
				String windSpeed=wind.getString("speed");
				String temp = condition.getString("temp");
				
				Log.d("weatherseen",weatherseen);
				Log.d("temp",temp);
				Log.d("windSpeed",windSpeed);
				Log.d("humidity",humidity);
				Log.d("visibility",visibility);
				weatherTemperature = new Temperature();
				weatherTemperature.setAtmoshphere(weatherseen);
				weatherTemperature.setHumidity(humidity);
				weatherTemperature.setTemperature(temp);
				weatherTemperature.setVisibility(visibility);
				weatherTemperature.setWindSpeed(windSpeed);
			} else {
				Log.e("ServiceHandler status",
						"Couldn't get any data from the url");
				
			}

		

		} 
			catch (Exception e) 
			{
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		mProgressDialog.dismiss();

		try {
			
			this.mylistner.setList(weatherTemperature);
			 

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(context, "No Result found please type city Name", Toast.LENGTH_LONG).show();
		}
	
	}
	public void setApiResulListener(TemperatureInterface listner){
		this.mylistner=listner;
		

	}

	
}
