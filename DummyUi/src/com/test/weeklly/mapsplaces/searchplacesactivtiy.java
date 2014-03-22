package com.test.weeklly.mapsplaces;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.test.weeklly.dummyui.EventCreation;
import com.test.weeklly.dummyui.InternetAlertDialogManager;
import com.test.weeklly.mapsplaces.MainActivity.LoadPlaces;
import com.test.weeklly.R;

public class searchplacesactivtiy extends Activity{

	Boolean nearbysearch=true;
	EditText search;
	CheckBox nearby;
	Bundle bun;
	AlertDialogManager alert = new AlertDialogManager();
	//	View rootView; 
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	       setContentView(R.layout.places);
		    ConnectionDetector cd = new ConnectionDetector(searchplacesactivtiy.this);
			Button btnFind;
			btnFind = ( Button ) findViewById(R.id.btn_find);
			search=(EditText)findViewById(R.id.searchet);
			Typeface font = Typeface.createFromAsset(searchplacesactivtiy.this.getAssets(), "fonts/leaguegothic.otf");  
		     search.setTypeface(font); 
		     bun=getIntent().getExtras();
			nearby=(CheckBox)findViewById(R.id.placesSearchnearby);
			// Check if Internet present
			InternetAlertDialogManager inalert=new InternetAlertDialogManager();
	    	ConnectionDetector incd =new ConnectionDetector(searchplacesactivtiy.this);
	        boolean isInternet = incd.isConnectingToInternet();
			while(!isInternet) {
				// Internet Connection is not present
				inalert.showNoConnectionDialog(searchplacesactivtiy.this);
				// stop executing code by return
				return;
			}
			Boolean isInternetPresent = cd.isConnectingToInternet();
			if (!isInternetPresent) {
				// Internet Connection is not present
				alert.showAlertDialog(searchplacesactivtiy.this, "Internet Connection Error",
						"Please connect to working Internet connection", false);
				// stop executing code by return
				return;
			}

			// creating GPS Class object
			GPSTracker gps = new GPSTracker(searchplacesactivtiy.this);

			// check if GPS location can get
			if (gps.canGetLocation()) {
				Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
			} else {
				// Can't get user's current location
				alert.showAlertDialog(searchplacesactivtiy.this, "GPS Status",
						"Couldn't get location information. Please enable GPS",
						false);
				return;
				// stop executing code by return
				
			}

			
			nearby.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					
					nearbysearch=isChecked;
					
					
				}
				
			});
			
		LinearLayout coffee=(LinearLayout)findViewById(R.id.coffee);
			coffee.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (searchplacesactivtiy.this,MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "eventcreation");
				in.putExtra("stext", "coffee");
				in.putExtra("checked",nearbysearch);
				startActivityForResult(in,15);
				}
				
			});
			LinearLayout Restaurants=(LinearLayout)findViewById(R.id.restaurant);
			Restaurants.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (searchplacesactivtiy.this,MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "eventcreation");
				in.putExtra("stext", "restaurant");
				in.putExtra("checked",nearbysearch);
				startActivityForResult(in,15);
				}
				
			});
			LinearLayout Smalls=(LinearLayout)findViewById(R.id.shoppingmalls);
			Smalls.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (searchplacesactivtiy.this,MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "eventcreation");
				in.putExtra("stext", "shopping_mall");
				in.putExtra("checked",nearbysearch);
				startActivityForResult(in,15);
				}
				
			});
			LinearLayout Movies=(LinearLayout)findViewById(R.id.Movies);
			Movies.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (searchplacesactivtiy.this,MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "eventcreation");
				in.putExtra("stext", "movie_theater");
				in.putExtra("checked",nearbysearch);
				startActivityForResult(in,15);
				}
				
			});
			LinearLayout pubs=(LinearLayout)findViewById(R.id.pubs);
			pubs.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (searchplacesactivtiy.this,MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "eventcreation");
				in.putExtra("stext", "night_club");
				in.putExtra("checked",nearbysearch);
				startActivityForResult(in,15);
				}
				
			});
			LinearLayout Stadium=(LinearLayout)findViewById(R.id.stadiums);
			Stadium.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (searchplacesactivtiy.this,MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "eventcreation");
				in.putExtra("stext", "stadium");
				in.putExtra("checked",nearbysearch);
				startActivityForResult(in,15);
				}
				
			});
			LinearLayout Bars=(LinearLayout)findViewById(R.id.bars);
			Bars.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (searchplacesactivtiy.this,MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "eventcreation");
				in.putExtra("stext", "bar");
				in.putExtra("checked",nearbysearch);
				startActivityForResult(in,15);
				}
				
			});
			LinearLayout Aparks=(LinearLayout)findViewById(R.id.amusementparks);
			Aparks.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (searchplacesactivtiy.this,MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "eventcreation");
				in.putExtra("stext", "amusement_park");
				in.putExtra("checked",nearbysearch);
				startActivityForResult(in,15);
				}
				
			});
			LinearLayout parks=(LinearLayout)findViewById(R.id.park);
			parks.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (searchplacesactivtiy.this,MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "eventcreation");
				in.putExtra("stext", "park");
				in.putExtra("checked",nearbysearch);
				startActivityForResult(in, 15);
				}
				
			});
			btnFind.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {	
					if(search.getText().toString()!="")
					{
						Intent in=new Intent (searchplacesactivtiy.this,MainActivity.class);
					in.putExtra("src","btn");
					in.putExtra("activitysrc", "eventcreation");
					in.putExtra("stext",search.getText().toString());
					in.putExtra("checked",nearbysearch);
					startActivityForResult(in,15);
					}
				}
				
			});
		 }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK)
		setResult(RESULT_OK,data);
		finish();
	}
		

}
