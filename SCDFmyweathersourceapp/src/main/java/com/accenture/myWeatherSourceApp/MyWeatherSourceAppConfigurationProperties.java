package com.accenture.myWeatherSourceApp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("myWeatherSourceApp")
public class MyWeatherSourceAppConfigurationProperties {
	
	 public String city;

	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }
	    
	    public String time;

	    public String getTime() {
	        return time;
	    }

	    public void setTime(String time) {
	        this.time = time;
	    }

}
