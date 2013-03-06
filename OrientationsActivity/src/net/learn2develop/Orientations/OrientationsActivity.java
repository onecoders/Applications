package net.learn2develop.Orientations;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class OrientationsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //---change to landscape mode---
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        /*
        Log.d("StateInfo", "onCreate");
        String str = (String) getLastNonConfigurationInstance();
        */
        
        //---get the current display info---
        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();

        if (d.getWidth() > d.getHeight()) {
            //---landscape mode---
            Log.d("Orientation", "Landscape mode");
        }
        else {
            //---portrait mode---
            Log.d("Orientation", "Portrait mode");
        }
    }
    
    @Override 
    public void onSaveInstanceState(Bundle outState) {
        //---save whatever you need to persist---
        outState.putString("ID", "1234567890");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //---retrieve the information persisted earlier---
        String ID = savedInstanceState.getString("ID");
    }
    
    @Override  
    public Object onRetainNonConfigurationInstance() {
        //---save whatever you want here; it takes in an Object type---
        return("Some text to preserve");
    }
    
    @Override
    public void onStart() {
        Log.d("StateInfo", "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("StateInfo", "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("StateInfo", "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("StateInfo", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("StateInfo", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onRestart() {
        Log.d("StateInfo", "onRestart");
        super.onRestart();
    }

}