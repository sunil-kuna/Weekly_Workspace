package com.test.weeklly.dummyui;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.HashBiMap;
import com.test.weeklly.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SuggestionsStaggeredAdapter extends ArrayAdapter<HashMap<String, String>> {
	private static String NAME = "name";
	private static String DATE = "datetime";
	private static String COMMENT = "comment";
	ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String, String>>();
	int textViewResourceId;
	Context mcontext;
	public SuggestionsStaggeredAdapter(Context context, int textViewResourceId,
			ArrayList<HashMap<String, String>> ls) {
		super(context, textViewResourceId, ls);
		data=ls;
		mcontext=context;
		this.textViewResourceId=textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			
			LayoutInflater layoutInflator = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflator.inflate(textViewResourceId,
					null);
			
			holder = new ViewHolder();
			holder.date=(TextView)convertView.findViewById(R.id.cm_date_tv);
			holder.title=(TextView)convertView.findViewById(R.id.cm_name_tv);
			holder.comment=(TextView)convertView.findViewById(R.id.comment);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();

			
			
			String date = data.get(position).get(DATE);
			String title = data.get(position).get(NAME);
			String commen = data.get(position).get(COMMENT);
			
			holder.date.setText(date);
			holder.title.setText(title);
			holder.comment.setText(commen);
			 Typeface font = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/HAMBH___.ttf");  
		   //    holder.title.setTypeface(font); 
			
		

		

		return convertView;
	}

	static class ViewHolder {
		TextView date,title,comment;
	}
}
