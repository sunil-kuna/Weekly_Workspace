
package com.test.weeklly.dummyui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidhive.jsonparsing.JSONfunctions;
import com.test.weeklly.mapsplaces.ConnectionDetector;
import com.test.weeklly.R;



public class suggestions extends Activity {
	//ListView chatlist;
	EditText textet;
	Button sendbt;
	ArrayList<HashMap<String, String>> chat=new ArrayList<HashMap<String, String>>(); ;
	Constants cons = new Constants();
	 ProgressDialog pDialog;
	 Bundle bun;
	 private static String ID = "id";
		private static String GID = "gid";
		private static String NAME = "name";
		private static String DATE = "datetime";
		private static String COMMENT = "comment";
		
		private static String DESC = "description";
		private static String VENUE = "venue"; 
		private static String INVITED = "invited";
		private static String ITEM = "item";
		private static String RESP= "response";
		 SharedPreferences app_preferences;
        String g_id ;
        ListView gridView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suggestions);
		 gridView = (ListView) findViewById(R.id.chatlist);
	   		int margin = getResources().getDimensionPixelSize(R.dimen.chatmargin);
	   		gridView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);//.setItemMargin(margin); // set the GridView margin
	   		gridView.setPadding(margin, 0, margin, 0);
		//chatlist=(ListView)findViewById(R.id.chatlist);
		textet=(EditText)findViewById(R.id.texttosend);
		
		sendbt=(Button)findViewById(R.id.sendText);
		bun=getIntent().getExtras();
		TextView title=(TextView)findViewById(R.id.titlechattv);
		title.setText(bun.getString(NAME));
		app_preferences=getSharedPreferences("userdata",0);
        g_id = app_preferences.getString("g_id",null);
      
		
		
		sendbt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(textet.getText().toString()!=""){
				new SendChat().execute();
				new LoadComments().execute();
				}
				else
					Toast.makeText(suggestions.this,"Enter some Text", Toast.LENGTH_SHORT).show();
				
			}
			
		});
	}

	  @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		new LoadComments().execute();
	  }
	  
	class LoadComments extends AsyncTask<String, String, String> {

		  	@Override
			protected void onPreExecute() {
		  		InternetAlertDialogManager alert=new InternetAlertDialogManager();
		    	ConnectionDetector cd =new ConnectionDetector(suggestions.this);
		        boolean isInternetPresent = cd.isConnectingToInternet();
				while(!isInternetPresent) {
					// Internet Connection is not present
					alert.showNoConnectionDialog(suggestions.this);
					// stop executing code by return
					return;
				}
				super.onPreExecute();
				pDialog = new ProgressDialog(suggestions.this);
				pDialog.setMessage(Html.fromHtml("<b>Loading....</b>"));
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
			}
	    	
		private void chatUpdate(){
			try{
				
				
				String chat_url = cons.ip + "WeeklyService/getcomment?event_id="+bun.getString(ID);
				JSONObject json = JSONfunctions.getJSONfromURL(chat_url);
				JSONArray items = json.getJSONArray(ITEM);
				chat.clear();
				for(int i = 0; i < items.length() ; i++){
					JSONObject row = items.getJSONObject(i);
					HashMap<String, String> chatHash = new HashMap<String, String>();
					chatHash.put(NAME, row.getString(NAME));
					chatHash.put(COMMENT, row.getString(COMMENT));
					chatHash.put(DATE, row.getString(DATE));
					chat.add(chatHash);
				}
		//				chat.add("SImply");
			}catch(JSONException e){
				e.printStackTrace();
			}
			
			
		}
		  
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			chatUpdate();

	       return null;
		}
		
		protected void onPostExecute(String file_url) {
			
			pDialog.dismiss();
			SuggestionsStaggeredAdapter adapter = new SuggestionsStaggeredAdapter(suggestions.this, R.layout.chatcard, chat);
	 		gridView.setAdapter(adapter);
	 		adapter.notifyDataSetChanged();
	 		gridView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	      //  gridView.setAdapter(new ArrayAdapter<HashMap<String, String>>(suggestions.this,R.layout.chatcard , chat));    

		}
	  
	  }
	class SendChat extends AsyncTask<String, String, String> {

	  	@Override
		protected void onPreExecute() {
			super.onPreExecute();
		/*	pDialog = new ProgressDialog(suggestions.this);
			pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			*/
		}
    	
	private void SendComment(){
	
		Calendar c=Calendar.getInstance();
		String datetime="";
		int min=c.get(Calendar.MINUTE);
    	int hour=c.get(Calendar.HOUR_OF_DAY);
    	int selectedday=c.get(Calendar.DAY_OF_MONTH);
    	int selectedmonth=c.get(Calendar.MONTH);
    	int selectedyear=c.get(Calendar.YEAR);
		if(selectedday<10)
		datetime+="0";
		datetime+=""+selectedday;
		
		datetime+="/";
		
		selectedmonth++;
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
		     
			String chat_url = cons.ip + "WeeklyService/putcomment?event_id="+bun.getString(ID)+"&gid="+g_id+"&comment="+textet.getText().toString()+"&datetime="+datetime;
			JSONObject json = JSONfunctions.getJSONfromURL(chat_url);
			
			
		
		
		
	}
	  
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		SendComment();

       return null;
	}
	
	protected void onPostExecute(String file_url) {
		
	//	pDialog.dismiss();
	/*	SuggestionsStaggeredAdapter adapter = new SuggestionsStaggeredAdapter(suggestions.this, R.layout.chatcard, chat);
 		gridView.setAdapter(adapter);
 		adapter.notifyDataSetChanged();*/
		textet.setText("");
	}
  
  }
		    
	
}
