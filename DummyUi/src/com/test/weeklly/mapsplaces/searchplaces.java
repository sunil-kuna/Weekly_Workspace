package com.test.weeklly.mapsplaces;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.test.weeklly.dummyui.EventCreation;
import com.test.weeklly.mapsplaces.MainActivity.LoadPlaces;
import com.test.weeklly.R;

public class searchplaces extends SherlockFragment{

	Boolean nearbysearch=true;
	EditText search;
	CheckBox nearby;
	
	AlertDialogManager alert = new AlertDialogManager();
		View rootView; 
		 public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
		    rootView = inflater.inflate(R.layout.places,
						container, false);
		    ConnectionDetector cd = new ConnectionDetector(rootView.getContext());
			Button btnFind;
			btnFind = ( Button ) rootView.findViewById(R.id.btn_find);
			search=(EditText)rootView.findViewById(R.id.searchet);
			Typeface font = Typeface.createFromAsset(rootView.getContext().getAssets(), "fonts/leaguegothic.otf");  
		     search.setTypeface(font); 
		     
			nearby=(CheckBox)rootView.findViewById(R.id.placesSearchnearby);
			// Check if Internet present
			Boolean isInternetPresent = cd.isConnectingToInternet();
			if (!isInternetPresent) {
				// Internet Connection is not present
				alert.showAlertDialog(rootView.getContext(), "Internet Connection Error",
						"Please connect to working Internet connection", false);
				// stop executing code by return
				
			}

			// creating GPS Class object
			GPSTracker gps = new GPSTracker(rootView.getContext());

			// check if GPS location can get
			if (gps.canGetLocation()) {
				Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
			} else {
				// Can't get user's current location
				alert.showAlertDialog(rootView.getContext(), "GPS Status",
						"Couldn't get location information. Please enable GPS",
						false);
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
			
		LinearLayout coffee=(LinearLayout)rootView.findViewById(R.id.coffee);
			coffee.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (rootView.getContext(),MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "venue");
				in.putExtra("stext", "coffee");
				in.putExtra("checked",nearbysearch);
				startActivity(in);
				}
				
			});
			LinearLayout Restaurants=(LinearLayout)rootView.findViewById(R.id.restaurant);
			Restaurants.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (rootView.getContext(),MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "venue");
				in.putExtra("stext", "restaurant");
				in.putExtra("checked",nearbysearch);
				startActivity(in);
				}
				
			});
			LinearLayout Smalls=(LinearLayout)rootView.findViewById(R.id.shoppingmalls);
			Smalls.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (rootView.getContext(),MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "venue");
				in.putExtra("stext", "shopping_mall");
				in.putExtra("checked",nearbysearch);
				startActivity(in);
				}
				
			});
			LinearLayout Movies=(LinearLayout)rootView.findViewById(R.id.Movies);
			Movies.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (rootView.getContext(),MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "venue");
				in.putExtra("stext", "movie_theater");
				in.putExtra("checked",nearbysearch);
				startActivity(in);
				}
				
			});
			LinearLayout pubs=(LinearLayout)rootView.findViewById(R.id.pubs);
			pubs.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (rootView.getContext(),MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "venue");
				in.putExtra("stext", "night_club");
				in.putExtra("checked",nearbysearch);
				startActivity(in);
				}
				
			});
			LinearLayout Stadium=(LinearLayout)rootView.findViewById(R.id.stadiums);
			Stadium.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (rootView.getContext(),MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "venue");
				in.putExtra("stext", "stadium");
				in.putExtra("checked",nearbysearch);
				startActivity(in);
				}
				
			});
			LinearLayout Bars=(LinearLayout)rootView.findViewById(R.id.bars);
			Bars.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (rootView.getContext(),MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "venue");
				in.putExtra("stext", "bar");
				in.putExtra("checked",nearbysearch);
				startActivity(in);
				}
				
			});
			LinearLayout Aparks=(LinearLayout)rootView.findViewById(R.id.amusementparks);
			Aparks.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (rootView.getContext(),MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "venue");
				in.putExtra("stext", "amusement_park");
				in.putExtra("checked",nearbysearch);
				startActivity(in);
				}
				
			});
			LinearLayout parks=(LinearLayout)rootView.findViewById(R.id.park);
			parks.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent in =new Intent (rootView.getContext(),MainActivity.class);
				in.putExtra("src", "list");
				in.putExtra("activitysrc", "venue");
				in.putExtra("stext", "park");
				in.putExtra("checked",nearbysearch);
				startActivity(in);
				}
				
			});
			btnFind.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {	
					if(search.getText().toString()!="")
					{
						Intent in=new Intent (rootView.getContext(),MainActivity.class);
					in.putExtra("src","btn");
					in.putExtra("activitysrc", "venue");
					in.putExtra("stext",search.getText().toString());
					in.putExtra("checked",nearbysearch);
					startActivity(in);
					}
				}
				
			});
		    
		    return rootView;
		 }
		

}
