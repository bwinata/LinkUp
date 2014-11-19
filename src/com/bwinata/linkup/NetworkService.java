package com.bwinata.linkup;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class NetworkService extends IntentService {

	private static final String IP = "10.0.0.9";
	private static final int PORT = 8080;
	
	private Socket client;
	private SocketAddress addr;
	private ObjectInputStream input;
		
	public NetworkService() {
		super("NetworkService");
	}

	private void processMessage (Message msg) {
		switch (msg.getType()) {
			case MessageType.INCOMING_SMS:
				Log.d ("Link", "Message received - INCOMING SMS");
				break;
			case MessageType.INCOMING_CALL:
				Log.d ("Link", "Message received - INCOMING CALL");
				break;
		}
	}	

	@Override
	protected void onHandleIntent(Intent intent) {	
		Log.d ("Link", "Connecting to server...");
		this.client = new Socket ();
		this.addr = new InetSocketAddress (IP, PORT);
		try {						
			this.client.connect(addr, 5000);
			Log.d ("Link", "Connected!");
			while (this.client.isConnected()) {
				Log.d ("Link", "Waiting for message from client...");
				this.input = new ObjectInputStream (this.client.getInputStream());
				Message msg = (Message) this.input.readObject();
				this.processMessage (msg);
			}						
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
}
