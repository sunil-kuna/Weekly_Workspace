/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.weeklly.gplus;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.test.weeklly.gplus.PlusClientFragment.OnSignedInListener;
import com.test.weeklly.R;

/**
 * Example of listing people through the PlusClient.
 */
public class ListPeopleActivity extends FragmentActivity implements OnSignedInListener,
        PlusClient.OnPeopleLoadedListener {

    private static final String TAG = ListPeopleActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PLUS_CLIENT_FRAGMENT = 0;

    private ArrayAdapter mListAdapter;
    private ListView mPersonListView;
    private ArrayList<String> mListItems;
    private PlusClientFragment mPlusClientFragment;
    Button done;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_list_activity);

        mListItems = new ArrayList<String>();
        mListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,
                mListItems);
        mPersonListView = (ListView) findViewById(R.id.person_list);
        mPersonListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mPersonListView.setAdapter(mListAdapter);
        mPlusClientFragment = PlusClientFragment.getPlusClientFragment(this,
                MomentUtil.VISIBLE_ACTIVITIES);
        done=(Button)findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str="";
				SparseBooleanArray checked = mPersonListView.getCheckedItemPositions();
				int size = checked.size(); // number of name-value pairs in the array
				for (int i = 0; i < size; i++) {
				    int key = checked.keyAt(i);
				    boolean value = checked.get(key);
				    if (value){
				    	str+="\n"+mListItems.get(key);
				    	done.setText(str);
				    	Intent returnIntent = new Intent();
				    	 returnIntent.putExtra("result",str);
				    	 setResult(RESULT_OK,returnIntent);     
				    	 finish();
				    	
				    }
				        //doSomethingWithSelectedIndex(key);
				}
			}
		});
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlusClientFragment.signIn(REQUEST_CODE_PLUS_CLIENT_FRAGMENT);
    }

    /**
     * Called when the {@link com.google.android.gms.plus.PlusClient} has been connected
     * successfully.
     *
     * @param plusClient The connected {@link PlusClient} for making API requests.
     */
    @Override
    public void onSignedIn(PlusClient plusClient) {
        plusClient.loadPeople(this, Person.Collection.VISIBLE,
                Person.OrderBy.ALPHABETICAL, 100, null);
    }

    @Override
    public void onPeopleLoaded(ConnectionResult status, PersonBuffer personBuffer,
            String nextPageToken) {
        if (status.getErrorCode() == ConnectionResult.SUCCESS) {
            mListItems.clear();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {
                    mListItems.add(personBuffer.get(i).getDisplayName());
                }
            } finally {
                personBuffer.close();
            }

            mListAdapter.notifyDataSetChanged();
        } else {
            Log.e(TAG, "Error when listing people: " + status);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mPlusClientFragment.handleOnActivityResult(requestCode, resultCode, data)) {
            switch (resultCode) {
                case RESULT_CANCELED:
                    // User canceled sign in.
                    Toast.makeText(this, R.string.greeting_status_sign_in_required,
                            Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    }
}
