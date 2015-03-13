package com.weatherdetector.activities;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.weatherdetector.Entities.Temperature;

import android.app.Activity;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TemperatureMap extends Activity {
	private LatLng Lahore = new LatLng(31.5497 ,74.3436);
	private LatLng islamabad = new LatLng(33.7167 ,73.0667);
	private LatLng karachi = new LatLng(24.8600 ,67.0100);
	private LatLng pishawar = new LatLng(34.0167 ,71.5833);
	private LatLng quetta = new LatLng(30.1833 ,67.0000);
	   private GoogleMap googleMap;// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);;
	   Temperature weather;
	   SharedPreferences sharedPreferences;
	   int locationCount = 0;
	   private ArrayList<Temperature> citiesTemperature;
	   ArrayList< Marker> markers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_map);
		markPoint();

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.temp_map, menu);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private void markPoint()
	{
		Bundle bundleObject = getIntent().getExtras(); 
		citiesTemperature = (ArrayList<Temperature>) bundleObject.getSerializable("keycities");
		 try { 
	            if (googleMap == null) {
	               googleMap = ((MapFragment) getFragmentManager().
	               findFragmentById(R.id.map)).getMap();
	            }
	         googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	         markers =new ArrayList<Marker>();
	         Marker Lhr = googleMap.addMarker(new MarkerOptions().
	    	         position(Lahore).title("Lahore").snippet(citiesTemperature.get(0).getTemperature()+"F"));
	    	         markers.add(Lhr);
	    	         Marker isl = googleMap.addMarker(new MarkerOptions().
	    	    	         position(islamabad).title(citiesTemperature.get(4).getTemperature()).snippet("56F"));
	    	         markers.add(isl);
	    	         Marker kch = googleMap.addMarker(new MarkerOptions().
	    	    	         position(karachi).title("Karachi").snippet(citiesTemperature.get(2).getTemperature()+"F"));
	    	         markers.add(kch);
	    	         Marker psh = googleMap.addMarker(new MarkerOptions().
	    	    	         position(pishawar).title("Pishawar").snippet(citiesTemperature.get(3).getTemperature()+"F"));
	    	         markers.add(psh);
	    	         Marker qut = googleMap.addMarker(new MarkerOptions().
	    	    	         position(quetta).title("Quetta").snippet(citiesTemperature.get(4).getTemperature()+"F"));
	    	         markers.add(qut);
	    	         
	    	         LatLngBounds.Builder builder = new LatLngBounds.Builder();
	    	         for (Marker marker : markers) {
	    	             builder.include(marker.getPosition());
	    	         }
	    	         LatLngBounds bounds = builder.build();
	    	      //    int padding = 0; offset from edges of the map in pixels
	    	         CameraUpdate cu = CameraUpdateFactory.newLatLngBounds( bounds, 925,925,15);
	    	         googleMap.moveCamera(cu);
	    	         googleMap.animateCamera(cu);
	       
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
