package com.tessiting.utilities;

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




public class CurrencyConvertorAsyncTask extends AsyncTask<String, Void, Void> {


	

	private String response;
	private Context context;
	private String url;
	
	private String woeid;
	private JSONObject jobj;
	private ProgressDialog mProgressDialog;

	public CurrencyConvertorAsyncTask(Context context) {
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

		url="http://www.freecurrencyconverterapi.com/api/v2/convert?q=EUR_DKK,EUR_BGN,EUR_GBP,EUR_HRK,EUR_NOK,EUR_PLN,EUR_SEK,EUR_TRY";
		
		ServiceHandler sh = new ServiceHandler();
		try {
			String jsonStr = sh.makeServiceCall(
					url,
					ServiceHandler.GET);
			response = jsonStr;
	
		Log.d("response", response);
			
			if (response != null && response != "") {
				
				
			
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
			
	
			 

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(context, "No Result found please type city Name", Toast.LENGTH_LONG).show();
		}
	
	}
	

	
}
