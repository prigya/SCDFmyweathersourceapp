package com.accenture.myWeatherSourceApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.cloud.stream.annotation.EnableBinding;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;



@EnableBinding(Source.class)
@EnableConfigurationProperties({MyWeatherSourceAppConfigurationProperties.class})
public class MyWeatherSourceAppConfiguration {
	
	@Autowired
	private MyWeatherSourceAppConfigurationProperties properties;
	
	@Bean
	@InboundChannelAdapter(
			  value = Source.OUTPUT, 
			  poller = @Poller(fixedDelay = "3600000", maxMessagesPerPoll = "1")
			)
	
	// HTTP GET request
	public MessageSource<String> sendGet() throws Exception {

		String url = "http://api.openweathermap.org/data/2.5/weather?q="+this.properties.city+"&units=metric&appid=f3f97cd5ed838ace7b1e3751cfa90d4c";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		return () -> MessageBuilder.withPayload(response.toString()).build();

	}
}