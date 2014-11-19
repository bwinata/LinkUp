package com.bwinata.linkup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ServicesMenu extends Activity {
	
	private ListView myView;
	private ListAdapter myAdapter;
	
	
	// ENTRY POINT
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services);
		
		String [] servicesMenu = new String [] {"Messages",
											  	"Calls",
											  	"Find Me"};
		
		int [] layout_servicesMenu = new int [] {Layout.LAYOUT_CHECKBOX,
												 Layout.LAYOUT_CHECKBOX,
												 Layout.LAYOUT_CHECKBOX};
				
		this.myAdapter = new MainListAdapter (this, servicesMenu, layout_servicesMenu);
		this.myView = (ListView) findViewById (R.id.servicesListView);
		this.myView.setAdapter(this.myAdapter);
				
		this.myView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			CheckBox myCheckBox;
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
				myCheckBox = (CheckBox) view.findViewById(R.id.checkBox1);

				if (!myCheckBox.isChecked()) myCheckBox.setChecked(true);
				else myCheckBox.setChecked(false);	
				
			}
		});
				
	}	

}
