package com.test.weeklly.dummyui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.test.weeklly.mapsplaces.ConnectionDetector;

//import com.androidhive.googleplacesandmaps.R;
//import com.androidhive.googleplacesandmaps.R.drawable;


public class InternetAlertDialogManager extends Activity{
	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 * 				 - pass null if you don't want icon
	 * */
	Context context;
	boolean check;
	@SuppressWarnings("deprecation")
	  public static void showNoConnectionDialog(Context ctx1) {
        final Context ctx = ctx1;
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(true);
        builder.setMessage("No Connection");
        builder.setTitle("No Connection");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ctx.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                ((Activity) ctx).finish();               
                return;
                
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) ctx).finish();
            	return;
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            	((Activity) ctx).finish();
                return;
            }
        });

        builder.show();
    }
}
