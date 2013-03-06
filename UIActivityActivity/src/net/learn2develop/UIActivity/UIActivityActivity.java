package net.learn2develop.UIActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UIActivityActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //---the two buttons are wired to the same event handler---
        Button btn1 = (Button)findViewById(R.id.btn1);
        //btn1.setOnClickListener(btnListener);
        btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//---do something---
			}
		});        

        Button btn2 = (Button)findViewById(R.id.btn2);
        //btn2.setOnClickListener(btnListener);
        btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//---do something---
			}
		});
                        
        //---create an anonymous inner class to act as an onfocus listener---
        EditText txt1 = (EditText)findViewById(R.id.txt1);
        txt1.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(getBaseContext(),
                    ((EditText) v).getId() + " has focus - " + hasFocus,
                    Toast.LENGTH_LONG).show();
            }
        });
    }
    
    //---create an anonymous class to act as a button click listener---
    private OnClickListener btnListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            Toast.makeText(getBaseContext(),
                    ((Button) v).getText() + " was clicked",
                    Toast.LENGTH_LONG).show();
        }
    };
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                Toast.makeText(getBaseContext(),
                        "Center was clicked",
                        Toast.LENGTH_LONG).show();
                break;                
            case KeyEvent.KEYCODE_DPAD_LEFT:
                Toast.makeText(getBaseContext(),
                        "Left arrow was clicked",
                        Toast.LENGTH_LONG).show();
                break;                
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                Toast.makeText(getBaseContext(),
                        "Right arrow was clicked",
                        Toast.LENGTH_LONG).show();
                break;                
            case KeyEvent.KEYCODE_DPAD_UP:
                Toast.makeText(getBaseContext(),
                        "Up arrow was clicked",
                        Toast.LENGTH_LONG).show();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                Toast.makeText(getBaseContext(),
                        "Down arrow was clicked",
                        Toast.LENGTH_LONG).show();
                break;                
        }
        return false;
    }

}