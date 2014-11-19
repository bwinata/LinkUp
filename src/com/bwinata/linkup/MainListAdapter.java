package com.bwinata.linkup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainListAdapter extends ArrayAdapter<String> {

	// Private Variables
	private TextView textView;
	private View theView;
	private Switch theSwitch;
	private int [] layout_type;
	private Layout.OnSwitchListener event;
	
	// -- Constructors
	public MainListAdapter(Context context, String[] items, int [] type) {
		super(context, R.layout.layout_textview, items);
		this.layout_type = type;
	}
	public MainListAdapter(Context context, String[] items, int [] type, Layout.OnSwitchListener event) {
		super(context, R.layout.layout_textview, items);
		this.layout_type = type;
		this.event = event;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		
		LayoutInflater inflater = LayoutInflater.from (getContext());
		
		String item = getItem (position);
		
		if (this.layout_type[position] == Layout.LAYOUT_CHECKBOX) {
				theView = inflater.inflate(Layout.LAYOUT_CHECKBOX, parent, false);
				textView = (TextView) theView.findViewById(R.id.checboxTextView);
				textView.setText(item);
		} else if (this.layout_type[position] == Layout.LAYOUT_SWITCH) {
				theView = inflater.inflate(Layout.LAYOUT_SWITCH, parent, false);
				textView = (TextView) theView.findViewById(R.id.textView1);
				theSwitch = (Switch) theView.findViewById(R.id.switch1);				
				theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked)
							if (MainListAdapter.this.event.onItemSwitch())
								Log.d ("Link", "Wifi connected");
							else
								MainListAdapter.this.theSwitch.setChecked(false);							
					}
				});
				textView.setText(item);							
		} else if (this.layout_type[position] == Layout.LAYOUT_TEXTVIEW) {
				theView = inflater.inflate(Layout.LAYOUT_TEXTVIEW, parent, false);
				textView = (TextView) theView.findViewById(R.id.textView1);
				textView.setText(item);
		} else {
				theView = inflater.inflate(Layout.LAYOUT_TEXTVIEW, parent, false);
		}
		
		return theView;
	}
}
