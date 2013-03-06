package net.learn2develop.Layouts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LayoutsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onClick(View view) {
    	Toast.makeText(this, 
    		String.valueOf(view.getWidth()), 
    		Toast.LENGTH_LONG).show();
    }
}