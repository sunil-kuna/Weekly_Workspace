package com.test.weeklly.gplus;

import static com.androidhive.pushnotifications.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.androidhive.pushnotifications.CommonUtilities.EXTRA_MESSAGE;
import static com.androidhive.pushnotifications.CommonUtilities.SENDER_ID;

import java.util.Random;

import org.json.JSONObject;

import com.androidhive.jsonparsing.JSONfunctions;
import com.androidhive.pushnotifications.ServerUtilities;
import com.androidhive.pushnotifications.WakeLocker;
import com.google.android.gcm.GCMRegistrar;
import com.test.weeklly.dummyui.Constants;
import com.test.weeklly.dummyui.InternetAlertDialogManager;
import com.test.weeklly.dummyui.MainActivity;
import com.test.weeklly.mapsplaces.ConnectionDetector;
import com.test.weeklly.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity{
	
	Button submitphnm,submitcode;
	EditText phonenum,code;
	Random ran;
	int num;
	private static String signup_url;// = "http://192.168.0.111:8080/WeeklyService/Signup?";
	private static String ID = "id";
	private static String GID = "gid";
	private static String USERNAME = "username";
	private static String PHONE_NO = "phone_no";
	private static String ITEM = "item";
	private static String RESP= "response";
	AsyncTask<Void, Void, Void> mRegisterTask;
	
	Boolean clicked=false;
	String phno;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		InternetAlertDialogManager alert=new InternetAlertDialogManager();
    	ConnectionDetector cd =new ConnectionDetector(Signup.this);
        boolean isInternetPresent = cd.isConnectingToInternet();
		while(!isInternetPresent) {
			// Internet Connection is not present
			alert.showNoConnectionDialog(Signup.this);
			// stop executing code by return
			return;
		}
		Constants cons=new Constants();
		signup_url= cons.ip+"WeeklyService/Signup?";
        submitphnm=(Button)findViewById(R.id.sumbitPhnm);
        submitcode=(Button)findViewById(R.id.submitcode);
        phonenum=(EditText)findViewById(R.id.phone_num);
        code=(EditText)findViewById(R.id.Code);
        ran=new Random();
        num=ran.nextInt(1000000);
        code.setEnabled(false);
	   // submitcode.setAlpha((float)0.5);
	    submitcode.setClickable(false);
        //Toast.makeText(Signup.this,""+num , Toast.LENGTH_LONG).show();  
		  
        submitphnm.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!phonenum.getText().toString().equals("")&&phonenum.getText().toString().length()>=10)
				{
				phno=phonenum.getText().toString();
				
					SmsManager smsManager = SmsManager.getDefault();
				    smsManager.sendTextMessage(phno, null, ""+num, null, null);
				    Toast.makeText(Signup.this,"You will get a code \n enter the code Below" , Toast.LENGTH_LONG).show();  
				    clicked=true;
				    code.setEnabled(true);
				   //submitcode.setAlpha((float)0.99);
				    submitcode.setClickable(true);
				}
				else
					Toast.makeText(Signup.this,"please enter your phone number" , Toast.LENGTH_LONG).show();  
			    
			}
        	
        });
        
        submitcode.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(clicked) {
				String numstr=""+num;
				 if(phno.length()>10)
		            	phno=phno.substring(phno.length()-10);
		        
				if(numstr.equals(code.getText().toString()))
				{	InternetAlertDialogManager alert=new InternetAlertDialogManager();
		    	ConnectionDetector cd =new ConnectionDetector(Signup.this);
		        boolean isInternetPresent = cd.isConnectingToInternet();
				while(!isInternetPresent) {
					// Internet Connection is not present
					alert.showNoConnectionDialog(Signup.this);
					// stop executing code by return
					return;
				    }
		       
					
					//Toast.makeText(Signup.this,phno,Toast.LENGTH_SHORT).show();
					final SharedPreferences app_preferences=getSharedPreferences("userdata",0);
			         String g_id = app_preferences.getString("g_id","no id");
			         String emailid = app_preferences.getString("emailid","");
			         String name = app_preferences.getString("name","no name");
			         String photourl = app_preferences.getString("photourl","");
			         
			         String regid = regGcm(name, emailid); 
				       
			         
			       //  Toast.makeText(Signup.this,g_id+name, Toast.LENGTH_LONG).show();
			         JSONObject signjson = JSONfunctions.getJSONfromURL(signup_url+"gid="+g_id+"&username="+name+"&phone_no="+phno+"&lati=0.0&longi=0.0"+"&reg_id="+regid);
			       //  Toast.makeText(Signup.this, signjson.toString() ,Toast.LENGTH_SHORT).show();
			         Intent i=new Intent(Signup.this,MainActivity.class);
			         startActivity(i);
			         Signup.this.finish();
			         
			       
						
				}
				else Toast.makeText(Signup.this,"Enter the correct code", Toast.LENGTH_LONG);
				
				
				
				
				 }
				 else
					 Toast.makeText(Signup.this,"Enter the phone number", Toast.LENGTH_LONG);
					 }
        	
        });
        
	}
	
	protected String regGcm(final String name , final String emailid){
		
		GCMRegistrar.checkDevice(this);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		
		final String regId = GCMRegistrar.getRegistrationId(this);
		String reg = "";
		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
			 GCMRegistrar.register(this, SENDER_ID);
			 reg = GCMRegistrar.getRegistrationId(this);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.				
				Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, name, emailid, regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}

		return reg;
		
	}
	
	
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	
}
