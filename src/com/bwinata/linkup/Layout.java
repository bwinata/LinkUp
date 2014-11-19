package com.bwinata.linkup;

public class Layout {
	// Public Contants 
	public static int LAYOUT_TEXTVIEW = R.layout.layout_textview;
	public static int LAYOUT_CHECKBOX = R.layout.layout_checkbox;
	public static int LAYOUT_SWITCH	= R.layout.layout_switch;
	
	// Public Interfaces
	public static interface OnSwitchListener {
		public boolean onItemSwitch ();		
	}
	
}
