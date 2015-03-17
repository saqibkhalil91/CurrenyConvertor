
package com.currencyconvertor.activities;


import com.currencyconvertor.utilities.ConnectionDetector;
import com.currencyconvertor.utilities.CurrencyConvertorAsyncTask;
import com.example.testcurrency.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;

public class MainActivity extends Activity {

	Button hitapi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ConnectionDetector cd = new ConnectionDetector(this);
		boolean checkingConnection =cd.isConnectingToInternet();
		if (checkingConnection==true)	{
			CurrencyConvertorAsyncTask currencyConvertorApi =  new CurrencyConvertorAsyncTask(this);
			
			currencyConvertorApi.execute(null, null, null);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
