package com.test.weeklly.dummyui;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidhive.jsonparsing.JSONfunctions;
import com.facebook.FacebookException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.test.weeklly.mapsplaces.ConnectionDetector;
import com.test.weeklly.mapsplaces.MainActivity;
import com.test.weeklly.mapsplaces.Place;
import com.test.weeklly.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
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

public class myEventDesc extends Activity{
	String invited="";
	Button Edit,suggestions,share;//,fbshare;
	ListView mycontactslist;
	TextView purpose,Desc,Venue,Date,Time,title;
	HashMap<String, String> map;
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
	
	private static String declined = "3";
    private static String accepted = "1";
    private static String may= "2";
    private static String pending = "0";
    String invitedcontacts= "";
    ArrayList<HashMap<String, String>> contactItems = new ArrayList<HashMap<String,String>>();
	
    Bundle bun;
	private ArrayList<String> contacts = new ArrayList<String>();
	LoginButton authButton;  
	 SharedPreferences app_preferences;
	
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myeventdesc);
        bun=getIntent().getExtras();
        app_preferences=getSharedPreferences("userdata",0);
        purpose=(TextView)findViewById(R.id.mypurposetv);
        Desc=(TextView)findViewById(R.id.myDesctv);
        Venue=(TextView)findViewById(R.id.myVenuetv);
        Date=(TextView)findViewById(R.id.mydatetv);
        Time=(TextView)findViewById(R.id.mytimetv);
        title=(TextView)findViewById(R.id.titlemypurposetv);
        share=(Button)findViewById(R.id.myshare);
      //  fbshare=(Button)findViewById(R.id.myfbshare);
     //   fbshare.setVisibility(View.INVISIBLE);
     //   gshare=(Button)findViewById(R.id.myGshare);
        mycontactslist=(ListView)findViewById(R.id.MyContactlist);
        Edit=(Button)findViewById(R.id.editmyevent);
      //getalllocations=(Button)findViewById(R.id.getlocations);
        suggestions=(Button)findViewById(R.id.mysuggestions);
        
       
        purpose.setText(bun.getString(NAME));
        title.setText(bun.getString(NAME));
        
        String datetime=bun.getString(DATE);
        Desc.setText(bun.getString(DESC));
        Venue.setText(bun.getString(VENUE));
       //bun.getString(INVITED);
      // Toast.makeText(myEventDesc.this,invited,Toast.LENGTH_LONG).show();
       if(datetime!="")
        {String date=datetime.substring(0,10);
        String time=datetime.substring(11,16);
       Date.setText(date);
        Time.setText(time);
        }
        Constants cons = new Constants();
        String getinfo_url= cons.ip+"WeeklyService/attending?event_id="+bun.getString(ID);
       //Toast.makeText(myEventDesc.this, bun.getString(ID), Toast.LENGTH_LONG).show();
       try {
    	   InternetAlertDialogManager alert=new InternetAlertDialogManager();
       	ConnectionDetector cd =new ConnectionDetector(myEventDesc.this);
           boolean isInternetPresent = cd.isConnectingToInternet();
   		while(!isInternetPresent) {
   			// Internet Connection is not present
   			alert.showNoConnectionDialog(myEventDesc.this);
   			// stop executing code by return
   			return;
   		}
		JSONObject json = JSONfunctions.getJSONfromURL(getinfo_url);
		// Toast.makeText(myEventDesc.this, json.toString(), Toast.LENGTH_LONG).show();
		// Desc.setText(json.toString());
	        
		JSONArray item;
		
			item = json.getJSONArray(ITEM);
			if(item.length()>0)
			for (int i=0; i < item.length(); i++) {
				JSONObject row = item.getJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				
				String name = row.getString(NAME);
				invitedcontacts+=name+",";
				String status = row.getString(STATUS);
				contacts.add(name + "\n" + getStatus(status));
					map.put("person_name", row.getString(NAME));
					map.put("status",getStatus(row.getString(STATUS)));
					// Place name
					
					contactItems.add(map);
				}
			}
		//	Toast.makeText(myEventDesc.this, item.toString(),Toast.LENGTH_LONG).show();
			
		
		 catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(myEventDesc.this, "No Invitees", Toast.LENGTH_LONG).show();
		} 
       ListAdapter adapter=null;
       if(!contactItems.isEmpty())
      adapter = new SimpleAdapter(myEventDesc.this, contactItems,
               R.layout.invitedperson,
               new String[] { "person_name", "status"}, new int[] {
                       R.id.personname, R.id.status});
	
       
        mycontactslist.setAdapter(adapter);
       share.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(android.content.Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			String post=bun.getString(NAME);
			post+="\n"+bun.getString(DESC);
			post+="\n"+bun.getString(VENUE);
			post+="\n"+bun.getString(DATE);
			// Add data to the intent, the receiving app will decide what to do with it.
			intent.putExtra(Intent.EXTRA_SUBJECT,"DOWNLOAD WEEKLY APP TO GET INSTANT INVITATIONS AND ENJOY EASY EVENT ORGANISING" );
			intent.putExtra(Intent.EXTRA_TEXT, post);
			
			
			
			startActivity(Intent.createChooser(intent, "How do you want to share?"));

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
       
////////////////////     FB Begins       //////////////////////////


   //    uiHelper = new UiLifecycleHelper(myEventDesc.this, callback);
   //   uiHelper.onCreate(savedInstanceState);
      //   authButton = (LoginButton)findViewById(R.id.myauthButton);

        
///////////////////          FB Ends         //////////////////////////

        suggestions.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i=new Intent(myEventDesc.this,suggestions.class);
				i.putExtra(ID,bun.getString(ID));
				i.putExtra(NAME, bun.getString(NAME));
				startActivity(i);
				
			}
        	
        });
        Edit.setOnClickListener(new OnClickListener(){
        
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(myEventDesc.this,EventUpdate.class);
				i.putExtras(bun);
				i.putExtra(INVITED,invited);
				startActivityForResult(i,12);
			}
        	
        });

        


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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //uiHelper.onActivityResult(requestCode, resultCode, data);
    if(requestCode==12)
    if(resultCode==13)
    	finish();

    }
    /*
////////////////////////FB             ////////////////////

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
//Log.i(TAG, "Logged in...");
authButton.setVisibility(View.INVISIBLE);
fbshare.setVisibility(View.VISIBLE);
} else if (state.isClosed()) {
//Log.i(TAG, "Logged out...");
authButton.setVisibility(View.VISIBLE);
fbshare.setVisibility(View.INVISIBLE);
}
}


private void facebookFeedDialog() {
// Set the dialog parameters
Bundle params = new Bundle();
params.putString("name",app_preferences.getString("name",null));
params.putString("caption",bun.getString(NAME));
params.putString("description",bun.getString(DESC)+"\n venue -"+bun.getString(VENUE)+"\nalong with "+invitedcontacts+"\n @ "+Time.getText().toString() +"\n on "+Date.getText().toString());
params.putString("link", "http://www.foofys.com");
//params.putString("picture", "http://www.sugarmedia.com/nyccookbook/images/pizza.jpg");

// Invoke the dialog
WebDialog feedDialog = (
new WebDialog.FeedDialogBuilder(myEventDesc.this,
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
			Toast.makeText(myEventDesc.this,
					"Story published: "+postId,
					Toast.LENGTH_SHORT).show();
		}
	}
}

})
.build();
feedDialog.show();

}

@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	finish();
}

///////////////////////////            FB ENDS          ////////////////////////


*/




}


