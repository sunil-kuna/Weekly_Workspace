package com.test.weekly.mapsplaces;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.test.weekly.R;

public class PlacesMapActivity extends FragmentActivity {
	// Nearest places
	PlacesList nearPlaces;

	// Map view
	GoogleMap mGoogleMap;

	// Map overlay items
	List<Overlay> mapOverlays;

	AddItemizedOverlay itemizedOverlay;

	GeoPoint geoPoint;
	// Map controllers
	MapController mc;
	
	double latitude;
	double longitude;
	OverlayItem overlayitem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_places);

		// Getting intent data
		Intent i = getIntent();
		
		// Users current geo location
		Double user_latitude = i.getDoubleExtra("user_latitude", 0.0);
		Double user_longitude = i.getDoubleExtra("user_longitude", 0.0);
		nearPlaces = (PlacesList) i.getSerializableExtra("near_places");

    	SupportMapFragment fragment = ( SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	 	
    	// Getting Google Map
    	mGoogleMap = fragment.getMap();
    			
    	// Enabling MyLocation in Google Map
    	mGoogleMap.setMyLocationEnabled(true);
    	LatLng mlatLng = new LatLng(user_latitude, user_longitude);
		
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mlatLng));
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    	
    	if (nearPlaces.results != null) {
			// loop through all the places
    		MarkerOptions markerOptions = new MarkerOptions();
			for (Place place : nearPlaces.results) {
				latitude = place.geometry.location.lat; // latitude
				longitude = place.geometry.location.lng; // longitude
		
            
            // Getting name
            String name = place.name;
            
            // Getting vicinity
            String vicinity = place.vicinity;
            
            LatLng latLng = new LatLng(latitude, longitude);;
            // Setting the position for the marker
            markerOptions.position(latLng);

            // Setting the title for the marker. 
            //This will be displayed on taping the marker
            markerOptions.title(name + " : " + vicinity);	            

            // Placing a marker on the touched position
            mGoogleMap.addMarker(markerOptions);            
      
			}		
    	}
		
	/*	
		// Geopoint to place on map
		geoPoint = new GeoPoint((int) (Double.parseDouble(user_latitude) * 1E6),
				(int) (Double.parseDouble(user_longitude) * 1E6));
		
		// Drawable marker icon
		Drawable drawable_user = this.getResources()
				.getDrawable(R.drawable.mark_red);
		
		itemizedOverlay = new AddItemizedOverlay(drawable_user, this);
		
		// Map overlay item
		overlayitem = new OverlayItem(geoPoint, "Your Location",
				"That is you!");

		itemizedOverlay.addOverlay(overlayitem);
		
		mapOverlays.add(itemizedOverlay);
		itemizedOverlay.populateNow();
		
		// Drawable marker icon
		Drawable drawable = this.getResources()
				.getDrawable(R.drawable.mark_blue);
		
		itemizedOverlay = new AddItemizedOverlay(drawable, this);

		mc = mGoogleMap.getController();

		// These values are used to get map boundary area
		// The area where you can see all the markers on screen
		int minLat = Integer.MAX_VALUE;
		int minLong = Integer.MAX_VALUE;
		int maxLat = Integer.MIN_VALUE;
		int maxLong = Integer.MIN_VALUE;

		// check for null in case it is null
		if (nearPlaces.results != null) {
			// loop through all the places
			for (Place place : nearPlaces.results) {
				latitude = place.geometry.location.lat; // latitude
				longitude = place.geometry.location.lng; // longitude
				
				// Geopoint to place on map
				geoPoint = new GeoPoint((int) (latitude * 1E6),
						(int) (longitude * 1E6));
				
				// Map overlay item
				overlayitem = new OverlayItem(geoPoint, place.name,
						place.vicinity);

				itemizedOverlay.addOverlay(overlayitem);
				
				
				// calculating map boundary area
				minLat  = (int) Math.min( geoPoint.getLatitudeE6(), minLat );
			    minLong = (int) Math.min( geoPoint.getLongitudeE6(), minLong);
			    maxLat  = (int) Math.max( geoPoint.getLatitudeE6(), maxLat );
			    maxLong = (int) Math.max( geoPoint.getLongitudeE6(), maxLong );
			}
			mapOverlays.add(itemizedOverlay);
			
			// showing all overlay items
			itemizedOverlay.populateNow();
		}
		
		// Adjusting the zoom level so that you can see all the markers on map
		mapView.getController().zoomToSpan(Math.abs( minLat - maxLat ), Math.abs( minLong - maxLong ));
		
		// Showing the center of the map
		mc.animateTo(new GeoPoint((maxLat + minLat)/2, (maxLong + minLong)/2 ));
		mapView.postInvalidate();
*/
	}
/*
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}*/
	
}
