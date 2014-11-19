package com.bwinata.linkup;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/*
 * Main Activity (Entry)
 * -------------------------------------------
 */
public class LinkUp extends Activity {
	
	// Private Variables
	ListView myListView;
	ListAdapter myAdapter;
	
	// Private Functions
	private void initialiseListeners () {
		/* List View */
		this.myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
				
				switch (i) {
					case 0:
						Intent configMenuIntent = new Intent (LinkUp.this, ConfigurationMenu.class);
						startActivity (configMenuIntent);					
						break;
					case 1:
						Intent servicesMenuIntent = new Intent (LinkUp.this, ServicesMenu.class);
						startActivity (servicesMenuIntent);
						break;
					case 2:
						Intent aboutMenuIntent = new Intent (LinkUp.this, AboutMenu.class);
						startActivity (aboutMenuIntent);
						break;
					default:
				}
			}
		});			
	}
	
	private void checkNetworkSettings () {
		String username, password, address, port;
		boolean showPassword;
		
		SharedPreferences settings = this.getSharedPreferences(ConnectDialog.PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		username = settings.getString(getResources().getString(R.string.pref_connect_username), " ");
		password = settings.getString(getResources().getString(R.string.pref_connect_password), " ");
		address = settings.getString(getResources().getString(R.string.pref_connect_address), " ");
		port = settings.getString(getResources().getString(R.string.pref_connect_port), " ");
		showPassword = settings.getBoolean(getResources().getString(R.string.pref_connect_show_password), false);
		
		if (username == " " && 
			password == " " &&
			address == " " &&
			port == " " &&
			showPassword == false) {
			/* 
			 * Nothing stored - happens when app starts up for first time ever, then this 
			 * block should never be exected indefintely
			 */		
			editor.putString(getResources().getString(R.string.pref_connect_username), getResources().getString(R.string.default_username));
			editor.putString(getResources().getString(R.string.pref_connect_password), getResources().getString(R.string.default_password));
			editor.putString(getResources().getString(R.string.pref_connect_address), getResources().getString(R.string.default_address));
			editor.putString(getResources().getString(R.string.pref_connect_port), getResources().getString(R.string.default_port));
			editor.putBoolean(getResources().getString(R.string.pref_connect_show_password), false);
			editor.commit();
		}	
	}
	
	private void queryInbox () {
		Uri inboxURI = Uri.parse("content://sms/inbox");
		String [] cols = new String [] {"_id", "address", "body", "date"};
		
		ContentResolver cr = getContentResolver();
		Cursor c = cr.query(inboxURI, cols, null, null, "date");
		if (c.moveToFirst()) {
			Log.d ("Link", "ID = " + c.getString(0));
			Log.d ("Link", "address = " + c.getString(1));
			Log.d ("Link", "body = " + c.getString(2));
			Log.d ("Link", "body = " + c.getString(3));
		}
		
		Log.d ("Link count", Integer.toString(c.getCount()));
	}
	
	//------------------------------------------------------------------------
	
	// ENTRY POINT
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link_up);
		
		String [] mainList = new String [] {"Configuration",
											"Services",
											"About"};
		
		int [] type_mainList = new int [] {Layout.LAYOUT_TEXTVIEW,
										   Layout.LAYOUT_TEXTVIEW,
										   Layout.LAYOUT_TEXTVIEW };
		
		this.myAdapter = new MainListAdapter (this, mainList, type_mainList);
		
		this.myListView = (ListView) findViewById (R.id.mainListView);
		this.myListView.setAdapter(myAdapter);
				
		this.initialiseListeners();
		this.checkNetworkSettings();		
		
		this.queryInbox();
		
		//Intent intent = new Intent (this, NetworkService.class);
		//startService(intent);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.link_up, menu);
		return true;
	}

}
