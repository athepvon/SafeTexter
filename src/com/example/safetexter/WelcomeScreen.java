package com.example.safetexter;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.bluetooth.*;

public class WelcomeScreen extends Activity {

	BluetoothAdapter bluetooth;
	String status;
	String PermaUUID;
	static String PermaAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_screen);
		
		bluetooth = BluetoothAdapter.getDefaultAdapter();	
		
		if(bluetooth==null)
		{
			Log.e("ahh","didn't work");
		}
		
		if(bluetooth.isEnabled())
		{
			Log.e("is enabled", "first in");
		}
		else
		{
			Log.e("uh oh", "not abled");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_screen, menu);
		return true;
	}
	
	public void goToSettings(View v) {
		
		Intent goToSettings = new Intent(this, ParentSettings.class);
		startActivity(goToSettings);
		}
	
	public void checkBT(View v)
	{
		
		if (bluetooth.isEnabled()) {
		    String mydeviceaddress = bluetooth.getAddress();
		    String mydevicename = bluetooth.getName();
		    status = mydevicename + " : " + mydeviceaddress;
		}
		else
		{
		    status = "Bluetooth is not Enabled.";
		}
		     
		Toast.makeText(this, status, Toast.LENGTH_LONG).show();
	}
	
	public void discoveryDevice(View v)
	{
		String name;
		String sampleUUID;
		
		String ui_serial_number; //I want to use this SN to connect.
		//BluetoothAdapter bluetoothAdapter;
		Set<BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
		for (BluetoothDevice device : pairedDevices) {
		    // here you get the mac using device.getAddress()

			ui_serial_number = device.getAddress();
			Log.e("device address",ui_serial_number);
			name = device.getName();
			Log.e("device name",name);
			sampleUUID = device.getUuids().toString();
			Log.e("device UUID",sampleUUID);
			//have if statement asking if address matches specific address 
			//of our bluetooth module. if so then work off it
			//maybe with mBluetoothAdapter.getRemoteDevice()
			
			if(name.equals("MICHAELS_LAPTOP"))
			{
				Log.e("in the if","inside");
				PermaAddress = ui_serial_number;
				PermaUUID = sampleUUID;	//sets uuid to keep for when opening certain bluetooth
				//may just be able to use generic UUID instead of to and from string
				//createSocket(PermaUUID);
				ConnectThread ct = new ConnectThread(device);
				//runOnUiThread(ct.start());
				
				//
			}
		}
	}
	
//	public void discoveryDevice(View v){
//		// Register the BroadcastReceiver
//		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//
//		    registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
//
//		    bluetooth.startDiscovery();
//		}
//
//		// Create a BroadcastReceiver for ACTION_FOUND
//		private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//		    public void onReceive(Context context, Intent intent) {
//		        String action = intent.getAction();
//		        // When discovery finds a device
//		        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//		            // Get the BluetoothDevice object from the Intent
//		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//
//		            Log.e("gettin the name", "device " + device.getName() + "\n" + device.getAddress());
//		        }
//		    }
//		};
	

	public class ConnectThread extends Thread {
	    private final BluetoothSocket mmSocket;
	    private final BluetoothDevice mmDevice;
	 
	    public ConnectThread(BluetoothDevice device) {
	        // Use a temporary object that is later assigned to mmSocket,
	        // because mmSocket is final
	    	Log.e("in the constructor","in connect");
	        BluetoothSocket tmp = null;
	        Log.e("in the constructor","in connect1");
	        mmDevice = device;
	        Log.e("in the constructor","in connect2");
	        // Get a BluetoothSocket to connect with the given BluetoothDevice
	        try {
	            // MY_UUID is the app's UUID string, also used by the server code
	        	Log.e("in the constructor","in connect3");
	            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(PermaUUID));
	            Log.e("in the constructor","in connect45");
	        } catch (Exception e) { }
	        Log.e("in the constructor","in connect5");
	        mmSocket = tmp;
	        Log.e("in the constructor","in connect6");
	    }
	 
	    public void run() {
	        // Cancel discovery because it will slow down the connection
	        bluetooth.cancelDiscovery();
	        Log.e("in the constructor","in connect4");
	        try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	            mmSocket.connect();
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out
	            try {
	                mmSocket.close();
	            } catch (IOException closeException) { }
	            return;
	        }
	 
	        // Do work to manage the connection (in a separate thread)
	      // manageConnectedSocket(mmSocket);
	    }
	 
	    /** Will cancel an in-progress connection, and close the socket */
	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }
	}
	
}
