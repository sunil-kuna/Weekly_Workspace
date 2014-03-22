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
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
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
import com.test.weeklly.R;



@SuppressWarnings("serial")
public class EventCreationclass extends SherlockFragment implements Serializable
{

	
	String invitedconts="";
	//LoginButton authButton;  
	String lati="0.0";
	String longi="0.0";
	private static String ID = "id";
	private static String GID = "gid";
	private static String STATUS = "status";
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
Button invite,revert,getcontacts;//,fbshare;
EditText purpose,Desc,venue;
static Button timpick,datepick;
static int min=0;
static int hour=0;
static int selectedday=0;
static int selectedmonth=0;
static int selectedyear=0;

	String contacts;
	Bundle bun;
	 SharedPreferences app_preferences;
	 
	 View rootView; 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
	    	 rootView = inflater.inflate(R.layout.eventcreation,
					container, false);
		    
	          
	        
	        
	        app_preferences=rootView.getContext().getSharedPreferences("userdata",0);
	        
	        revert=(Button)rootView.findViewById(R.id.eventcancel);
	       
	        invite=(Button)rootView.findViewById(R.id.eventok);
	        purpose=(EditText)rootView.findViewById(R.id.purpose);
	        Desc=(EditText)rootView.findViewById(R.id.Desc);
	     //   venue=(EditText)rootView.findViewById(R.id.Venue);
	        getcontacts=(Button)rootView.findViewById(R.id.getcontacts);
	       
	       
	        	
	     
	        Calendar c= Calendar.getInstance();
        	
        	min=c.get(Calendar.MINUTE);
        	hour=c.get(Calendar.HOUR_OF_DAY);
        	selectedday=c.get(Calendar.DAY_OF_MONTH);
        	selectedmonth=c.get(Calendar.MONTH);
        	selectedyear=c.get(Calendar.YEAR);
        	bun=((Activity) rootView.getContext()).getIntent().getExtras();
	      
	       
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
	         String Str =name+address+phone+latitude+longitude+vicinity;
	         venue.setText(Str);
	    	 }
	       
	    }
	        
	        datepick=(Button)rootView.findViewById(R.id.datePicker1);
	        datepick.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 DialogFragment newFragment = new DatePickerFragment();
					    newFragment.show(getChildFragmentManager(), "datePicker");

				}
	        	
	        });
	        timpick = (Button)rootView. findViewById(R.id.timePicker1);
	        timpick.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					DialogFragment newFragment = new TimePickerFragment();
			        newFragment.show(getChildFragmentManager(), "timePicker");
						
				}
	        	
	        });
	        
	            
	        getcontacts.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent pickingcontacts=new Intent(rootView.getContext(),Contacts.class);
					//Toast.makeText(rootView.getContext(),invitedconts,Toast.LENGTH_LONG).show();
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
					InternetAlertDialogManager alert=new InternetAlertDialogManager();
			    	ConnectionDetector cd =new ConnectionDetector(rootView.getContext());
			        boolean isInternetPresent = cd.isConnectingToInternet();
					while(!isInternetPresent) {
						// Internet Connection is not present
						alert.showNoConnectionDialog(rootView.getContext());
						// stop executing code by return
						return;
					}
			       
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
					
					SharedPreferences prefs=rootView.getContext().getSharedPreferences("userdata",0);
	                if(!purpose.getText().toString().equals("")&&!Desc.getText().toString().equals("")&&!venue.getText().toString().equals(""))
					
					{String create_url = cons.ip+"WeeklyService/newevent?gid="+
					prefs.getString("g_id","")+"&title="+purpose.getText().toString()+"&desc="
					+Desc.getText().toString()+"&lati="+lati+"&longi="+longi+"&venue="
	                +venue.getText().toString()+"&datetime="+datetime+"&invited="+ contacts+"&status="+"1";
	                
	                //create_url=create_url.replace(" ", "%20");
					
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

					StrictMode.setThreadPolicy(policy); 
					try {
						JSONObject json = JSONfunctions.getJSONfromURL(create_url);
						invite.setText(json.toString() + " " + datetime);
						JSONObject item;
						
							item = json.getJSONObject(ITEM);
							//Toast.makeText(rootView.getContext(),item.toString(), Toast.LENGTH_LONG);
							
						String idstr = item.getString(ID);
						//Toast.makeText(rootView.getContext(),"id = "+idstr, Toast.LENGTH_LONG).show();
						String gidstr = item.getString(GID);
						//Toast.makeText(rootView.getContext(),"gid = "+gidstr, Toast.LENGTH_LONG).show();
						String namestr = item.getString(NAME);
						//Toast.makeText(rootView.getContext(),"name = "+namestr, Toast.LENGTH_LONG).show();
						String datestr = datetime;
						//Toast.makeText(rootView.getContext(),"date = "+datestr, Toast.LENGTH_LONG).show();
						String descstr = item.getString(DESC);
						//Toast.makeText(rootView.getContext(),"desc = "+descstr, Toast.LENGTH_LONG).show();
						String venuestr = item.getString(VENUE);
						//Toast.makeText(rootView.getContext(),"venue = "+venuestr, Toast.LENGTH_LONG).show();
						
						
						invite.setText(json.toString());
						Intent newActivity = new Intent(rootView.getContext(), myEventDesc.class);
						 newActivity.putExtra(GID, gidstr);
				          newActivity.putExtra(NAME ,namestr);
				          newActivity.putExtra(DATE,datestr);
				          newActivity.putExtra(DESC,descstr);
				          newActivity.putExtra(VENUE,venuestr);
				          newActivity.putExtra(ID, idstr);
				        //  newActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(newActivity);
						 if(bun!=null){
					    	 if(bun.getString("src").equals("venue")){
					     //setResult(10);
					     //finish();
					    	 }
						 }
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(rootView.getContext(), "Event Creation Failed", Toast.LENGTH_LONG).show();
					}
					}
				}
	        	
	        });
			return rootView;
	        
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
	        uiHelper = new UiLifecycleHelper(rootView.getContext(), callback);
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
	
		}
	}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    
    if(requestCode==5)
    {
    	
    	{Bundle bun=data.getExtras();
    	contacts=bun.getString("result");
    	invitedconts=bun.getString("invitedconts");
    	getcontacts.setText(contacts);
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
  			new WebDialog.FeedDialogBuilder(rootView.getContext(),
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
      								Toast.makeText(rootView.getContext(),
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
