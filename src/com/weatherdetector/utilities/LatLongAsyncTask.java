package com.weatherdetector.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LatLongAsyncTask extends AsyncTask<String, Void, Void> {

	private ProgressDialog mProgressDialog;
	private Context context;
	private String url;
	private String cityName;
	private String response;
	private JSONObject jobj;
	CoordinateInterface mylistner;
	
	public LatLongAsyncTask(Context context) {
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
		
		

	
		
		ServiceHandler sh = new ServiceHandler();
		try {
			url="http://maps.googleapis.com/maps/api/geocode/json?latlng="+params[0]+","+params[1]+"&sensor=true";
			String jsonStr = sh.makeServiceCall(
					url,
					ServiceHandler.GET);
			response = jsonStr;
		
					
					if (response != null && response != "")
					{
						jobj = new JSONObject(response);
						JSONArray results = jobj.getJSONArray("results");
						
						JSONObject index_address_components = results.getJSONObject(0);
						JSONArray address_components= index_address_components.getJSONArray("address_components");
				
						for(int i=0;i<address_components.length();i++)
						{
							JSONObject index_type =address_components.getJSONObject(i);
							JSONArray type=index_type.getJSONArray("types");
							String loc = type.get(0).toString();
							//Log.d("loc", loc);
							if(loc.equals("locality"))
							{
								cityName=address_components.getJSONObject(i).getString("long_name");
								//Log.d("cityName", cityName);
							}
							
						}
					

					} 
					else 
					{
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
			

			
			this.mylistner.setCityName(cityName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}
	public void setApiResulNameListener(CoordinateInterface listner){
		this.mylistner=listner;
		

	}

}
