package com.weatherdetector.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.weatherdetector.Entities.Temperature;

public class TempJsonParsing {
	private String response;
	private Temperature weatherTemperature;
	private String city;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	private JSONObject jobj;
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Temperature getWeatherTemperature() {
		return weatherTemperature;
	}
	public void setWeatherTemperature(Temperature weatherTemperature) {
		this.weatherTemperature = weatherTemperature;
	}
	public TempJsonParsing(String response,String city)
	{
		this.response = response;
		this.weatherTemperature = new Temperature();
		this.city=city;
	}
	public void getTempObj()
	{
		if (response != null && response != "") 
		{
			
				try {
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
						
						/*Log.d("weatherseen",weatherseen);
						Log.d("temp",temp);
						Log.d("windSpeed",windSpeed);
						Log.d("humidity",humidity);
						Log.d("visibility",visibility);*/
					
						weatherTemperature.setAtmoshphere(weatherseen);
						weatherTemperature.setHumidity(humidity);
						weatherTemperature.setTemperature(temp);
						weatherTemperature.setVisibility(visibility);
						weatherTemperature.setWindSpeed(windSpeed);
						weatherTemperature.setCity(city);
				} 
				catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} 
		else {
			Log.e("ServiceHandler status",
					"Couldn't get any data from the url");
			
		}
	}

}
