package com.test.weeklly.dummyui;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.androidhive.jsonparsing.JSONfunctions;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.FacebookException;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.test.weeklly.gplus.ShareActivity;
import com.test.weeklly.gplus.SignInActivity;
import com.test.weeklly.mapsplaces.ConnectionDetector;
import com.test.weeklly.mapsplaces.PlaceDetails;
import com.test.weeklly.mapsplaces.searchplacesactivtiy;
import com.test.weeklly.R;



@SuppressWarnings("serial")
public class EventUpdate extends FragmentActivity implements Serializable
{

	String invited="",invitedconts="",names="";
	 private static String ID = "id";
		private static String GID = "gid";
		private static String NAME = "name";
		private static String DATE = "date";
		private static String DESC = "description";
		private static String VENUE = "venue"; 
		private static String INVITED = "invited";
		private static String ITEM = "item";
		private static String RESP= "response";

	LoginButton authButton;  
	String lati="0.0";
	String longi="0.0";
	TextView smallvenue,venuetv;
static TextView datetv,timetv,invitedtv;
private static final String TAG = "EventCreation";
String bread,Eventid;
PlaceDetails placeDetails;
Button invite,revert,getcontacts,setlocation;
EditText purpose,Desc;//,venue;
static Button timpick,datepick;
static int min=0;
static int hour=0;
static int selectedday=0;
static int selectedmonth=0;
static int selectedyear=0;

	String contacts;
	Bundle bun;
	 SharedPreferences app_preferences;
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.eventcreation );
	        
	        
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy); 
	        
	        bun=getIntent().getExtras();
	        app_preferences=getSharedPreferences("userdata",0);
	        
	        revert=(Button)findViewById(R.id.eventcancel);
	       
	  
	        invite=(Button)findViewById(R.id.eventok);
	        purpose=(EditText)findViewById(R.id.purpose);
	        Desc=(EditText)findViewById(R.id.Desc);
	        venuetv=(TextView)findViewById(R.id.venuetv);
	        getcontacts=(Button)findViewById(R.id.getcontacts);
	        setlocation=(Button)findViewById(R.id.setlocation);
		    datetv=(TextView)findViewById(R.id.datetv);
	        timetv=(TextView)findViewById(R.id.timetv);
	        invitedtv=(TextView)findViewById(R.id.invitedtv);
	        smallvenue=(TextView)findViewById(R.id.venuesmalltv);
	        smallvenue.setText("EVENT UPDATE");
	       
	    
	       
	        	invite.setText("Update");
	        	Calendar c= Calendar.getInstance();
	        	
	        	min=c.get(Calendar.MINUTE);
	        	hour=c.get(Calendar.HOUR_OF_DAY);
	        	selectedday=c.get(Calendar.DAY_OF_MONTH);
	        	selectedmonth=c.get(Calendar.MONTH);
	        	selectedyear=c.get(Calendar.YEAR);
	        	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/leaguegothic.otf");  
				 purpose.setTypeface(font);
				 Desc.setTypeface(font);
		     
	     if(bun!=null){
	    	purpose.setText(bun.getString(NAME).toUpperCase());
	    	Desc.setText(bun.getString(DESC).toUpperCase());
	    	venuetv.setText(bun.getString(VENUE).toUpperCase());
	    	invited=bun.getString(INVITED);
	    	Eventid=bun.getString(ID);
	    	//Toast.makeText(EventUpdate.this,invited,Toast.LENGTH_LONG).show();
	    	
	    }
	        getAttending();
	        invitedtv.setText(names.toUpperCase());
	        datepick=(Button)findViewById(R.id.datePicker1);
	        String datetime=bun.getString(DATE);
	        String date=datetime.substring(0,10);
	        String time=datetime.substring(11,16);
	        datepick.setText(date);
	        datetv.setText(date);
	        datepick.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 DialogFragment newFragment = new DatePickerFragment();
					    newFragment.show(getSupportFragmentManager(), "datePicker");

				}
	        	
	        });
	        timpick = (Button) findViewById(R.id.timePicker1);
	        timpick.setText(time);
	        timetv.setText(time);
	        timpick.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					DialogFragment newFragment = new TimePickerFragment();
			        newFragment.show(getSupportFragmentManager(), "timePicker");
						
				}
	        	
	        });
	        
	        setlocation.setOnClickListener(new OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent settinglocation=new Intent(EventUpdate.this,searchplacesactivtiy.class);
					settinglocation.putExtra("activitysrc","eventcreation");
					startActivityForResult(settinglocation,15);
				
				}
	        	
	        }); 
	        getcontacts.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent pickingcontacts=new Intent(EventUpdate.this,Contacts.class);
					pickingcontacts.putExtra("invitedconts",invitedconts);
					startActivityForResult(pickingcontacts,5);
				}
		    	   
		       }) ;
	      	
	        revert.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
	    	   
	       }) ;
	        
	        invite.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ArrayList<String> noninvited=new ArrayList();
					ArrayList<String> addinvited=new ArrayList();
					boolean check;
					
					 for (String ret1: invited.split(",",1000))
						 if(!ret1.equals(null) && !ret1.equals("") )
						 {check=false;
						 for (String ret2: invitedconts.split(",",1000))
							 if(ret2!=null && ret2!="" )
							 {
							 	if(ret1.equals(ret2)&&!ret1.equals(""))
							 		{
							 		check=true;
							 		}
					 	  }
						 if(!check)
						 {
							 noninvited.add(ret1);
							 //Toast.makeText(EventUpdate.this,ret1,Toast.LENGTH_LONG).show();
						 }
							 
				 	  }
					 //Toast.makeText(EventUpdate.this,"invited="+invited+"\niconts="+invitedconts+"\n ninvi="+noninvited.toString(),Toast.LENGTH_LONG).show();
						
						
					 for (String ret1: invitedconts.split(",",1000))
						 if(!ret1.equals(null) && !ret1.equals("") )
				 	  {check=false;
						 for (String ret2: invited.split(",",1000))
							 if(!ret2.equals(null) && !ret2.equals("") )
					 	  {
							 	if(ret1.equals(ret2))
							 		{
							 		check=true;
							 		}
					 	  }
						 if(!check)
						 {
							 noninvited.add(ret1);
							 //Toast.makeText(EventUpdate.this,ret1,Toast.LENGTH_LONG).show();
						 }
							 
				 	  }
					 //Toast.makeText(EventUpdate.this,"invited="+invited+"\niconts="+invitedconts+"\n ninvi="+noninvited.toString(),Toast.LENGTH_LONG).show();
						
					 Constants cons = new Constants();
					 
					 for(String people : noninvited){
						 if(!people.equals(null) && !people.equals("") ){
						 String cancel_url = cons.ip + "WeeklyService/cancel?event_id="+bun.getString(ID)+"&invited_id="+people;
						 JSONObject json = JSONfunctions.getJSONfromURL(cancel_url);
						 Toast.makeText(EventUpdate.this,json.toString(),Toast.LENGTH_LONG).show();
						 }
					 }
					 
					/* for(String people : addinvited){
						 if(!people.equals(null) && !people.equals("") ){
						 Toast.makeText(EventUpdate.this,bun.getString(ID),Toast.LENGTH_LONG).show();
						 String cancel_url = cons.ip + "WeeklyService/cancel?event_id="+bun.getString(ID)+"&invited_id="+people;
						 JSONObject json = JSONfunctions.getJSONfromURL(cancel_url);
						 Toast.makeText(EventUpdate.this,json.toString(),Toast.LENGTH_LONG).show();
						 }
						 }
					*/
					InternetAlertDialogManager alert=new InternetAlertDialogManager();
			    	ConnectionDetector cd =new ConnectionDetector(EventUpdate.this);
			        boolean isInternetPresent = cd.isConnectingToInternet();
					while(!isInternetPresent) {
						// Internet Connection is not present
						alert.showNoConnectionDialog(EventUpdate.this);
						// stop executing code by return
						return;
					}
			       
					String datetime="";
					if(selectedday<10)
					datetime+="0";
					datetime+=""+selectedday;
					
					datetime+="/";
					
					selectedmonth++;
					if(selectedmonth<10)
						datetime+="0";
					datetime+=selectedmonth;
					
					datetime+="/";
					datetime+=selectedyear;
					datetime+=" ";
					
					if(hour<10)
					datetime+="0";
					datetime+=hour;
					
					datetime+=":";
					
					if(min<10)
					datetime+="0";
					datetime+=min;
					
					SharedPreferences prefs=getSharedPreferences("userdata",0);
	                String create_url = cons.ip+"WeeklyService/updateevent?event_id="+Eventid+"&gid="+
					prefs.getString("g_id","")+"&title="+purpose.getText().toString().replace("'","")+"&desc="
					+Desc.getText().toString().replace("'", "")+"&lati="+lati+"&longi="+longi+"&venue="
	                +venuetv.getText().toString().replace("'", "")+"&datetime="+datetime;
	               // invite.setText(create_url);
					 
	        
					try{
					JSONObject json = JSONfunctions.getJSONfromURL(create_url);
					//Toast.makeText(EventUpdate.this, json.toString() ,
					//		Toast.LENGTH_LONG).show();
					
					JSONObject item;
					
					item = json.getJSONObject(ITEM);
				
				String idstr = item.getString(ID);
				//Toast.makeText(EventUpdate.this,"id = "+idstr, Toast.LENGTH_LONG);
				String gidstr = item.getString(GID);
				//Toast.makeText(EventUpdate.this,"gid = "+gidstr, Toast.LENGTH_LONG);
				String namestr = item.getString(NAME);
				//Toast.makeText(EventUpdate.this,"name = "+namestr, Toast.LENGTH_LONG).show();
				String datestr = datetime;
				//Toast.makeText(EventUpdate.this,"date = "+datestr, Toast.LENGTH_LONG).show();
				String descstr = item.getString(DESC);
				//Toast.makeText(EventUpdate.this,"desc = "+descstr, Toast.LENGTH_LONG).show();
				String venuestr = item.getString(VENUE);
				//Toast.makeText(EventUpdate.this,"venue = "+venuestr, Toast.LENGTH_LONG).show();
				
				
			//	invite.setText(json.toString());
				invited=invitedconts;
				
				Intent newActivity = new Intent(EventUpdate.this, myEventDesc.class);
				 newActivity.putExtra(GID, gidstr);
		          newActivity.putExtra(NAME ,namestr);
		          newActivity.putExtra(DATE,datestr);
		          newActivity.putExtra(DESC,descstr);
		          newActivity.putExtra(VENUE,venuestr);
		          newActivity.putExtra(ID, idstr);
		          newActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(newActivity);
				setResult(13);
				finish();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(EventUpdate.this, "Event update Failed", Toast.LENGTH_LONG).show();
			}
					
					
				//	Intent i = new Intent(EventCreation.this,myEventDesc.class);
				//	startActivity(i);
				}
	        	
	        });
	        
	        
	        
}
	   
public static class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current time as the default values for the picker
final Calendar c = Calendar.getInstance();
int hour = c.get(Calendar.HOUR_OF_DAY);
int minute = c.get(Calendar.MINUTE);

// Create a new instance of TimePickerDialog and return it
return new TimePickerDialog(getActivity(), this, hour, minute,
DateFormat.is24HourFormat(getActivity()));
}

public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
// Do something with the time chosen by the user
	   timpick.setText(" "+hourOfDay+":"+minute);
	min=minute;
	hour=hourOfDay;
}


}
public static class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener {

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
// Do something with the date chosen by the user
	
	selectedday=day;
	selectedmonth=month;
	selectedyear=year;
	
	datepick.setText(" "+selectedday+"/"+selectedmonth+"/"+selectedyear);
	datetv.setText(" "+selectedday+"/"+selectedmonth+"/"+selectedyear);

		}
	}



@Override
public void onResume() {
    super.onResume();
   
	
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    
    if(requestCode==5)
    {
    	if(resultCode==RESULT_OK)
    	{Bundle bun=data.getExtras();
    	contacts=bun.getString("result");
    	invitedconts=bun.getString("invitedconts");
    	String invitednames = bun.getString("invitednames");
    	invitedtv.setText(invitednames);
    	//getcontacts.setText(contacts);
    	}
    }
    if(requestCode==15)
    {
    	if(resultCode==RESULT_OK)
    	{   bun=data.getExtras();
	        placeDetails=(PlaceDetails) bun.getSerializable("place"); 
	         String name = placeDetails.result.name;
				String address = placeDetails.result.formatted_address;
				String phone = placeDetails.result.formatted_phone_number;
				String latitude = Double.toString(placeDetails.result.geometry.location.lat);
				String longitude = Double.toString(placeDetails.result.geometry.location.lng);
				String vicinity = placeDetails.result.vicinity;
				lati = latitude; longi = longitude;
				smallvenue.setText(name);
	         String Str =name+"\n"+address+"\n"+phone;
	       //  venue.setText(Str);
	        Str =name;
	         if(address!=null)
	        	 Str+=","+address;
	        	 if(phone!=null)
	        		 Str+=".\nph-no : "+phone;
	         venuetv.setText(Str);
	    	 
    		
    	}
    	
    }
    
}

@Override
public void onPause() {
    super.onPause();
}

@Override
public void onDestroy() {
    super.onDestroy();
}

@Override
public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
}


  
private void getAttending(){
	Constants cons = new Constants();
    String getinfo_url= cons.ip+"WeeklyService/attending?event_id="+bun.getString(ID);
   //Toast.makeText(EventUpdate.this, bun.getString(ID), Toast.LENGTH_LONG).show();
	
	JSONObject json = JSONfunctions.getJSONfromURL(getinfo_url);
	// Toast.makeText(EventUpdate.this, json.toString(), Toast.LENGTH_LONG).show();
		
	JSONArray item;
	try {
		item = json.getJSONArray(ITEM);
		for (int i=0; i < item.length(); i++) {
			JSONObject row = item.getJSONObject(i);
			
			String name = row.getString(NAME);
			String gid = row.getString(GID);
			names+=name+",";
			invited += gid + ",";
		
		}
		invitedconts=invited;
	//	Toast.makeText(myEventDesc.this, item.toString(),Toast.LENGTH_LONG).show();
		if(names.length()>0)
		names=names.substring(0,names.length()-1);
	
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
}





}
