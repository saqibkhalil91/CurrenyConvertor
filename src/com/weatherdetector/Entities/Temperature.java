package com.weatherdetector.Entities;

import java.io.Serializable;

public class Temperature implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private String temperature;
	private String humidity;
	private String Visibility;
	private String windSpeed;
	private String atmoshphere;
	private String city;

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getVisibility() {
		return Visibility;
	}

	public void setVisibility(String visibility) {
		Visibility = visibility;
	}

	public String getAtmoshphere() {
		return atmoshphere;
	}

	public void setAtmoshphere(String atmoshphere) {
		this.atmoshphere = atmoshphere;
	}

}
