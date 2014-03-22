package com.test.weeklly.dummyui;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
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
import com.test.weeklly.mapsplaces.MainActivity;
import com.test.weeklly.mapsplaces.PlaceDetails;
import com.test.weeklly.mapsplaces.searchplaces;
import com.test.weeklly.mapsplaces.searchplacesactivtiy;
import com.test.weeklly.R;



@SuppressWarnings("serial")
public class EventCreation extends FragmentActivity implements Serializable
{

	
	String invitedconts="",invitednames="";
	//LoginButton authButton;  
	String lati="0.0";
	String longi="0.0";
	 private static String ID = "id";
		private static String GID = "gid";
		private static String NAME = "name";
		private static String DATE = "date";
		private static String DESC = "description";
		private static String VENUE = "venue"; 
		private static String INVITED = "invited";
		private static String ITEM = "item";
		private static String RESP= "response";
	
private static final String TAG = "EventCreation";
String bread;
PlaceDetails placeDetails;
Button invite,revert,getcontacts,setlocation;//,fbshare;
EditText purpose,Desc;//,venue;
TextView smallvenue,venuetv;
static TextView datetv,timetv,invitedtv;
static Button timpick,datepick;
static int min=0;
static int hour=0;
static int selectedday=0;
static int selectedmonth=0;
static int selectedyear=0;
Bundle bun;

	String contacts;
	
	 SharedPreferences app_preferences;
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.eventcreation );
	        
	        
	        
	        app_preferences=getSharedPreferences("userdata",0);
	        
	        revert=(Button)findViewById(R.id.eventcancel);
	       
	        invite=(Button)findViewById(R.id.eventok);
	        purpose=(EditText)findViewById(R.id.purpose);
	        Desc=(EditText)findViewById(R.id.Desc);
	      //  venue=(EditText)findViewById(R.id.Venue);
	        getcontacts=(Button)findViewById(R.id.getcontacts);
	        setlocation=(Button)findViewById(R.id.setlocation);
		        smallvenue=(TextView)findViewById(R.id.venuesmalltv);
	        datetv=(TextView)findViewById(R.id.datetv);
	        timetv=(TextView)findViewById(R.id.timetv);
	        invitedtv=(TextView)findViewById(R.id.invitedtv);
	        venuetv=(TextView)findViewById(R.id.venuetv);
		      
	        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/leaguegothic.otf");  
			 purpose.setTypeface(font);
			 Desc.setTypeface(font);
	     
	        Calendar c= Calendar.getInstance();
        	
        	min=c.get(Calendar.MINUTE);
        	hour=c.get(Calendar.HOUR_OF_DAY);
        	selectedday=c.get(Calendar.DAY_OF_MONTH);
        	selectedmonth=c.get(Calendar.MONTH);
        	selectedyear=c.get(Calendar.YEAR);
        	bun=getIntent().getExtras();
	      
	       
	     if(bun!=null){
	    	 if(bun.getString("src").equals("venue")){
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
	        setlocation.setOnClickListener(new OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent settinglocation=new Intent(EventCreation.this,searchplacesactivtiy.class);
					settinglocation.putExtra("activitysrc","eventcreation");
					startActivityForResult(settinglocation,15);
				
				}
	        	
	        });
	        datepick=(Button)findViewById(R.id.datePicker1);
	        datepick.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 DialogFragment newFragment = new DatePickerFragment();
					    newFragment.show(getSupportFragmentManager(), "datePicker");

				}
	        	
	        });
	        timpick = (Button) findViewById(R.id.timePicker1);
	        timpick.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					DialogFragment newFragment = new TimePickerFragment();
			        newFragment.show(getSupportFragmentManager(), "timePicker");
						
				}
	        	
	        });
	        
	            
	        getcontacts.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent pickingcontacts=new Intent(EventCreation.this,Contacts.class);
					//Toast.makeText(EventCreation.this,invitedconts,Toast.LENGTH_LONG).show();
					pickingcontacts.putExtra("invitedconts",invitedconts);
					startActivityForResult(pickingcontacts,5);
				}
		    	   
		       }) ;
	      	
	        revert.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				timpick.setText("SET TIME");
				timetv.setText("SET TIME");
				datepick.setText("SET DATE");
				datepick.setText("SET DATE");
				purpose.setText("INSERT MEETUP NAME HERE");
				invitedtv.setText("");
				venuetv.setText("");
				Desc.setText("");
		    	smallvenue.setText("CREATE EVENT");
		    	Calendar c= Calendar.getInstance();
		    	min=c.get(Calendar.MINUTE);
	        	hour=c.get(Calendar.HOUR_OF_DAY);
	        	selectedday=c.get(Calendar.DAY_OF_MONTH);
	        	selectedmonth=c.get(Calendar.MONTH);
	        	selectedyear=c.get(Calendar.YEAR);
	        	contacts="";
	        	invitedconts="";
			}
	    	   
	       }) ;
	        
	        invite.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					InternetAlertDialogManager alert=new InternetAlertDialogManager();
			    	ConnectionDetector cd =new ConnectionDetector(EventCreation.this);
			        boolean isInternetPresent = cd.isConnectingToInternet();
					while(!isInternetPresent) {
						// Internet Connection is not present
						alert.showNoConnectionDialog(EventCreation.this);
						// stop executing code by return
						return;
					}
					//Toast.makeText(EventCreation.this, contacts, Toast.LENGTH_LONG).show();
					
					String datetime="";
					if(selectedday<10)
					datetime+="0";
					datetime+=""+selectedday;
					
					datetime+="/";
					
					
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
					
					Constants cons=new Constants();
					  if(!purpose.getText().toString().equals("")&&!Desc.getText().toString().equals("")&&!venuetv.getText().toString().equals(""))
							
					{SharedPreferences prefs=getSharedPreferences("userdata",0);
	                String create_url = cons.ip+"WeeklyService/newevent?gid="+
					prefs.getString("g_id","")+"&title="+purpose.getText().toString().replace("'", "")+"&desc="
					+Desc.getText().toString().replace("'", "")+"&lati="+lati+"&longi="+longi+"&venue="
	                +venuetv.getText().toString().replace("'", "")+"&datetime="+datetime+"&invited="+ contacts+"&status="+"1";
	                
	                //create_url=create_url.replace(" ", "%20");
	                ProgressDialog pDialog;
					pDialog = new ProgressDialog(EventCreation.this);
					pDialog.setMessage(Html.fromHtml("<b>Feeds</b><br/>Loading Feeds..."));
					pDialog.setIndeterminate(false);
					pDialog.setCancelable(true);
					pDialog.show();
					
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

					StrictMode.setThreadPolicy(policy); 
					try {
						JSONObject json = JSONfunctions.getJSONfromURL(create_url);
						//Toast.makeText(EventCreation.this, json.toString() ,
							//	Toast.LENGTH_LONG).show();
						
						JSONObject item;
						
							item = json.getJSONObject(ITEM);
							//Toast.makeText(EventCreation.this, item.toString() ,
								//	Toast.LENGTH_LONG).show();
						String idstr = item.getString(ID);
						//Toast.makeText(EventCreation.this,"id = "+idstr, Toast.LENGTH_LONG);
						String gidstr = item.getString(GID);
						//Toast.makeText(EventCreation.this,"gid = "+gidstr, Toast.LENGTH_LONG);
						String namestr = item.getString(NAME);
						//Toast.makeText(EventCreation.this,"name = "+namestr, Toast.LENGTH_LONG).show();
						String datestr = datetime;
						//Toast.makeText(EventCreation.this,"date = "+datestr, Toast.LENGTH_LONG).show();
						String descstr = item.getString(DESC);
						//Toast.makeText(EventCreation.this,"desc = "+descstr, Toast.LENGTH_LONG).show();
						String venuestr = item.getString(VENUE);
						//Toast.makeText(EventCreation.this,"venue = "+venuestr, Toast.LENGTH_LONG).show();
						
						
						//invite.setText(json.toString());
						Intent newActivity = new Intent(EventCreation.this, myEventDesc.class);
						 newActivity.putExtra(GID, gidstr);
				          newActivity.putExtra(NAME ,namestr);
				          newActivity.putExtra(DATE,datestr);
				          newActivity.putExtra(DESC,descstr);
				          newActivity.putExtra(VENUE,venuestr);
				          newActivity.putExtra(ID, idstr);
				          newActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				          pDialog.dismiss();
						startActivity(newActivity);
						if(bun!=null){
					    	// if(bun.getString("src").equals("venue"))
					    	 {
					     setResult(10);
					     finish();
					    	 }
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(EventCreation.this, "Event Creation Failed", Toast.LENGTH_LONG).show();
					}
					}else
						Toast.makeText(EventCreation.this,"Enter text in all fields ", Toast.LENGTH_LONG).show();
				}
	        	
	        });
	        
	      /*  fbshare.setOnClickListener(new OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					facebookFeedDialog();
				}
	        	
	        });
	        */
	       
////////////////////         FB Begins       //////////////////////////

/*
	        uiHelper = new UiLifecycleHelper(EventCreation.this, callback);
	        uiHelper.onCreate(savedInstanceState);
	         authButton = (LoginButton)findViewById(R.id.authButton);

	        */
///////////////////          FB Ends         //////////////////////////
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
	timetv.setText(" "+hourOfDay+":"+minute);
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
	selectedmonth=month+1;
	selectedyear=year;
	
	datepick.setText(" "+selectedday+"/"+selectedmonth+"/"+selectedyear);
	datetv.setText(" "+selectedday+"/"+selectedmonth+"/"+selectedyear);

	
		}
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
    	invitednames=bun.getString("invitednames");
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
    //else uiHelper.onActivityResult(requestCode, resultCode, data);
    
}
////////////////////////      FB             ////////////////////
/*
private UiLifecycleHelper uiHelper;
private Session.StatusCallback callback = new Session.StatusCallback() {
    @Override
    public void call(final Session session, final SessionState state, final Exception exception) {
        onSessionStateChange(session, state, exception);
    }
};
@Override
public void onResume() {
    super.onResume();
    
    // For scenarios where the main activity is launched and user
	// session is not null, the session state change notification
	// may not be triggered. Trigger it if it's open/closed.
	Session session = Session.getActiveSession();
	if (session != null &&
			(session.isOpened() || session.isClosed()) ) {
		onSessionStateChange(session, session.getState(), null);
	}
	
    uiHelper.onResume();
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
    	getcontacts.setText(contacts);
    	}
    }
    else
    	uiHelper.onActivityResult(requestCode, resultCode, data);
    
}

@Override
public void onPause() {
    super.onPause();
    uiHelper.onPause();
}

@Override
public void onDestroy() {
    super.onDestroy();
    uiHelper.onDestroy();
}

@Override
public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    uiHelper.onSaveInstanceState(outState);
}

private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	if (state.isOpened()) {
		Log.i(TAG, "Logged in...");
		authButton.setVisibility(View.INVISIBLE);
		fbshare.setVisibility(View.VISIBLE);
    } else if (state.isClosed()) {
    	Log.i(TAG, "Logged out...");
    	authButton.setVisibility(View.VISIBLE);
		fbshare.setVisibility(View.INVISIBLE);
    }
}
    

  private void facebookFeedDialog() {
  	// Set the dialog parameters
  	Bundle params = new Bundle();
  	params.putString("name",app_preferences.getString("name",null));
  	params.putString("caption",purpose.getText().toString());
  	params.putString("description",Desc.getText().toString()+"\n venue -"+venue.getText().toString()+"\n @ "+hour+":"+min+ "\n on "+selectedday+"/"+selectedmonth+"/"+selectedyear);
  	params.putString("link", "http://www.foofys.com");
  	//params.putString("picture", "http://www.sugarmedia.com/nyccookbook/images/pizza.jpg");
  	
  	// Invoke the dialog
  	WebDialog feedDialog = (
  			new WebDialog.FeedDialogBuilder(EventCreation.this,
  					Session.getActiveSession(),
  					params))
  					.setOnCompleteListener(new OnCompleteListener() {

  						@Override
  						public void onComplete(Bundle values,
  								FacebookException error) {
  							if (error == null) {
  								// When the story is posted, echo the success
      							// and the post Id.
  								final String postId = values.getString("post_id");
      							if (postId != null) {
      								Toast.makeText(EventCreation.this,
      										"Story published: "+postId,
      										Toast.LENGTH_SHORT).show();
      							}
  							}
  						}
  						
  						})
  					.build();
  	feedDialog.show();
  	
  }
  
///////////////////////////            FB ENDS          ////////////////////////




*/


}
