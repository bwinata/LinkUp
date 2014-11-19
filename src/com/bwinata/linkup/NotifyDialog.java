package com.bwinata.linkup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class NotifyDialog extends DialogFragment{
	
	private String title;
	private String message;
	
	public void setTitle (String title) {
		this.title = title;
	}
	
	public void setMessage (String message) {
		this.message = message;
	}
	
	@Override
	public Dialog onCreateDialog (Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder
		.setTitle(this.title)
		.setMessage(this.message)
		.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// Do Nothing			
			}
		});
				
		return builder.create();		
	}
}
