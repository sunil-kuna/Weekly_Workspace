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


public class StaggeredAdapter extends ArrayAdapter<HashMap<String, String>> {

	ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String, String>>();
int textViewResourceId;
	public StaggeredAdapter(Context context, int textViewResourceId,
			ArrayList<HashMap<String, String>> ls) {
		super(context, textViewResourceId, ls);
		data=ls;
		this.textViewResourceId=textViewResourceId;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			
			
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(textViewResourceId,
					null);
			
			holder = new ViewHolder();
			holder.date=(TextView)convertView.findViewById(R.id.carddate);
			holder.title=(TextView)convertView.findViewById(R.id.cardtitle);
			holder.by=(TextView)convertView.findViewById(R.id.cardby);
			
			holder.venue=(TextView)convertView.findViewById(R.id.cardvenue);
			
			holder.color=(TextView)convertView.findViewById(R.id.colorsegment);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		String date = data.get(position).get("date");
		String title = data.get(position).get("title");
		String venue=data.get(position).get("venue");
		String creator = data.get(position).get("creator");
		holder.date.setText(date);
			holder.title.setText(title);
			holder.by.setText(creator);
			if(venue.length()>=20)
				holder.venue.setText(venue.substring(0,20));
				else
				holder.venue.setText(venue);
			int c=R.color.c1;
			int x=position%4;
			switch(x)
			{
			case 0:
				c=R.color.c0;
				break;
			case 1:
				c=R.color.c1;
				break;
			case 2:
				c=R.color.c2;
				break;
			case 3:
				c=R.color.c3;
				break;
			
				
			}

			holder.color.setBackgroundResource(c);
			 Typeface font = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/leaguegothic.otf");  
		     holder.title.setTypeface(font); 
		     holder.date.setTypeface(font);
		     holder.venue.setTypeface(font);
		     holder.by.setTypeface(font);
			holder.by.setText("  -invited by "+creator);
			if(creator.equals(""))
			holder.by.setVisibility(View.INVISIBLE);
			convertView.setTag(holder);
		

		


		return convertView;
	}

	static class ViewHolder {
		TextView date,title,by,venue,color;
	}
}
