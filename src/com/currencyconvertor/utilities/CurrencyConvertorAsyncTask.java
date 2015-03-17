package com.currencyconvertor.utilities;

import android.widget.Toast;
import android.util.Log;
import android.os.AsyncTask;
import android.content.ContentValues;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;
import android.app.ProgressDialog;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.currencyconvertor.databases.DbHandler;
import com.currencyconvertor.entities.Currency;

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
	private DbHandler db;
	
	private JSONObject mJSONObject;
	private ProgressDialog mProgressDialog;

	public CurrencyConvertorAsyncTask(Context context) {
		this.context = context;
		db=new DbHandler(context);
		
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
				
						mJSONObject= new JSONObject(response);
						
						JSONObject results =mJSONObject.getJSONObject("results");
						 @SuppressWarnings("unchecked")
						Iterator<String> iter = results.keys();
						 int column_index= 0;
						    while (iter.hasNext())
						    {
							     String key = iter.next();
							     try {
							    	 column_index++;
									      JSONObject aCuurency = results.getJSONObject(key);
					
									      String countryValue= aCuurency.getString("val");
											String countryName = aCuurency.getString("to");
											
											Log.d("countryValue", countryValue);
											Log.d("countryName", countryName);
											Currency mCurrency = new Currency();
											mCurrency.setContryname(countryName);
											mCurrency.setCurrencyvalue(countryValue);
											mCurrency.setId(column_index);
											db.updatedatemydatabase(mCurrency);
											
			
							     	} 
							     	catch (JSONException e)
							     	{
							     		e.getMessage();
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

		
	
	}
	

	
}
