package com.bwinata.linkup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectDialog extends DialogFragment {
	
	// Public Constants
	public static final String PREFS_NAME = "MyNetworkPrefs";
	public static final int PASSWORD_MAX_LENGTH = 8;
	
	// Private Variables
	private View view;
	private EditText textEdit;
	private CheckBox checkBox;
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;
	private LayoutInflater inflater;
	private Resources resource;
	
	// Private Functions
	private void loadDefaultPreferences () {
		/* Load saved preferences */
		this.textEdit = (EditText) this.view.findViewById(R.id.usernameEditText);
		this.textEdit.setText(this.settings.getString(resource.getString(R.string.pref_connect_username), " "));
		this.textEdit = (EditText) this.view.findViewById(R.id.passwordEditText);
		this.textEdit.setText(this.settings.getString(resource.getString(R.string.pref_connect_password), " "));
		this.textEdit = (EditText) this.view.findViewById(R.id.addressEditText);
		this.textEdit.setText(this.settings.getString(resource.getString(R.string.pref_connect_address), " "));
		this.textEdit = (EditText) this.view.findViewById(R.id.portEditText);
		this.textEdit.setText(this.settings.getString(resource.getString(R.string.pref_connect_port), " "));		
	}
		
	// Public Functions
	@Override
	public Dialog onCreateDialog (Bundle savedInstanceState) {		
		
		AlertDialog.Builder builder = new AlertDialog.Builder (getActivity ());
		
		this.resource = getActivity().getResources();
		this.inflater = getActivity().getLayoutInflater();
		this.view = this.inflater.inflate(R.layout.dialog_connect, null);
		this.settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
		this.editor = this.settings.edit();
		
		this.loadDefaultPreferences ();
		
		/* Build dialog */
		builder
		.setTitle(R.string.dialog_network_title)
		/* Set View - Inflate custom layout */
		.setView(this.view)		
		/* Set Positive Button */
		.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			
			/* Save Network details to Shared Preferences */
			public void onClick(DialogInterface dialog, int which) {
				/* 
				 * Verify that all fields are valid. If all valid, the proceed to commit changes into SharedPreferences, 
				 * else throw error. 
				 */
				NotifyDialog alertDialog = new NotifyDialog ();
				String username, password, address, port;
				boolean showPassword;
				
				ConnectDialog.this.textEdit = (EditText) ConnectDialog.this.view.findViewById(R.id.usernameEditText);
				username = ConnectDialog.this.textEdit.getText().toString();
				ConnectDialog.this.textEdit = (EditText) ConnectDialog.this.view.findViewById(R.id.passwordEditText);
				password = ConnectDialog.this.textEdit.getText().toString();				
				ConnectDialog.this.textEdit = (EditText) ConnectDialog.this.view.findViewById(R.id.addressEditText);
				address = ConnectDialog.this.textEdit.getText().toString();
				ConnectDialog.this.textEdit = (EditText) ConnectDialog.this.view.findViewById(R.id.portEditText);
				port = ConnectDialog.this.textEdit.getText().toString();
				
				alertDialog.setTitle("Network Error");							
				
				/* Check username */
				if (username.length() > 0) {
					/* Check Password */
					if (password.length() > PASSWORD_MAX_LENGTH) {
						/* Check Address */
						if (address.length() > 0) {
							/* Check Port */
							if (port.length() > 0) {
								ConnectDialog.this.editor.putString(getResources().getString(R.string.pref_connect_username), username);
								ConnectDialog.this.editor.putString(getResources().getString(R.string.pref_connect_password), password);
								ConnectDialog.this.editor.putString(getResources().getString(R.string.pref_connect_address), address);
								ConnectDialog.this.editor.putString(getResources().getString(R.string.pref_connect_port), port);
								ConnectDialog.this.editor.commit();
								
								Toast.makeText(getActivity(), "Network Settings Saved", Toast.LENGTH_SHORT).show();
								
							} else {
								alertDialog.setMessage("Invalid Details: Port");
								alertDialog.show(getFragmentManager(), "Notify");
							}
						} else {
							alertDialog.setMessage("Invalid Details: IP Address");
							alertDialog.show(getFragmentManager(), "Notify");	
						}							
					} else {
						alertDialog.setMessage("Invalid Details: Password");
						alertDialog.show(getFragmentManager(), "Notify");
					}
				} else {
					alertDialog.setMessage("Invalid Details: Username");
					alertDialog.show(getFragmentManager(), "Notify");
				}																											
			}
		})
		
		/* Set Negative Button */
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {			

			}
		});
		
		return builder.create();		
	}
}
