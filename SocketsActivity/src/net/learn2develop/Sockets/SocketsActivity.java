package net.learn2develop.Sockets;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SocketsActivity extends Activity {
    static final String NICKNAME = "Wei-Meng";
	//---socket---
	InetAddress serverAddress;
	Socket socket;

	//---all the Views---    
	static TextView txtMessagesReceived;
	EditText txtMessage;

	//---thread for communicating on the socket---
	CommsThread commsThread;

	//---used for updating the UI on the main activity---
	static Handler UIupdater = new Handler() {
		@Override
		public void handleMessage(Message msg) {              
			int numOfBytesReceived = msg.arg1;
			byte[] buffer = (byte[]) msg.obj;
			
			//---convert the entire byte array to string---
			String strReceived = new String(buffer);

			//---extract only the actual string received---
			strReceived = strReceived.substring(
					0, numOfBytesReceived);

			//---display the text received on the TextView---              
			txtMessagesReceived.setText(
					txtMessagesReceived.getText().toString() + 
					strReceived);
		}
	};

	
    private class CreateCommThreadTask extends AsyncTask
    <Void, Integer, Void> {
		@Override
        protected Void doInBackground(Void... params) {            
            try {
                //---create a socket---
                serverAddress = 
                    InetAddress.getByName("192.168.1.142");
                socket = new Socket(serverAddress, 500);
                commsThread = new CommsThread(socket);
                commsThread.start();                
    			//---sign in for the user; sends the nick name---
    			sendToServer(NICKNAME);
            } catch (UnknownHostException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            } catch (IOException e) {
            	Log.d("Sockets", e.getLocalizedMessage());
            }
            return null;
        }
    }
        
    private class WriteToServerTask extends AsyncTask
    <byte[], Void, Void> {
        protected Void doInBackground(byte[]...data) {
            commsThread.write(data[0]);
            return null;
        }
    }
    
    private class CloseSocketTask extends AsyncTask
    <Void, Void, Void> {        
        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket.close();
            } catch (IOException e) {
            	Log.d("Sockets", e.getLocalizedMessage());                
            }
            return null;
        }
    }
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//---get the views---
		txtMessage = (EditText) findViewById(R.id.txtMessage);
		txtMessagesReceived = (TextView)
				findViewById(R.id.txtMessagesReceived);
	}

	public void onClickSend(View view) {
		//---send the message to the server---
		sendToServer(txtMessage.getText().toString());
	}

	private void sendToServer(String message) {
		byte[] theByteArray = 
				message.getBytes();
        new WriteToServerTask().execute(theByteArray);		
	}

    @Override
    public void onResume() {
        super.onResume();
        new CreateCommThreadTask().execute();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        new CloseSocketTask().execute();
    }

}