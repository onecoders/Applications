package net.learn2develop.PassingData;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondactivity);
				
		//---get the data passed in using getStringExtra()---
		Toast.makeText(this,getIntent().getStringExtra("str1"), 
				Toast.LENGTH_SHORT).show();

		//---get the data passed in using getIntExtra()---
		Toast.makeText(this,Integer.toString(
				getIntent().getIntExtra("age1", 0)), 
				Toast.LENGTH_SHORT).show();               

		//---get the Bundle object passed in---
		Bundle bundle = getIntent().getExtras();

		//---get the data using the getString()---         
		Toast.makeText(this, bundle.getString("str2"), 
				Toast.LENGTH_SHORT).show();

		//---get the data using the getInt() method---
		Toast.makeText(this,Integer.toString(bundle.getInt("age2")), 
				Toast.LENGTH_SHORT).show();
	}
	
	public void onClick(View view) {
		//---use an Intent object to return data---
		Intent i = new Intent();            

		//---use the putExtra() method to return some 
		// value---
		i.putExtra("age3", 45);

		//---use the setData() method to return some value---
		i.setData(Uri.parse(
				"Something passed back to main activity"));                           

		//---set the result with OK and the Intent object---
		setResult(RESULT_OK, i);

		//---destroy the current activity---
		finish();        
	}
}