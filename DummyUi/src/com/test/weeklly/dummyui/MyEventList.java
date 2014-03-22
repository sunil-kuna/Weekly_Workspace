package com.test.weeklly.dummyui;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidhive.jsonparsing.JSONfunctions;
import com.test.weeklly.mapsplaces.ConnectionDetector;
import com.test.weeklly.mapsplaces.MainActivity;
import com.test.weeklly.R;

public class MyEventList extends SherlockFragment {

 

 ProgressDialog pDialog;
	
 static boolean eventsexists=true;
 private String lv_arr[]={"Lunch @BeijingBites","Iron Man @GOpalan","Hangover||| @Forum","FnF6 @Gopalan"};
 String invited;
    private static String CREATOR= "fuckyea";
 	private static String ID = "id";
	private static String GID = "gid";
	private static String NAME = "name";
	private static String DATE = "date";
	private static String DESC = "description";
	private static String VENUE = "venue"; 
	private static String INVITED = "invited";
	private static String ITEM = "item";
	private static String RESP= "response";
	ListView gridView;
 private ArrayList<String> dates = new ArrayList<String>();
 private ArrayList<String> creators = new ArrayList<String>();
 private ArrayList<String> titles = new ArrayList<String>();
 HashMap<String, String> data = new HashMap<String, String>();
 ArrayList<HashMap<String, String>> myevents = new ArrayList<HashMap<String,String>>();
 
 HashMap<String, String> card = new HashMap<String, String>();
 ArrayList<HashMap<String, String>> cardlist = new ArrayList<HashMap<String,String>>();
 View rootView ;
 /** Called when the activity is first created. */
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	 rootView = inflater.inflate(R.layout.myeventlist,
				container, false);
    	 gridView = (ListView) this.rootView.findViewById(R.id.mystaggeredGridView);
  		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
  		//gridView.setItemMargin(margin); // set the GridView margin
  		gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 
  		
  	  ImageButton plus=(ImageButton)rootView.findViewById(R.id.myplus);
  	  plus.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent in =new Intent(rootView.getContext(),EventCreation.class);
			in.putExtra("src","MyEventList");
			rootView.getContext().startActivity(in);
		}
  		  
  	  });
    	 Button refresh_bt = (Button)rootView.findViewById(R.id.refresh_my_list);
        
        refresh_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new LoadFeeds().execute();
			       
			}
		});
        	
        
        
        InternetAlertDialogManager alert=new InternetAlertDialogManager();
    	ConnectionDetector cd =new ConnectionDetector(rootView.getContext());
        boolean isInternetPresent = cd.isConnectingToInternet();
		while(!isInternetPresent) {
			// Internet Connection is not present
			alert.showNoConnectionDialog(rootView.getContext());
			// stop executing code by return
		
		}
       
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 

       
		new LoadFeeds().execute();
		
  
		return rootView;
        }
    
    protected void feedsUpdate(){
    	cardlist.clear();
    	Constants cons = new Constants();
    	SharedPreferences prefs=rootView.getContext().getSharedPreferences("userdata",0);
    	String gid = prefs.getString("g_id","");
    	String feedsUrl = cons.ip + "WeeklyService/feedsupdate?gid="+gid;
    	try {
    	
    	JSONObject json = JSONfunctions.getJSONfromURL(feedsUrl);
    	
    	//lv_arr[3] = json.toString();
    	//ls.clear();
    	
    
			JSONArray items =  json.getJSONArray(ITEM);
			//Toast.makeText(rootView.getContext(),items.toString(),Toast.LENGTH_SHORT).show();
	    	if(items.length()>0)
			for (int i = items.length()-1 ; i >= 0; i--) {
			    JSONObject row = items.getJSONObject(i);
			   // Toast.makeText(rootView.getContext(),row.toString(),Toast.LENGTH_SHORT).show();
		    	card=new HashMap<String, String>();
			    data=new HashMap<String,String>();
			    String date = row.getString(DATE);
			   // Toast.makeText(rootView.getContext(),date,Toast.LENGTH_SHORT).show();
		    	data.put(DATE,date);
		    	card.put("date"," @ "+date.substring(11)+"  on "+date.substring(0,5));
		    	
			    String name = row.getString(NAME);
			  //  Toast.makeText(rootView.getContext(),name,Toast.LENGTH_SHORT).show();
		    	data.put(NAME, name);
		    	card.put("title", name);
			    String id = row.getString(ID);
			    data.put(ID, id );
			    
			    String g_id = row.getString(GID);
			    data.put(GID, g_id );
			    
			    String desc = row.getString(DESC);
			    data.put(DESC, desc );
			    
			    String venue = row.getString(VENUE);
			    data.put(VENUE, venue );
			    card.put("venue"," in "+venue);
			  /*  cardcreator=row.getString(CREATOR);
			    data.put(CREATOR, cardcreator);*/	
			    card.put("creator", "");		    
			   
			  //  invited = row.getString(INVITED);
			//    Toast.makeText(rootView.getContext(),invited,Toast.LENGTH_SHORT).show();
			 //   data.put(INVITED, invited);
			    
			    String content = name + "\n" + date + "\n";// + invited;
			    //Toast.makeText(rootView.getContext(),content,Toast.LENGTH_SHORT).show();
		    	
			    myevents.add(items.length()-1-i, data);
			    cardlist.add(card);
			    eventsexists=true;
			}
	    	else
	    	{
	    		card=new HashMap<String, String>();
	    		eventsexists=false;  
	    		card.put("title", "No events");
				card.put("date", "");
				card.put("creator", "");
				card.put("venue","");
				cardlist.add(card);
	    	}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			card.put("title", "No events");
			card.put("date", "");
			card.put("creator", "");
			card.put("venue","");
			cardlist.add(card);
			e.printStackTrace();
			
		}       
    	
    	if(eventsexists)
    	       gridView.setOnItemClickListener(new OnItemClickListener()
    	       {

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View view,
    					int position, long id) {
    				// TODO Auto-generated method stub
    				 Intent newActivity = new Intent(view.getContext(),myEventDesc.class);    

    			       // Toast.makeText(rootView.getContext(),""+position,Toast.LENGTH_SHORT).show();
    			       HashMap<String, String> map =new HashMap<String, String>();
    			    		   map=myevents.get(position);
    			         
    			          newActivity.putExtra(GID, myevents.get(position).get(GID));
    			          newActivity.putExtra(NAME ,myevents.get(position).get(NAME));
    			          newActivity.putExtra(DATE,myevents.get(position).get(DATE));
    			          newActivity.putExtra(DESC,myevents.get(position).get(DESC));
    			          newActivity.putExtra(VENUE,myevents.get(position).get(VENUE));
    			          newActivity.putExtra(ID, myevents.get(position).get(ID));
    			         // Toast.makeText(rootView.getContext(),myevents.get(position).get(ID),Toast.LENGTH_SHORT).show();  
    			         // newActivity.putExtra(INVITED,myevents.get(position).get(INVITED));
    			         startActivity(newActivity);
    			    
    			}
    	    	   
    	       });

        
    }
    
    class LoadFeeds extends AsyncTask<String, String, String> {

    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(rootView.getContext());
			pDialog.setMessage(Html.fromHtml("<b>Feeds</b><br/>Loading Feeds..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
    	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			feedsUpdate();
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			
			pDialog.dismiss();
			
			myStaggeredAdapter adapter = new myStaggeredAdapter(rootView.getContext(), R.layout.mycard, cardlist);
	 		gridView.setAdapter(adapter);
	 		adapter.notifyDataSetChanged();

		}

    }


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}



}