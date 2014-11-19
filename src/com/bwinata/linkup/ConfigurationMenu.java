package com.bwinata.linkup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ConfigurationMenu extends Activity {

	private ListView myView;
	private ListAdapter myAdapter;
	private AlertDialog.Builder notifier;

	
	// ENTRY POINT
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvity_configuration);
		
		String [] configMenu = new String [] {"Connect",
											  "Alert Type"};
		
		int [] layout_configMenu = new int [] {Layout.LAYOUT_SWITCH,
											   Layout.LAYOUT_TEXTVIEW};
		
		this.notifier = new AlertDialog.Builder (this);
		
		this.myAdapter = new MainListAdapter (this, configMenu, layout_configMenu, new Layout.OnSwitchListener() {
			/* Perform callback when switch is toggled */
			public boolean onItemSwitch() {
				ConnectivityManager connectivity = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
				NetworkInfo status = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);		
								
				/* Check if Wi-Fi is enabled and connected to a server */
				if (status.isConnected()) {					
					/* Check network settings are valid */
					
					/* Connect to server */
					Intent intent = new Intent (ConfigurationMenu.this, NetworkService.class);
					startService (intent);
					
					return true;					
				} else {	
					ConfigurationMenu.this.notifier
					.setTitle(getResources().getText(R.string.alert_network_error_title))
					.setMessage(getResources().getText(R.string.alert_wifi_not_connected));
					ConfigurationMenu.this.notifier.create().show();													
					return false;
				}				
			}
		});
		
		this.myView = (ListView) findViewById (R.id.configurationListView);
		this.myView.setAdapter(this.myAdapter);		
		this.myView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
				switch (i) {
					case 0:
						DialogFragment connectFragment = new ConnectDialog ();
						connectFragment.show (getFragmentManager (), "Network");	
						break;
					case 1:
						Log.d ("Link", "Alert type");
				}		
			}
		});
	}	
}
