package net.learn2develop.Services;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;



import android.content.ComponentName;
import android.os.IBinder;
import android.content.ServiceConnection;


public class ServicesActivity extends Activity {
    IntentFilter intentFilter;
    
    MyService serviceBinder;
    Intent i;

    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            //---called when the connection is made---
            serviceBinder = ((MyService.MyBinder)service).getService();
            try {
                URL[] urls = new URL[] {
                    new URL("http://www.amazon.com/somefiles.pdf"),
                    new URL("http://www.wrox.com/somefiles.pdf"),
                    new URL("http://www.google.com/somefiles.pdf"),
                    new URL("http://www.learn2develop.net/somefiles.pdf")};
                    //---assign the URLs to the service through the serviceBinder object---
                    serviceBinder.urls = urls;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }   
                startService(i);
        }
        public void onServiceDisconnected(ComponentName className) {
            //---called when the service disconnects---
            serviceBinder = null;
        }
    };
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public void onResume() {
    	super.onResume();

        //---intent to filter for file downloaded intent---
        intentFilter = new IntentFilter();
        intentFilter.addAction("FILE_DOWNLOADED_ACTION");

        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	
    	//---unregister the receiver---
    	unregisterReceiver(intentReceiver);
    }
    
    public void startService(View view) {
        //startService(new Intent(getBaseContext(), MyService.class));
        //OR
        //startService(new Intent("net.learn2develop.MyService"));
        //startService(new Intent(getBaseContext(), MyIntentService.class));
        
    	/*
        Intent intent = new Intent(getBaseContext(), MyService.class);
        try {
            URL[] urls = new URL[] {
                    new URL("http://www.amazon.com/somefiles.pdf"),
                    new URL("http://www.wrox.com/somefiles.pdf"),
                    new URL("http://www.google.com/somefiles.pdf"),
                    new URL("http://www.learn2develop.net/somefiles.pdf")};
            intent.putExtra("URLs", urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        startService(intent);
        */
        i = new Intent(ServicesActivity.this, MyService.class);
        bindService(i, connection, Context.BIND_AUTO_CREATE);    	
    }
    
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getBaseContext(), "File downloaded!",
                    Toast.LENGTH_LONG).show();
        }
    };

}