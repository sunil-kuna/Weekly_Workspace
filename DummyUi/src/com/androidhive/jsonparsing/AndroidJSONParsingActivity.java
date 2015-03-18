package com.androidhive.jsonparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.test.weeklly.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidJSONParsingActivity extends ListActivity {

	// url to make request
	private static String url = "http://192.168.0.112:8080/WeeklyService/Signup?username=bullshit&gid=97532";
	private static String eventurl = "http://192.168.0.112:8080/WeeklyService/newevent?gid=420&title=hjsdcfahj&venue=ccda&datetime=05/06/1994%20:45";
	private static String infourl = "http://192.168.1.9:8080/WeeklyService/getInfo?gid=1234";
	
	// JS111ON Node names
	private static final String TAG_CONTACTS = "contacts";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_GENDER = "response";
	private static final String TAG_PHONE = "phone_no";
	private static final String TAG_PHONE_MOBILE = "mobile";
	private static final String TAG_PHONE_HOME = "home";
	private static final String TAG_PHONE_OFFICE = "office";
	private static final String TAG_ITEM = "item";
	// contacts JSONArray
	JSONArray contacts = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		
		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(infourl);
		
			
					
					JSONObject item;
					try {
						item = json.getJSONObject(TAG_ITEM);
					
					//JSONObject gid = item.getJSONObject("gid");
					String xyz=item.getString(TAG_GENDER);
					//String abc=item.getString(TAG_NAME);
					
					 Toast.makeText(AndroidJSONParsingActivity.this, json.toString() ,
								Toast.LENGTH_LONG).show();
			
				
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//item = json.getJSONObject("item");
				//if(json.isNull("item"))
	
		
			
			// Getting Array of Contacts
			/*contacts = json.getJSONArray(TAG_CONTACTS);
			
			// looping through All Contacts
			for(int i = 0; i < contacts.length(); i++){
				JSONObject c = contacts.getJSONObject(i);
				
				// Storing each json item in variable
				String id = c.getString(TAG_ID);
				String name = c.getString(TAG_NAME);
				String email = c.getString(TAG_EMAIL);
				String address = c.getString(TAG_ADDRESS);
				String gender = c.getString(TAG_GENDER);
				
				// Phone number is agin JSON Object
				JSONObject phone = c.getJSONObject(TAG_PHONE);
				String mobile = phone.getString(TAG_PHONE_MOBILE);
				String home = phone.getString(TAG_PHONE_HOME);
				String office = phone.getString(TAG_PHONE_OFFICE);
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				
				// adding each child node to HashMap key => value
				map.put(TAG_ID, id);
				map.put(TAG_NAME, name);
				map.put(TAG_EMAIL, email);
				map.put(TAG_PHONE_MOBILE, mobile);

				// adding HashList to ArrayList
				contactList.add(map);*/
			
		
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		/*ListAdapter adapter = new SimpleAdapter(this, contactList,
				R.layout.list_item,
				new String[] { TAG_NAME, TAG_EMAIL, TAG_PHONE_MOBILE }, new int[] {
						R.id.name, R.id.email, R.id.mobile });

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String cost = ((TextView) view.findViewById(R.id.email)).getText().toString();
				String description = ((TextView) view.findViewById(R.id.mobile)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(TAG_NAME, name);
				in.putExtra(TAG_EMAIL, cost);
				in.putExtra(TAG_PHONE_MOBILE, description);
				startActivity(in);

			}
		});*/
		
	}	

}