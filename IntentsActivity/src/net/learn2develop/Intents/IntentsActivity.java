package net.learn2develop.Intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class IntentsActivity extends Activity {

	int request_Code = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onClickWebBrowser(View view) {
		/*
		Intent i = new
				Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse("http://www.amazon.com"));
		*/
		/*
		Intent i = new
				Intent("android.intent.action.VIEW",
						Uri.parse("http://www.amazon.com"));
		*/
		
        Intent i = new
                Intent("android.intent.action.VIEW");
        i.setData(Uri.parse("http://www.amazon.com"));		
		startActivity(i);
	}

	public void onClickMakeCalls(View view) {        
		Intent i = new 
				Intent(android.content.Intent.ACTION_DIAL,
						Uri.parse("tel:+651234567"));
		startActivity(i);
        
		/*
		Intent i = new 
				Intent(android.content.Intent.ACTION_CALL,
						Uri.parse("tel:+651234567"));
		startActivity(i);
		*/		
	}

	public void onClickShowMap(View view) {
		Intent i = new 
				Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse("geo:37.827500,-122.481670"));
		startActivity(i);

	}
	
	public void onClickLaunchMyBrowser(View view) {
		/*
		Intent i = new
				Intent("net.learn2develop.MyBrowser");
        i.setData(Uri.parse("http://www.amazon.com"));
        startActivity(i);
        */		
      
        Intent i = new
                Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://www.amazon.com"));
        //i.addCategory("net.learn2develop.Apps");
        //---this category does not match any in the intent-filter---
        i.addCategory("net.learn2develop.OtherApps");
        i.addCategory("net.learn2develop.SomeOtherApps");
        startActivity(Intent.createChooser(i, "Open URL using..."));
	}
	
}