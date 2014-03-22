package com.test.weeklly.dummyui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidhive.jsonparsing.JSONfunctions;
import com.test.weeklly.gplus.SignInActivity;
import com.test.weeklly.mapsplaces.ConnectionDetector;
import com.test.weeklly.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class Contacts extends Activity {
private ListView mainListView;
ProgressDialog pDialog;


private Cursor mCursor;

JSONObject phone_json=null;
private static String phone_url;// = "http://192.168.1.10:8080/WeeklyService/contactverification?phone_no=";
private static String ID = "id";
private static String GID = "gid";
private static String USERNAME = "name";
private static String PHONE_NO = "phone_no";
private static String ITEM = "item";
private static String RESP= "response";
String sb = "",names="";
private contactadapter mListAdapter;
private ListView mPersonListView;
private ArrayList<String> mListItems,Gids;
Button done,update;
String str;
HashMap<String, String> contacts = new HashMap<String, String>();
Bundle bun;
String invitedconts;

/** Called when the activity is first created. */
@SuppressLint("NewApi")
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Constants cons=new Constants();
    bun=getIntent().getExtras();
    invitedconts=bun.getString("invitedconts");
    
    phone_url= cons.ip+"WeeklyService/contactverification?phone_no=";
    setContentView(R.layout.contacts);
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	StrictMode.setThreadPolicy(policy); 
	 mListItems = new ArrayList<String>();
	 Gids=new ArrayList<String>();
	 mListAdapter = new contactadapter<String>(this, R.layout.contactitem,
             mListItems);
     mPersonListView = (ListView) findViewById(R.id.contacts_person_list);
     mPersonListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
     mPersonListView.setAdapter(mListAdapter);
     
     loadContacts();
     
     done=(Button)findViewById(R.id.contacts_done);
     
     done.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str=null;
			SparseBooleanArray checked = mPersonListView.getCheckedItemPositions();
			int size = checked.size(); // number of name-value pairs in the array
			names="";
			int i;
			
			for (i = 0; i < size; i++) {
			    int key = checked.keyAt(i);
			    boolean value = checked.get(key);
			    if (value){
			    	if(!Gids.get(key).equals(null)){
			    	str+="\n"+Gids.get(key);
			    	names=mListItems.get(key);
			    	//done.setText(sb);
			    	sb+=Gids.get(key);
			    	break;
			    	}
			    	
			    	
			    }
			        //doSomethingWithSelectedIndex(key);
			}i++;
			for (; i < size; i++) {
			    int key = checked.keyAt(i);
			    boolean value = checked.get(key);
			    if (value){
			    	if(!Gids.get(key).equals(null)){
			    	str+="\n"+Gids.get(key);
			    	//done.setText(sb);
			    	sb+=",";
			    	sb+=Gids.get(key);
			    	names+=",";
			    	names+=mListItems.get(key);
			    	
			    	}
			    	
			    	
			    }
			        //doSomethingWithSelectedIndex(key);
			}
		
			Intent returnIntent = new Intent();
	    	 returnIntent.putExtra("result",sb.toString());
	    	 returnIntent.putExtra("invitedconts",sb.toString());
	    	 returnIntent.putExtra("invitednames", names);
	    	 setResult(RESULT_OK,returnIntent);     
	    	 finish();
		}
		
    	 
    	 
     });
     update=(Button)findViewById(R.id.contacts_update);
     
     
     update.setOnClickListener(new OnClickListener(){

    	 
		@Override
		public void onClick(View v) {
			InternetAlertDialogManager alert=new InternetAlertDialogManager();
	    	ConnectionDetector cd =new ConnectionDetector(Contacts.this);
	        boolean isInternetPresent = cd.isConnectingToInternet();
			while(!isInternetPresent) {
				// Internet Connection is not present
				alert.showNoConnectionDialog(Contacts.this);
				// stop executing code by return
				return;
			}
	       
			// TODO Auto-generated method stub
			  new UpdateContacts().execute();
			     
		}
    	 
     }
     );
  

    // Add Contact Class to the Arraylist

}

private void loadContacts(){
	SharedPreferences prefs = getSharedPreferences("Contacts", 0);
	
	 String str = null;
	 mListItems.clear();
	 int i=0;
	for( Entry<String, ?> entry : prefs.getAll().entrySet() ) {
	  
	  str+=entry.getValue().toString();
      mListItems.add(entry.getValue().toString());
      Gids.add(entry.getKey());
 	  mListAdapter.notifyDataSetChanged();
 	  if(!invitedconts.equals(""))
 	  for (String retval: invitedconts.split(",",1000))
 	  {
 	  					if(retval.equals(entry.getKey()))
 	  					{
 	  						mPersonListView.setItemChecked(i, true);	 
 	  					}
 			 
 	  			}
 	  	  i++;
	}
	
}

private void updateContacts(){
	 // Throw Query and fetch the contacts.
String phonenums="";
	SharedPreferences user=getSharedPreferences("userdata",0);
    SharedPreferences.Editor editor = getSharedPreferences("Contacts", 0).edit();
	Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};
    mCursor = getContentResolver().query(uri, projection, null, null, null);
    
    if (mCursor != null) {
        mCursor.moveToFirst();
        
        int j = 0;
        int i=0,nop=0;

       
        	
        	do {
        	String phone = mCursor.getString(mCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        	phone = phone.replaceAll("[^0-9.,]+","");
            if(phone.length()>10)
            	phone=phone.substring(phone.length()-10);
            	//Toast.makeText(Contacts.this,phone,Toast.LENGTH_SHORT).show();
            if(!phone.equals(""))
            	{phonenums+=phone+",";
            	nop++;}

        	}while(mCursor.moveToNext());
        	
		try{
    	 phone_json =  JSONfunctions.getJSONfromURL(phone_url+phonenums+"&nop="+nop);
    	//Toast.makeText(Contacts.this,phone_json.toString(),Toast.LENGTH_LONG).show();
    	JSONArray items = phone_json.getJSONArray(ITEM);
	
    	if(items.length()>0)
		for ( i = items.length()-1 ; i >= 0; i--) {
		    JSONObject item = new JSONObject();
		    item=items.getJSONObject(i);
		  
	
	String resp = item.getString(RESP);
	if(resp.equals("exsits")){
    	String phone_no = item.getString(PHONE_NO);
    	String name = item.getString(USERNAME);
    	String gid = item.getString(GID);
    	
    	 str+="\n"+name;
    	
        
       
        if(! user.getString("g_id", "").equals(gid)){
            if(name!=""){
            	//Toast.makeText(Contacts.this,gid,Toast.LENGTH_SHORT).show();
            	editor.putString(gid, name);
            	editor.commit();
            	
            }
        }
        
	}
		}
		}
		
        catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		//update.setText(phone_json.toString());
	    	
		}
        

    } 
    else 
    {
    	//update.setText(phone_json.toString());
        System.out.println("Cursor is NULL");
    }
    
}

class UpdateContacts extends AsyncTask<String, String, String> {

	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(Contacts.this);
		pDialog.setMessage(Html.fromHtml("<b>Update</b><br/>Loading Contacts...\nIt will take some minutes time"));
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		updateContacts();
		return null;
	}
	
	protected void onPostExecute(String file_url) {
		
		pDialog.dismiss();
		loadContacts();

	}

}

@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	Intent returnIntent = new Intent();
	 returnIntent.putExtra("result",invitedconts);
	 returnIntent.putExtra("invitedconts",invitedconts);
	 setResult(RESULT_OK,returnIntent);     
	 finish();
	
}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
super.onKeyDown(keyCode, event);
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		
    		Intent returnIntent = new Intent();
    		 returnIntent.putExtra("result",invitedconts);
    		 returnIntent.putExtra("invitedconts",invitedconts);
    		 setResult(RESULT_OK,returnIntent);     
    		 finish();
    		return true;
    	}
    	return false;
    }


}