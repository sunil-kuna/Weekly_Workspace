/*
// Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.weeklly.gplus;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.androidhive.jsonparsing.AndroidJSONParsingActivity;
import com.androidhive.jsonparsing.JSONParser;
import com.androidhive.jsonparsing.JSONfunctions;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.test.weeklly.dummyui.Constants;
import com.test.weeklly.dummyui.InternetAlertDialogManager;
import com.test.weeklly.dummyui.MainActivity;
import com.test.weeklly.gplus.PlusClientFragment.OnSignedInListener;
import com.test.weeklly.mapsplaces.ConnectionDetector;
import com.test.weeklly.R;

/**
 * Example of signing in a user with Google+, and how to make a call to a Google+ API endpoint.
 */
public class SignInActivity extends FragmentActivity
        implements View.OnClickListener, OnSignedInListener {
	
	private static String signup_url ;//= "http://192.168.0.110:8080/WeeklyService/Signup?";
	private static String getinfo_url;// = "http://192.168.0.110:8080/WeeklyService/getInfo?gid=";
	private static String ID = "id";
	private static String GID = "gid";
	private static String USERNAME = "username";
	private static String PHONE_NO = "phone_no";
	private static String ITEM = "item";
	private static String RESP= "response";
	
    public static final int REQUEST_CODE_PLUS_CLIENT_FRAGMENT = 0;
    String g_id,emailid,name,photourl;
     //SharedPreferences app_preferences=PreferenceManager.getDefaultSharedPreferences(SignInActivity.this);
    private PlusClientFragment mSignInFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(this, "activity started", Toast.LENGTH_LONG).show();
      
        setContentView(R.layout.sign_in_activity);
       
        
        InternetAlertDialogManager alert=new InternetAlertDialogManager();
    	ConnectionDetector cd =new ConnectionDetector(SignInActivity.this);
        boolean isInternetPresent = cd.isConnectingToInternet();
		while(!isInternetPresent) {
			// Internet Connection is not present
			alert.showNoConnectionDialog(SignInActivity.this);
			// stop executing code by return
			return;
		}
       
       Constants cons = new Constants();
        signup_url=cons.ip+"WeeklyService/Signup?";
        getinfo_url= cons.ip+"WeeklyService/getInfo?gid=";
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		final SharedPreferences app_preferences=getSharedPreferences("userdata",0);
         g_id = app_preferences.getString("g_id",null);
         emailid=app_preferences.getString("emailid",null);
         name=app_preferences.getString("name",null);
         photourl=app_preferences.getString("photourl",null);
         
      
         
         
         if( g_id==null||emailid==null||name==null||photourl==null)
      ;//  setContentView(R.layout.sign_in_activity);
         else
         {
       
       	 /*Intent i=new Intent(this,com.test.weekly.backend.AndroidJSONParsingActivity.class);
     	startActivity(i);
         
         SignInActivity.this.finish();*/
         }	 

        mSignInFragment =
                PlusClientFragment.getPlusClientFragment(this, MomentUtil.VISIBLE_ACTIVITIES);
        
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        
        }

    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		finish();
	}

	@Override
    public void onClick(View view) {
        switch(view.getId()) {
            
            case R.id.sign_in_button:
                mSignInFragment.signIn(REQUEST_CODE_PLUS_CLIENT_FRAGMENT);
                break;
           
        }
    }
    
    protected void checkUser(String gid){

    	
		// getting JSON string from URL
		JSONObject json = JSONfunctions.getJSONfromURL(getinfo_url+gid);
		
		
					JSONObject item;
					try {
						item = json.getJSONObject(ITEM);
					
						String resp = item.getString(RESP);
					
					
						if(resp.equals("exsits")){
							Intent i=new Intent(this,MainActivity.class);
				        	startActivity(i);
				           SignInActivity.this.finish();
						}else{
							Intent in = new Intent(this,Signup.class);
				        	startActivity(in);
				        	/*Toast.makeText(SignInActivity.this, json.toString() ,
										Toast.LENGTH_SHORT).show();
							 JSONObject signjson = JSONfunctions.getJSONfromURL(signup_url+"gid="+gid+"&username=sourabh&phone_no=9789155342&lati=13.6&longi=77.03");
							 Toast.makeText(SignInActivity.this, signjson.toString() ,
										Toast.LENGTH_SHORT).show();
										*/
						}
			
				
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    }
    
    
    
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {	
        mSignInFragment.handleOnActivityResult(requestCode, responseCode, intent);
    }

    @SuppressWarnings("unused")
	@Override
    public void onSignedIn(PlusClient plusClient) {
        // We can now obtain the signed-in user's profile information.
    	//Toast.makeText(SignInActivity.this,"Signed in", Toast.LENGTH_LONG).show();
    	Person currentPerson = plusClient.getCurrentPerson();
        
        
        
        if (currentPerson != null) {
        	try{
        		SharedPreferences app_preferences=getSharedPreferences("userdata",0);
                
                SharedPreferences.Editor editor = app_preferences.edit();  
                
                editor.putString("g_id",currentPerson.getId());
                editor.putString("emailid",plusClient.getAccountName());
                editor.putString("name",currentPerson.getDisplayName());
            //    editor.putString("photourl",currentPerson.getImage().getUrl());
                editor.commit();
        		checkUser(currentPerson.getId());
        
        	}   
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}

        } else {
           
        }
        
    }
   

       
    

   
}
