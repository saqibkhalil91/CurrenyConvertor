package com.currencyconvertor.entities;

import android.content.Context;


public class Currency {
	private String contryname;
	private String currencyvalue;
	
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContryname() {
		return contryname;
	}
	public void setContryname(String contryname) {
		this.contryname = contryname;
	}
	public String getCurrencyvalue() {
		return currencyvalue;
	}
	public void setCurrencyvalue(String currencyvalue) {
		this.currencyvalue = currencyvalue;
	}
	

}
