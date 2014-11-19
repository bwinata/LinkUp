package com.bwinata.linkup;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	private int message_type;
	
	public Message (int type) {
		this.setType(type);
	}
	
	public void setType (int type) {
		this.message_type = type;
	}
	
	public int getType () {
		return this.message_type;
	}
}
