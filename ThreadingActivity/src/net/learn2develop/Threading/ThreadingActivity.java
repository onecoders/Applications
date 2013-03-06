package net.learn2develop.Threading;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ThreadingActivity extends Activity {
	static TextView txtView1;

	//CountingThread thread;    

	DoCountingTask task;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        txtView1 = (TextView) findViewById(R.id.textView1);
    }      
   
    //--- Example  ---
    public void startCounter(View view) {  
    	task = (DoCountingTask) new DoCountingTask().execute();        
    }
    
    public void stopCounter(View view) {  
    	task.cancel(true);
    }    
        
    private class DoCountingTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void... params) {         
            for (int i = 0; i < 1000; i++) {
                //---report its progress---
                publishProgress(i);
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {					
					Log.d("Threading", e.getLocalizedMessage());
				}
                if (isCancelled()) break;
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {            
        	txtView1.setText(progress[0].toString());
        	Log.d("Threading", "updating...");
        }
    }    
    
	//---used for updating the UI on the main activity---
	static Handler UIupdater = new Handler() {
		@Override
		public void handleMessage(Message msg) {              
			byte[] buffer = (byte[]) msg.obj;
			
			//---convert the entire byte array to string---
			String strReceived = new String(buffer);

			//---display the text received on the TextView---              
			txtView1.setText(strReceived);
			Log.d("Threading", "running");
		}
	};
	
	/*
    //--- Example  ---
    public void startCounter(View view) {  
        thread = new CountingThread();    
        thread.start();
        //thread.stop();
    }
    
    public void stopCounter(View view) {  
        thread.cancel();
    }
    */

	/*
    //--- Example  ---
    public void startCounter(View view) {  
    	new Thread(new Runnable() {
    		@Override
    		public void run() {    			
    			for (int i=0; i<=1000; i++) {    				
    				//---update the main activity UI---
                    ThreadingActivity.UIupdater.obtainMessage(
                        0,  String.valueOf(i).getBytes() ).sendToTarget();
    				
    				//---insert a delay    				
    				try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Log.d("Threading", e.getLocalizedMessage());
					}
    			}
    		}    		
    	}).start();
    }
    */
	
    /*
    //--- Example  ---
    public void startCounter(View view) {  
    	new Thread(new Runnable() {
    		@Override
    		public void run() {    			
    			for (int i=0; i<=1000; i++) {
    				final int valueOfi = i; 
    				
    				//---update UI---
    				txtView1.post(new Runnable() {
    					public void run() {
    						//---UI thread for updating---    						
    						txtView1.setText(String.valueOf(valueOfi));
    					}
    				});
    				
    				//---insert a delay    				
    				try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Log.d("Threading", e.getLocalizedMessage());
					}
    			}
    		}    		
    	}).start();
    }       
	*/
    
    /*
    //--- Example  ---
    public void startCounter(View view) {  
    	Log.d("Threading", "running");
    	new Thread(new Runnable() {
			@Override
			public void run() {
		    	for (int i=0; i<=1000; i++) {
		    		Log.d("Threading", "running");
		    		txtView1.setText(String.valueOf(i));
		    		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Log.d("Threading", e.getLocalizedMessage());
					}
		    	}    
			}    		
    	}).start();
    }
    */
    
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		stopCounter(txtView1);
	}    
}