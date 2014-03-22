package com.test.weeklly.dummyui;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidhive.jsonparsing.JSONfunctions;
import com.test.weeklly.mapsplaces.AlertDialogManager;
import com.test.weeklly.mapsplaces.ConnectionDetector;
import com.test.weeklly.mapsplaces.GPSTracker;
import com.test.weeklly.mapsplaces.MainActivity;
import com.test.weeklly.mapsplaces.PlacesMapActivity;
import com.test.weeklly.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.provider.CalendarContract.Attendees;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


public class EventDesc extends Activity {
	GPSTracker gps; 
	ProgressDialog pDialog;
	
	Button GetDirec,accept,maybe,decline,suggestions;
	TextView purpose,Desc,Venue,Date,Time,title;
	HashMap<String, String> map;
	private static String ID = "id";
	private static String GID = "gid";
	private static String NAME = "name";
	private static String DATE = "date";
	private static String DESC = "description";
	private static String VENUE = "venue"; 
	private static String INVITED = "invited";
	private static String ITEM = "item";
	private static String STATUS = "status";
	private static String RESP= "response";
	private static String LAT="lat";
	private static String LONG="long";
	private static String declined = "3";
    private static String accepted = "1";
    private static String may= "2";
    private static String pending = "0";
	String slat,slong,dlat,dlong;
	ArrayList<HashMap<String, String>> contactItems = new ArrayList<HashMap<String,String>>();
	
	Bundle bun;
	ListView contactlist;
	//private String contacts[]={"Rahul","Akshay","Venkat","Sunil","Dileep"};
	private ArrayList<String> contacts = new ArrayList<String>();
	@SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.eventdesc);
        
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
        bun=getIntent().getExtras();
        contactlist=(ListView)findViewById(R.id.invitedcontactlist);

        suggestions=(Button)findViewById(R.id.InvitedSuggestions);
        GetDirec=(Button)findViewById(R.id.Getdirections);
        accept=(Button)findViewById(R.id.accept);
        maybe=(Button)findViewById(R.id.maybe);
        decline=(Button)findViewById(R.id.decline);
        purpose=(TextView)findViewById(R.id.purposetv);
        Desc=(TextView)findViewById(R.id.Desctv);
        Venue=(TextView)findViewById(R.id.Venuetv);
        Date=(TextView)findViewById(R.id.datetv);
        Time=(TextView)findViewById(R.id.timetv);
        title=(TextView)findViewById(R.id.titlepurposetv);
        title.setText(bun.getString(NAME));
        purpose.setText(bun.getString(NAME));
        Desc.setText(bun.getString(DESC));
        Venue.setText(bun.getString(VENUE));
        dlat=bun.getString(LAT);
        dlong=bun.getString(LONG);
       
        gps = new GPSTracker(this);

		// check if GPS location can get
		if (gps.canGetLocation()) {
			Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
		} else {
			// Can't get user's current location
			AlertDialogManager gpsalert=new AlertDialogManager();
			gpsalert.showAlertDialog(EventDesc.this, "GPS Status",
					"Couldn't get location information. Please enable GPS",
					false);
			// stop executing code by return
			return;
		}
        try{
        slat=""+gps.getLatitude();
		slong=""+gps.getLongitude();
        }
        catch(NullPointerException e)
        {
        	e.printStackTrace();
        }
		//Toast.makeText(EventDesc.this,""+dlat+"\n"+dlong,Toast.LENGTH_LONG).show();
		//Toast.makeText(EventDesc.this,""+slat+"\n"+slong,Toast.LENGTH_LONG).show();
		
			
        String datetime=bun.getString(DATE);
        String date=datetime.substring(0,10);
        String time=datetime.substring(11,16);
        Date.setText(date);
        Time.setText(time);
      //  String invited=bun.getString(INVITED);
        
        //for (String retval: invited.split(",",1000)){
        	//contacts.add(prefs.getString(retval,"no name"));

        //} 
        atendingUpdate();
       // contactlist.setAdapter(new ArrayAdapter<String>(this,android.R.layout. , contacts));
        suggestions.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i=new Intent(EventDesc.this,suggestions.class);
				i.putExtra(NAME, bun.getString(NAME));
				i.putExtra(ID,bun.getString(ID));
				startActivity(i);
				
			}
        	
        });
        GetDirec.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try{
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
						
							  Uri.parse("http://maps.google.com/maps?saddr="+slat+","+slong+"&daddr="+dlat+","+dlong));
					intent.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
					startActivity(intent);
					
			return;
				}catch(NullPointerException e)
				{
					e.printStackTrace();
				}
			}
        	
        });
        
        accept.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				accept.setText("PROCESSING");
				 InternetAlertDialogManager alert=new InternetAlertDialogManager();
			    	ConnectionDetector cd =new ConnectionDetector(EventDesc.this);
			        boolean isInternetPresent = cd.isConnectingToInternet();
					while(!isInternetPresent) {
						// Internet Connection is not present
						alert.showNoConnectionDialog(EventDesc.this);
						// stop executing code by return
						return;
					}
				pDialog = new ProgressDialog(EventDesc.this);
				pDialog.setMessage(Html.fromHtml("<b>Updating</b><br/>status..."));
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				
				pDialog.show();
				
				final SharedPreferences app_preferences=getSharedPreferences("userdata",0);
		         String g_id = app_preferences.getString("g_id",null);
		         String id = bun.getString(ID);
				Constants cons = new Constants();
				String status_url = cons.ip + "WeeklyService/status?event_id="+id+"&invited_id="+g_id+"&status="+accepted;
				try {
				JSONObject json = JSONfunctions.getJSONfromURL(status_url);
				//Toast.makeText(EventDesc.this,g_id + "\n" + id,Toast.LENGTH_LONG).show();
				//accept.setText(g_id + "\n" + id+ "\n" + json.toString());
				
				JSONObject item = json.getJSONObject(ITEM);
				String resp;
				
					resp = item.getString(RESP);
				
				if(resp.equals("1")){

					accept.setAlpha((float) 0.5);
					accept.setClickable(false);
					maybe.setAlpha((float) 1);
					maybe.setClickable(true);
					decline.setClickable(true);
					decline.setAlpha((float) 1);
				}
				atendingUpdate();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				pDialog.dismiss();
				accept.setText("ACCEPT");
			}
        	
        });
        
        maybe.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				maybe.setText("PROCESSING");
				 InternetAlertDialogManager alert=new InternetAlertDialogManager();
			    	ConnectionDetector cd =new ConnectionDetector(EventDesc.this);
			        boolean isInternetPresent = cd.isConnectingToInternet();
					while(!isInternetPresent) {
						// Internet Connection is not present
						alert.showNoConnectionDialog(EventDesc.this);
						// stop executing code by return
						return;
					}
				pDialog = new ProgressDialog(EventDesc.this);
				pDialog.setMessage(Html.fromHtml("<b>Updating</b><br/>status..."));
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				
				pDialog.show();
				final SharedPreferences app_preferences=getSharedPreferences("userdata",0);
		         String g_id = app_preferences.getString("g_id",null);
		         String id = bun.getString(ID);
				Constants cons = new Constants();
				String status_url = cons.ip + "WeeklyService/status?event_id="+id+"&invited_id="+g_id+"&status="+may;
				try{
				JSONObject json = JSONfunctions.getJSONfromURL(status_url);
				//Toast.makeText(EventDesc.this,g_id + "\n" + id,Toast.LENGTH_LONG).show();
				//accept.setText(g_id + "\n" + id+ "\n" + json.toString());
				JSONObject item = json.getJSONObject(ITEM);
				String resp;
				
					resp = item.getString(RESP);
				
				if(resp.equals("2")){

					maybe.setAlpha((float) 0.5);
					maybe.setClickable(false);
					accept.setAlpha((float) 1);
					accept.setClickable(true);
					decline.setAlpha((float) 1);
					decline.setClickable(true);
				}
				atendingUpdate();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				pDialog.dismiss();
				maybe.setText("MAY BE");
				}
			}
        	
        );
        
        decline.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				decline.setText("PROCESSING");
				 InternetAlertDialogManager alert=new InternetAlertDialogManager();
			    	ConnectionDetector cd =new ConnectionDetector(EventDesc.this);
			        boolean isInternetPresent = cd.isConnectingToInternet();
					while(!isInternetPresent) {
						// Internet Connection is not present
						alert.showNoConnectionDialog(EventDesc.this);
						// stop executing code by return
						return;
					}
					
							// TODO Auto-generated method stub
							pDialog = new ProgressDialog(EventDesc.this);
							pDialog.setMessage(Html.fromHtml("<b>Updating</b><br/>status..."));
							pDialog.setIndeterminate(false);
							pDialog.setCancelable(false);
							
							pDialog.show();
							final SharedPreferences app_preferences=getSharedPreferences("userdata",0);
					         String g_id = app_preferences.getString("g_id",null);
					         String id = bun.getString(ID);
							Constants cons = new Constants();
							String status_url = cons.ip + "WeeklyService/status?event_id="+id+"&invited_id="+g_id+"&status="+declined;
							try{
							JSONObject json = JSONfunctions.getJSONfromURL(status_url);
							//Toast.makeText(EventDesc.this,g_id + "\n" + id,Toast.LENGTH_LONG).show();
							//accept.setText(g_id + "\n" + id+ "\n" + json.toString());
							JSONObject item = json.getJSONObject(ITEM);
							String resp;
							
								resp = item.getString(RESP);
							
							if(resp.equals("3")){

								decline.setAlpha((float) 0.5);
								decline.setClickable(false);
								accept.setAlpha((float) 1);
								accept.setClickable(true);
								maybe.setAlpha((float) 1);
								maybe.setClickable(true);
								
							}
							atendingUpdate();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
							pDialog.dismiss();
							decline.setText("DECLINE");
					
				
			}
			}
        	
        );
        
	}
	
    private String getStatus(String code){
		
		if(code.equals(accepted))
			return "accepted";
		else if(code.equals(may))
			return "maybe";
		else if(code.equals(declined)) 
				return "Declined";
		else
			return "pending";
    }

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		
	}
    
    protected void atendingUpdate(){
        Constants cons = new Constants();
        String getinfo_url= cons.ip+"WeeklyService/attending?event_id="+bun.getString(ID);
        contactItems.clear();
		
		JSONObject json = JSONfunctions.getJSONfromURL(getinfo_url);
		final SharedPreferences app_preferences=getSharedPreferences("userdata",0);
		
		String g_id = app_preferences.getString("g_id",null);
		JSONArray item;
		try {
			 InternetAlertDialogManager alert=new InternetAlertDialogManager();
		    	ConnectionDetector cd =new ConnectionDetector(EventDesc.this);
		        boolean isInternetPresent = cd.isConnectingToInternet();
				while(!isInternetPresent) {
					// Internet Connection is not present
					alert.showNoConnectionDialog(EventDesc.this);
					// stop executing code by return
					return;
				}
			item = json.getJSONArray(ITEM);
			contacts.clear();
			for (int i=0; i < item.length(); i++) {
				JSONObject row = item.getJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				
				String name = row.getString(NAME);
				String gid = row.getString(GID);
				String status = row.getString(STATUS);
				map.put("person_name", row.getString(NAME));
				map.put("status",getStatus(row.getString(STATUS)));
				contactItems.add(map);
				contacts.add(name + "\n" + getStatus(status));
				//Toast.makeText(EventDesc.this, row + " " + status,Toast.LENGTH_LONG).show();
				//String g_id=row.getString(ID);
				if(g_id.equals(gid)){
					
					if(status.equals("1")){
						accept.setAlpha((float) 0.5);
						accept.setClickable(false);
						maybe.setAlpha((float) 1);
						maybe.setClickable(true);
						decline.setClickable(true);
						decline.setAlpha((float) 1);
				}else if(status.equals("2")){

					maybe.setAlpha((float) 0.5);
					maybe.setClickable(false);
					accept.setAlpha((float) 1);
					accept.setClickable(true);
					decline.setAlpha((float) 1);
					decline.setClickable(true);
					
					}else if(status.equals("3")){

						decline.setAlpha((float) 0.5);
						decline.setClickable(false);
						accept.setAlpha((float) 1);
						accept.setClickable(true);
						maybe.setAlpha((float) 1);
						maybe.setClickable(true);
					}
						
					
				}
				
			}
			//Toast.makeText(EventDesc.this, item.toString(),Toast.LENGTH_LONG).show();
			ListAdapter adapter = new SimpleAdapter(EventDesc.this, contactItems,
		               R.layout.invitedperson,
		               new String[] { "person_name", "status"}, new int[] {
		                       R.id.personname, R.id.status});
			
	        contactlist.setAdapter(adapter);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
	

