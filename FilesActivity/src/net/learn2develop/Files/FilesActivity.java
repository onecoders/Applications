package net.learn2develop.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FilesActivity extends Activity {
	EditText textBox;
	static final int READ_BLOCK_SIZE = 100;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		textBox = (EditText) findViewById(R.id.txtText1);
		
        InputStream is = this.getResources().openRawResource(R.raw.textfile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String str = null;
        try {
            while ((str = br.readLine()) != null) {
                Toast.makeText(getBaseContext(), 
                    str, Toast.LENGTH_SHORT).show();
            }
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

	public void onClickSave(View view) {
		String str = textBox.getText().toString();
		try
		{
            //---SD Card Storage---
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsolutePath() +
                "/MyFiles");
            directory.mkdirs();
            File file = new File(directory, "textfile.txt");
            FileOutputStream fOut = new FileOutputStream(file);

            /*			
			FileOutputStream fOut =
					openFileOutput("textfile.txt",
							MODE_WORLD_READABLE);
			*/
                        
			OutputStreamWriter osw = new
					OutputStreamWriter(fOut);

			//---write the string to the file---
			osw.write(str);
			osw.flush(); 
			osw.close();

			//---display file saved message---
			Toast.makeText(getBaseContext(),
					"File saved successfully!",
					Toast.LENGTH_SHORT).show();

			//---clears the EditText---
			textBox.setText("");
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public void onClickLoad(View view) {
		try
		{
			//---SD Storage---
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsolutePath() + 
                "/MyFiles");
            File file = new File(directory, "textfile.txt");
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fIn);

            /*
			FileInputStream fIn = 
					openFileInput("textfile.txt");
			InputStreamReader isr = new 
					InputStreamReader(fIn);
            */
            
			char[] inputBuffer = new char[READ_BLOCK_SIZE];
			String s = "";

			int charRead;
			while ((charRead = isr.read(inputBuffer))>0)
			{
				//---convert the chars to a String---
				String readString =
						String.copyValueOf(inputBuffer, 0,
								charRead);
				s += readString;

				inputBuffer = new char[READ_BLOCK_SIZE];
			}
			//---set the EditText to the text that has been 
			// read---
			textBox.setText(s);

			Toast.makeText(getBaseContext(),
					"File loaded successfully!",
					Toast.LENGTH_SHORT).show();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

}