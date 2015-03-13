package com.weatherdetector.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	
	 private Pattern pattern;
	  private Matcher matcher;

	  private static final String cityName_PATTERN = "^((?!-.,)[A-Za-z](?<!-.,)\\.)";

	  public Validation(){
		  pattern = Pattern.compile(cityName_PATTERN);
	  }

	  /**
	   * Validate cityname with regular expression
	   * @param username username for validation
	   * @return true valid username, false invalid username
	   */
	  public boolean validate(final String cityName_PATTERN){

		  matcher = pattern.matcher(cityName_PATTERN);
		  return matcher.matches();

	  }

}
