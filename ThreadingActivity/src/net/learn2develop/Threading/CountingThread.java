package net.learn2develop.Threading;

import android.util.Log;

public class CountingThread extends Thread {
	Boolean cancel=false;
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
			if (cancel) break;
		}
	}
	
    public void cancel() {
    	cancel = true;
    	//---called when the thread is cancelled---
    	Log.d("CountringThread","Thread cancelled");    	
    }
}
