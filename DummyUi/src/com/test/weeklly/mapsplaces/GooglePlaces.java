package com.test.weeklly.mapsplaces;

import org.apache.http.client.HttpResponseException;

import android.content.Context;
import android.util.Log;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;

@SuppressWarnings("deprecation")
public class GooglePlaces {

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	// Google API Key
	private static final String API_KEY = "AIzaSyCRwd1qYiDsQDpLYkHGVR3p0Uo9zmUtIMw"; // place your API key here

	// Google Places serach url's
	private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
	private static final String PLACES_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
	private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

	private double _latitude;
	private double _longitude;
	private double _radius;
	
	/**
	 * Searching places
	 * @param latitude - latitude of place
	 * @params longitude - longitude of place
	 * @param radius - radius of searchable area
	 * @param types - type of place to search
	 * @return list of places
	 * */
	public PlacesList search(double latitude, double longitude, double radius, String types,String textsearch,boolean nearby)
			throws Exception {

		this._latitude = latitude;
		this._longitude = longitude;
		this._radius = radius;
		HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
		HttpRequest request = null;
		try {

			
			if(nearby)
			{
			if(textsearch.equals(""))
				{
			 request= httpRequestFactory
					.buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
			request.getUrl().put("key", API_KEY);
			request.getUrl().put("sensor", "false");
			
			if(types != null)
				request.getUrl().put("types", types);
				}
				else
				{
				try{
					textsearch=textsearch.replace(" ", "+");
				   }
				catch(NullPointerException e)
					{
					e.printStackTrace();
					}
				
					request =httpRequestFactory
					.buildGetRequest(new GenericUrl("https://maps.googleapis.com/maps/api/place/textsearch/json?query="+textsearch+"&sensor=true&key="+API_KEY));
			 
				}
			
				request.getUrl().put("location", _latitude + "," + _longitude);
				request.getUrl().put("radius", _radius); // in meters
				
			}
			else
			{
				if(textsearch.equals(""))
				{
			 request= httpRequestFactory
					.buildGetRequest(new GenericUrl("https://maps.googleapis.com/maps/api/place/textsearch/json?"));
			request.getUrl().put("key", API_KEY);
			request.getUrl().put("sensor", "false");
			
			if(types != null)
				request.getUrl().put("query", types);
				}
				else
				{
				try{
					textsearch=textsearch.replace(" ", "+");
				   }
				catch(NullPointerException e)
					{
					e.printStackTrace();
					}
				
					request =httpRequestFactory
					.buildGetRequest(new GenericUrl("https://maps.googleapis.com/maps/api/place/textsearch/json?query="+textsearch+"&sensor=true&key="+API_KEY));
			 
				}
			}
			PlacesList list = request.execute().parseAs(PlacesList.class);
		
			// Check log cat for places response status
			Log.d("Places Status", "" + list.status);
			request=null;
			return list;

		} catch (HttpResponseException e) {
			Log.e("Error:", e.getMessage());
			return null;
		}

	}

	/**
	 * Searching single place full details
	 * @param refrence - reference id of place
	 * 				   - which you will get in search api request
	 * */
	public PlaceDetails getPlaceDetails(String reference) throws Exception {
		try {

			HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
			HttpRequest request = httpRequestFactory
					.buildGetRequest(new GenericUrl(PLACES_DETAILS_URL));
			request.getUrl().put("key", API_KEY);
			request.getUrl().put("reference", reference);
			request.getUrl().put("sensor", "false"); 
			PlaceDetails place = request.execute().parseAs(PlaceDetails.class);
			
			return place;

		} catch (HttpResponseException e) {
			Log.e("Error in Perform Details", e.getMessage());
			throw e;
		}
	}

	/**
	 * Creating http request Factory
	 * */
	public static HttpRequestFactory createRequestFactory(
			final HttpTransport transport) {
		return transport.createRequestFactory(new HttpRequestInitializer() {
			public void initialize(HttpRequest request) {
				GoogleHeaders headers = new GoogleHeaders();
				headers.setApplicationName("AndroidHive-Places-Test");
				request.setHeaders(headers);
				JsonHttpParser parser = new JsonHttpParser(new JacksonFactory());
				request.addParser(parser);
			}
		});
	}

}
