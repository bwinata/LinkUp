package com.bwinata.linkup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AboutMenu extends Activity {
	private ListView myView;
	private ListAdapter myAdapter;
	
	// ENTRY POINT
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		String [] aboutMenu = new String [] {"Version 1.0.0",
											 "Rate Me!"};
		
		int [] layout_aboutMenu = new int [] {Layout.LAYOUT_TEXTVIEW,
											  Layout.LAYOUT_TEXTVIEW};
		
		this.myAdapter = new MainListAdapter (this, aboutMenu, layout_aboutMenu);
		this.myView = (ListView) findViewById (R.id.aboutListView);
		this.myView.setAdapter (this.myAdapter);
				
	}		
}
