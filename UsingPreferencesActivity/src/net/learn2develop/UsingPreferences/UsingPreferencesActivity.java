package net.learn2develop.UsingPreferences;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UsingPreferencesActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onClickLoad(View view) {
		Intent i = new Intent("net.learn2develop.AppPreferenceActivity");
		startActivity(i);
	}

	public void onClickDisplay(View view) {
		/*
		SharedPreferences appPrefs = 
				getSharedPreferences("net.learn2develop.UsingPreferences_preferences", 
						MODE_PRIVATE); 
		*/
        SharedPreferences appPrefs = 
                getSharedPreferences("appPreferences", MODE_PRIVATE);

		DisplayText(appPrefs.getString("editTextPref", ""));
	}

	public void onClickModify(View view) {
		/*
		SharedPreferences appPrefs = 
				getSharedPreferences("net.learn2develop.UsingPreferences_preferences", 
						MODE_PRIVATE);
		*/
        SharedPreferences appPrefs = 
                getSharedPreferences("appPreferences", MODE_PRIVATE);
		
		SharedPreferences.Editor prefsEditor = appPrefs.edit();
		prefsEditor.putString("editTextPref", 
				((EditText) findViewById(R.id.txtString)).getText().toString());
		prefsEditor.commit();
	}

	private void DisplayText(String str) {
		Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
	}

}