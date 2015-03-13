package com.weatherdetector.activities;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.weatherdetector.Entities.CitiesTemperatureInfo;
import com.weatherdetector.Entities.Temperature;
import com.weatherdetector.services.TemperatureIntentService;
import com.weatherdetector.utilities.TemperatureInterface;
import com.weatherdetector.utilities.CityTemperatureAsyncTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PakistanCitiesTemperature extends Activity implements
		OnClickListener {

	
	private MyBroadcastReceiver myBroadcastReceiver;
	private CityTemperatureAsyncTask api;
	private Temperature sendweather;
	private ProgressBar spinnerisl, spinnerlhr, spinnerkch, spinnerpsh,
	spinnerqut;
	private ArrayList<Temperature> citiesweather;
	private TextView tempisl, templhr, tempkch, tempqueta, temppishawar;
	private ImageView weatherImageislambad, weatherImagelahore,
	weatherImagekarachi, weatherImagepishawar, weatherImagequetta;
	private int flaglhr = 0;
	private int flagisl = 0;
	private int flagpsh = 0;
	private int flagqut = 0;
	private int flagkch = 0;
	private ImageView togClhr, togFlhr, togCkch, togFkch, togCisl, togFisl,
	togCqut, togFqut, togCpsh, togFpsh;
	private float teminFisl, timeCisl, teminFlhr, timeClhr, teminFkch,
	timeCkch, teminFpsh, timeCpsh, teminFqut, timeCqut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_big_cities_temp);
		initComponents();
		sendweather = new Temperature();
		showTemperatureDetail();

	}

	private void initComponents() {
		tempisl = (TextView) findViewById(R.id.tvtempIslambad);
		templhr = (TextView) findViewById(R.id.tvtempLahore);
		tempkch = (TextView) findViewById(R.id.tvtempKarachi);
		temppishawar = (TextView) findViewById(R.id.tvtemppishawar);
		tempqueta = (TextView) findViewById(R.id.tvtempqueta);
		weatherImageislambad = (ImageView) findViewById(R.id.imgislamabad);
		weatherImagekarachi = (ImageView) findViewById(R.id.imgKarachi);
		weatherImagelahore = (ImageView) findViewById(R.id.imgisLahore);
		weatherImagepishawar = (ImageView) findViewById(R.id.imgpishawar);
		weatherImagequetta = (ImageView) findViewById(R.id.imgqueta);
		citiesweather = new ArrayList<Temperature>();
		togCisl = (ImageView) findViewById(R.id.degCisl);
		togCisl.setOnClickListener(this);

		togFisl = (ImageView) findViewById(R.id.degFisl);
		togFisl.setOnClickListener(this);

		togClhr = (ImageView) findViewById(R.id.degClhr);
		togClhr.setOnClickListener(this);

		togFlhr = (ImageView) findViewById(R.id.degFlhr);
		togFlhr.setOnClickListener(this);

		togCkch = (ImageView) findViewById(R.id.degCkch);
		togCkch.setOnClickListener(this);

		togFkch = (ImageView) findViewById(R.id.degFkch);
		togFkch.setOnClickListener(this);

		togCpsh = (ImageView) findViewById(R.id.degCpsh);
		togCpsh.setOnClickListener(this);

		togFpsh = (ImageView) findViewById(R.id.degFpsh);
		togFpsh.setOnClickListener(this);

		togCqut = (ImageView) findViewById(R.id.degCqut);
		togCqut.setOnClickListener(this);

		togFqut = (ImageView) findViewById(R.id.degFqut);
		togFqut.setOnClickListener(this);
		spinerloader();

	}

	private void spinerloader() {
		spinnerisl = (ProgressBar) findViewById(R.id.progressBar1);
		spinnerisl.setVisibility(View.VISIBLE);

		spinnerlhr = (ProgressBar) findViewById(R.id.progressBar2);
		spinnerlhr.setVisibility(View.VISIBLE);

		spinnerkch = (ProgressBar) findViewById(R.id.progressBar3);
		spinnerkch.setVisibility(View.VISIBLE);

		spinnerpsh = (ProgressBar) findViewById(R.id.progressBar5);
		spinnerpsh.setVisibility(View.VISIBLE);

		spinnerqut = (ProgressBar) findViewById(R.id.progressBar4);
		spinnerqut.setVisibility(View.VISIBLE);
	}

	private void showTemperatureDetail() {
		// showTemperatureDetail();
		Intent msgIntent = new Intent(this, TemperatureIntentService.class);
		startService(msgIntent);
		myBroadcastReceiver = new MyBroadcastReceiver();

		// register BroadcastReceiver
		IntentFilter intentFilter = new IntentFilter(
				TemperatureIntentService.ACTION_MyIntentService);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(myBroadcastReceiver, intentFilter);

	}

	private void setImageResource(String weathrCondition, int a) {

		switch (a) {
		case 2:
			if (weathrCondition.contains("Cloudy")) {

				weatherImageislambad.setImageResource(R.drawable.cloudy);
			} else if (weathrCondition.contains("Partial")) {
				weatherImageislambad.setImageResource(R.drawable.partialcloudy);
			} else if (weathrCondition.contains("Haze")
					|| weathrCondition.contains("Smoke")) {
				weatherImageislambad.setImageResource(R.drawable.haze);
			} else if (weathrCondition.contains("Rainy")) {
				weatherImageislambad.setImageResource(R.drawable.raining);
			} else if (weathrCondition.contains("Sunny")) {
				weatherImageislambad.setImageResource(R.drawable.suny);
			} else if (weathrCondition.contains("Snow")) {
				weatherImageislambad.setImageResource(R.drawable.snow);
			} else if (weathrCondition.contains("Thunder")) {
				weatherImageislambad.setImageResource(R.drawable.thunder);
			} else {
				weatherImageislambad.setImageResource(R.drawable.clear);
			}
			break;
		case 1:
			if (weathrCondition.contains("Cloudy")) {

				weatherImagelahore.setImageResource(R.drawable.cloudy);
			} else if (weathrCondition.contains("Partial")) {
				weatherImagelahore.setImageResource(R.drawable.partialcloudy);
			} else if (weathrCondition.contains("Haze")
					|| weathrCondition.contains("Smoke")) {
				weatherImagelahore.setImageResource(R.drawable.haze);
			} else if (weathrCondition.contains("Rainy")) {
				weatherImagelahore.setImageResource(R.drawable.raining);
			} else if (weathrCondition.contains("Sunny")) {
				weatherImagelahore.setImageResource(R.drawable.suny);
			} else if (weathrCondition.contains("Snow")) {
				weatherImagelahore.setImageResource(R.drawable.snow);
			} else if (weathrCondition.contains("Thunder")) {
				weatherImagelahore.setImageResource(R.drawable.thunder);
			} else {
				weatherImagelahore.setImageResource(R.drawable.clear);
			}
			break;
		case 3:
			if (weathrCondition.contains("Cloudy")) {

				weatherImagekarachi.setImageResource(R.drawable.cloudy);
			} else if (weathrCondition.contains("Partial")) {
				weatherImagekarachi.setImageResource(R.drawable.partialcloudy);
			} else if (weathrCondition.contains("Haze")
					|| weathrCondition.contains("Smoke")) {
				weatherImagekarachi.setImageResource(R.drawable.haze);
			} else if (weathrCondition.contains("Rainy")) {
				weatherImagekarachi.setImageResource(R.drawable.raining);
			} else if (weathrCondition.contains("Sunny")) {
				weatherImagekarachi.setImageResource(R.drawable.suny);
			} else if (weathrCondition.contains("Snow")) {
				weatherImagekarachi.setImageResource(R.drawable.snow);
			} else if (weathrCondition.contains("Thunder")) {
				weatherImagekarachi.setImageResource(R.drawable.thunder);
			} else {
				weatherImagekarachi.setImageResource(R.drawable.clear);
			}

			break;
		case 4:
			if (weathrCondition.contains("Cloudy")) {

				weatherImagepishawar.setImageResource(R.drawable.cloudy);
			} else if (weathrCondition.contains("Partial")) {
				weatherImagepishawar.setImageResource(R.drawable.partialcloudy);
			} else if (weathrCondition.contains("Haze")
					|| weathrCondition.contains("Smoke")) {
				weatherImagepishawar.setImageResource(R.drawable.haze);
			} else if (weathrCondition.contains("Rainy")) {
				weatherImagepishawar.setImageResource(R.drawable.raining);
			} else if (weathrCondition.contains("Sunny")) {
				weatherImagepishawar.setImageResource(R.drawable.suny);
			} else if (weathrCondition.contains("Snow")) {
				weatherImagepishawar.setImageResource(R.drawable.snow);
			} else if (weathrCondition.contains("Thunder")) {
				weatherImagepishawar.setImageResource(R.drawable.thunder);
			} else {
				weatherImagepishawar.setImageResource(R.drawable.clear);
			}
			break;

		case 5:
			if (weathrCondition.contains("Cloudy")) {

				weatherImagequetta.setImageResource(R.drawable.cloudy);
			} else if (weathrCondition.contains("Partial")) {
				weatherImagequetta.setImageResource(R.drawable.partialcloudy);
			} else if (weathrCondition.contains("Haze")
					|| weathrCondition.contains("Smoke")
					|| weathrCondition.contains("dust")) {
				weatherImagequetta.setImageResource(R.drawable.haze);
			} else if (weathrCondition.contains("Rainy")) {
				weatherImagequetta.setImageResource(R.drawable.raining);
			} else if (weathrCondition.contains("Sunny")) {
				weatherImagequetta.setImageResource(R.drawable.suny);
			} else if (weathrCondition.contains("Snow")) {
				weatherImagequetta.setImageResource(R.drawable.snow);
			} else if (weathrCondition.contains("Thunder")) {
				weatherImagequetta.setImageResource(R.drawable.thunder);
			} else {
				weatherImagequetta.setImageResource(R.drawable.clear);
			}
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.big_cities_temp, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.refresh:
			showTemperatureDetail();
			// search action
			return true;
		case R.id.back:
			Intent intent = new Intent(this, TemperatureMap.class);
			Bundle mBundle = new Bundle();

			mBundle.putSerializable("keycities", citiesweather);
			intent.putExtras(mBundle);
			startActivity(intent);
			// unregisterReceiver(myBroadcastReceiver);
			break;

		default:
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		// unregisterReceiver(myBroadcastReceiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private float convertFahrenheitToCelcius(float fahrenheit) {
		return ((fahrenheit - 32) * 5 / 9);
	}

	private void updateStatus(Temperature weatherinfo) {
		try {
			sendweather = weatherinfo;
			if (weatherinfo.getCity().equals("Lahore")) {
				spinnerlhr.setVisibility(View.GONE);
				teminFlhr = Float.parseFloat(weatherinfo.getTemperature());
				if (flaglhr == 0) {
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String temF = numberFormat.format(teminFlhr);
					templhr.setText("" + temF + "F");
				} else if (flaglhr == 1) {
					timeClhr = convertFahrenheitToCelcius(teminFlhr);
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String tempC = numberFormat.format(timeClhr);
					templhr.setText("" + tempC + "C");
				}

				String weathercondition = weatherinfo.getAtmoshphere();
				setImageResource(weathercondition, 1);
				citiesweather.add(sendweather);
			} else if (weatherinfo.getCity().equals("Islamabad")) {
				spinnerisl.setVisibility(View.GONE);

				teminFisl = Float.parseFloat(weatherinfo.getTemperature());
				if (flagisl == 0) {
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String temF = numberFormat.format(teminFisl);
					tempisl.setText("" + temF + "F");
				} else if (flagisl == 1) {
					timeCisl = convertFahrenheitToCelcius(teminFisl);
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String tempC = numberFormat.format(timeCisl);
					tempisl.setText("" + tempC + "C");
				}
				String weatherconditions = weatherinfo.getAtmoshphere();
				setImageResource(weatherconditions, 2);
				citiesweather.add(sendweather);

			} else if (weatherinfo.getCity().equals("Karachi")) {
				spinnerkch.setVisibility(View.GONE);
				teminFkch = Float.parseFloat(weatherinfo.getTemperature());
				if (flagkch == 0) {
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String temF = numberFormat.format(teminFkch);
					tempkch.setText("" + temF + "F");
				} else if (flagkch == 1) {
					timeCkch = convertFahrenheitToCelcius(teminFkch);
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String tempC = numberFormat.format(timeCkch);
					tempkch.setText("" + tempC + "C");
				}

				String weatherconditionk = weatherinfo.getAtmoshphere();
				setImageResource(weatherconditionk, 3);
				citiesweather.add(sendweather);
				Log.d("atmoshphere", weatherinfo.getAtmoshphere());
			} else if (weatherinfo.getCity().equals("Peshawar")) {
				spinnerpsh.setVisibility(View.GONE);

				teminFpsh = Float.parseFloat(weatherinfo.getTemperature());
				if (flagpsh == 0) {
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String temF = numberFormat.format(teminFpsh);
					temppishawar.setText("" + temF + "F");
				} else if (flagpsh == 1) {
					timeCpsh = convertFahrenheitToCelcius(teminFpsh);
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String tempC = numberFormat.format(timeCpsh);
					temppishawar.setText("" + tempC + "C");
				}

				String weatherconditionp = weatherinfo.getAtmoshphere();
				setImageResource(weatherconditionp, 4);
				citiesweather.add(sendweather);
			} else if (weatherinfo.getCity().equals("Queta")) {
				spinnerqut.setVisibility(View.GONE);
				teminFqut = Float.parseFloat(weatherinfo.getTemperature());
				if (flagqut == 0) {
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String temF = numberFormat.format(teminFqut);
					tempqueta.setText("" + temF + "F");
				} else if (flagqut == 1) {
					timeCqut = convertFahrenheitToCelcius(teminFqut);
					DecimalFormat numberFormat = new DecimalFormat("#.0");

					String tempC = numberFormat.format(timeCqut);
					tempqueta.setText("" + tempC + "C");
				}

				String weatherconditionq = weatherinfo.getAtmoshphere();

				setImageResource(weatherconditionq, 5);
				citiesweather.add(sendweather);
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	public class MyBroadcastReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP = "broadcast";
		String temp;
		String atm;
		String itemp;
		Temperature weather;

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			/*
			 * String result =
			 * intent.getStringExtra(TempListIntentService.EXTRA_KEY_OUT);
			 * textResult.setText(result);
			 */
			// weatherinfo = new ArrayList<Temperature>();
			if (intent.getAction().equals(
					TemperatureIntentService.ACTION_MyIntentService)) {

				// Bundle bundleObject = intent.getExtras();

				// Get ArrayList Bundle
				weather = (Temperature) intent.getSerializableExtra("SER_KEY");

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						updateStatus(weather);

					}
				});
			}

		}

	}

	private float convertCelciusToFahrenheit(float celsius) {
		return ((celsius * 9) / 5) + 32;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int key = v.getId();
		switch (key) {
		case R.id.degCisl:
			flagisl = 1;

			timeCisl = convertFahrenheitToCelcius(teminFisl);
			DecimalFormat numberFormat = new DecimalFormat("#.0");

			String temCC = numberFormat.format(timeCisl);
			// String tempp=timeC+"";
			tempisl.setText("" + temCC + "C");

			break;

		case R.id.degFisl:
			if (flagisl == 1) {
				DecimalFormat numberFormatt = new DecimalFormat("#.0");

				double temppp = convertCelciusToFahrenheit(timeCisl);
				String temFF = numberFormatt.format(temppp);
				tempisl.setText("" + temFF + "F");
			}
			break;

		case R.id.degClhr:
			flaglhr = 1;

			timeClhr = convertFahrenheitToCelcius(teminFlhr);
			DecimalFormat numberFormatlhr = new DecimalFormat("#.0");

			String temCClhr = numberFormatlhr.format(timeClhr);
			// String tempp=timeC+"";
			templhr.setText("" + temCClhr + "C");

			break;

		case R.id.degFlhr:
			if (flaglhr == 1) {
				DecimalFormat numberFormattlhr = new DecimalFormat("#.0");

				double temppplhr = convertCelciusToFahrenheit(timeClhr);
				String temFFlhr = numberFormattlhr.format(temppplhr);
				templhr.setText("" + temFFlhr + "F");
			}
			break;

		case R.id.degCkch:
			flagkch = 1;

			timeCkch = convertFahrenheitToCelcius(teminFkch);
			DecimalFormat numberFormatkch = new DecimalFormat("#.0");

			String temCCkch = numberFormatkch.format(timeCkch);
			// String tempp=timeC+"";
			tempkch.setText("" + temCCkch + "C");
			break;

		case R.id.degFkch:
			if (flagkch == 1) {
				DecimalFormat numberFormattkch = new DecimalFormat("#.0");

				double tempppkch = convertCelciusToFahrenheit(timeCkch);
				String temFFkch = numberFormattkch.format(tempppkch);
				tempkch.setText("" + temFFkch + "F");
			}
			break;

		case R.id.degCpsh:
			flagpsh = 1;

			timeCpsh = convertFahrenheitToCelcius(teminFpsh);
			DecimalFormat numberFormatpsh = new DecimalFormat("#.0");

			String temCCpsh = numberFormatpsh.format(timeCpsh);
			// String tempp=timeC+"";
			temppishawar.setText("" + temCCpsh + "C");
			break;

		case R.id.degFpsh:
			if (flagpsh == 1) {
				DecimalFormat numberFormattpsh = new DecimalFormat("#.0");

				double temppppsh = convertCelciusToFahrenheit(timeCpsh);
				String temFFpsh = numberFormattpsh.format(temppppsh);
				temppishawar.setText("" + temFFpsh + "F");
			}
			break;

		case R.id.degCqut:
			flagqut = 1;

			timeCqut = convertFahrenheitToCelcius(teminFqut);
			DecimalFormat numberFormatqut = new DecimalFormat("#.0");

			String temCCqut = numberFormatqut.format(timeCqut);
			// String tempp=timeC+"";
			tempqueta.setText("" + temCCqut + "C");
			break;

		case R.id.degFqut:
			if (flagqut == 1) {
				DecimalFormat numberFormattqut = new DecimalFormat("#.0");

				double tempppqut = convertCelciusToFahrenheit(timeCqut);
				String temFFqut = numberFormattqut.format(tempppqut);
				tempqueta.setText("" + temFFqut + "F");
			}
			break;

		}

	}

}
