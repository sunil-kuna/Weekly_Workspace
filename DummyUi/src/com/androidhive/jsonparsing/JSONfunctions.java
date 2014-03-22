package com.androidhive.jsonparsing;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import org.apache.commons.httpclient.util.URIUtil;


public class JSONfunctions {

	public static JSONObject getJSONfromURL(String url){
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		

	    try{
	    	
	            HttpClient httpclient = new DefaultHttpClient();
	            Log.d("URL", URIUtil.encodeQuery(url));
	            HttpGet httppost = new HttpGet(URIUtil.encodeQuery(url));

	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();

	    } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	    

	    try{
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                    sb.append(line + "\n");
	            }
	            is.close();
	            result=sb.toString();
	    } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
	    
	    try{
	    	
            jArray = new JSONObject(result);            
	    }catch(JSONException e){
	           
	    }
    
	    return jArray;
	}
}
