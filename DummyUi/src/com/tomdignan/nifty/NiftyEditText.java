package com.tomdignan.nifty;

import android.util.AttributeSet;
import android.widget.EditText;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class NiftyEditText extends EditText{

	 private static final String TAG = "NiftyEditText";
	    
	    public NiftyEditText(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        NiftyTextViewHelper.initialize(this, context, attrs);
	    }

}
