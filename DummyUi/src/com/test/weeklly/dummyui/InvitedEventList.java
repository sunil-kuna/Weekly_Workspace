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
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidhive.jsonparsing.JSONfunctions;
import com.test.weeklly.gplus.SignInActivity;
import com.test.weeklly.mapsplaces.ConnectionDetector;
import com.test.weeklly.R;

public class InvitedEventList extends SherlockFragment {

 //private ListView lv1;
 static boolean eventsexists=true;
 private String lv_arr[]={"Lunch -Rahul","Iron Man -Akshay","Hangover||| -Venkat","Forum -Sunil","Gopalan -Dileep"};
 private static String ID = "id";
 private static String CREATOR= "fuckyea";
	private static String GID = "gid";
	private static String NAME = "name";
	private static String DATE = "date";
	private static String DESC = "description";
	private static String VENUE = "venue"; 
	private static String INVITED = "invited";
	private static String ITEM = "item";
	private static String RESP= "response";
	private static String LAT="lat";
	private static String LONG="long";
	ListView gridView;
	HashMap<String, String> card = new HashMap<String, String>();
	 ArrayList<HashMap<String, String>> cardlist = new ArrayList<HashMap<String,String>>();
	 private ArrayList<String> ls = new ArrayList<String>();  
	 HashMap<String, String> data = new HashMap<String, String>();
	 ArrayList<HashMap<String, String>> events = new ArrayList<HashMap<String,String>>();
	 
	 ProgressDialog pDialog;
		
	 View rootView ;
	 /** Called when the activity is first created. */
		 public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
		    	 rootView = inflater.inflate(R.layout.invitedevent_list,
						container, false);
		    	 gridView =  (ListView) this.rootView.findViewById(R.id.instaggeredGridView);
		   		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		   		//gridView.setItemMargin(margin); // set the GridView margin
		   		gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 
		 Button plus=(Button)rootView.findViewById(R.id.invitedplus);
		    	  plus.setOnClickListener(new OnClickListener(){

		  		@Override
		  		public void onClick(View v) {
		  			// TODO Auto-generated method stub
		  			Intent in =new Intent(rootView.getContext(),EventCreation.class);
		  			in.putExtra("src","InvitedEventList");
					rootView.getContext().startActivity(in);
		  		}
		    		  
		    	  });   		
		   	    
        Button refresh_bt = (Button)rootView.findViewById(R.id.refresh_invited_list);
        
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
		new LoadFeeds().execute();

      //  lv1=(ListView)rootView.findViewById(R.id.InvitedeventList);

        // By using setAdpater method in listview we an add string array in list.
        //feedsUpdate();
        //lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , lv_arr));
		
		
		return rootView;
    }
   protected void feedsUpdate(){
	   cardlist.clear();
    	Constants cons = new Constants();
    	SharedPreferences prefs=rootView.getContext().getSharedPreferences("userdata",0);
    	String gid = prefs.getString("g_id","");
    	String feedsUrl = cons.ip + "WeeklyService/invitedfeeds?gid="+gid;
    	
    	try {
    	JSONObject json = JSONfunctions.getJSONfromURL(feedsUrl);	
    	//Toast.makeText(InvitedEventList.this,json.toString(),Toast.LENGTH_SHORT).show();
    	
    	
    	//lv_arr[3] = json.toString();
    
    	
			JSONArray items =  json.getJSONArray(ITEM);
			if(items.length()>0){
			for (int i = items.length()-1 ; i >= 0; i--) {
				card=new HashMap<String, String>();
			    JSONObject row = items.getJSONObject(i);
			    data=new HashMap<String,String>();
			    String date = row.getString(DATE);
			    data.put(DATE, date);
			    card.put("date"," @ "+date.substring(11)+"  on "+date.substring(0,5));
		    	String name = row.getString(NAME);
			    data.put(NAME, name );
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
				
			   // String invited = row.getString(INVITED);
			   // data.put(INVITED, invited );
			    
			    String lat=row.getString(LAT);
			    data.put(LAT, lat);
			    
			    String lon = row.getString(LONG);
			    data.put(LONG, lon);
			    
			    String content = name + "\n" + date + "\n" ;//+ invited;
			    events.add(data);
			     String cardcreator=row.getString(CREATOR);
			    data.put(CREATOR, cardcreator);
			    card.put("creator", cardcreator);
			    
			    cardlist.add(card);
			    ls.add(content);
			    eventsexists=true;
			    
			}
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

    			public void onItemClick(AdapterView<?> arg0, View view,
    					int position, long id) {
    				// TODO Auto-generated method stub
    				Intent newActivity = new Intent(view.getContext(),EventDesc.class);     
    			      
    			       HashMap<String, String> map =new HashMap<String, String>();
    				   map= events.get(position);
    			         newActivity.putExtra(ID, map.get(ID));
    			         newActivity.putExtra(GID, map.get(GID));
    			         newActivity.putExtra(NAME ,map.get(NAME));
    			         newActivity.putExtra(DATE,map.get(DATE));
    			         newActivity.putExtra(DESC,map.get(DESC));
    			         newActivity.putExtra(VENUE,map.get(VENUE));
    			        // newActivity.putExtra(INVITED,map.get(INVITED));
    			         newActivity.putExtra(LAT, map.get(LAT));
    			         newActivity.putExtra(LONG, map.get(LONG));
    			         
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
	     //   lv1.setAdapter(new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1 , ls));    
			StaggeredAdapter adapter = new StaggeredAdapter(rootView.getContext(), R.layout.card, cardlist);
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