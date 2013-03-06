package net.learn2develop.ContentProviders;

import android.app.Activity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContentProvidersActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onClickAddTitle(View view) {
		/*
		//---add a book---
		ContentValues values = new ContentValues();
		values.put(BooksProvider.TITLE, ((EditText)
				findViewById(R.id.txtTitle)).getText().toString());
		values.put(BooksProvider.ISBN, ((EditText)
				findViewById(R.id.txtISBN)).getText().toString());
		Uri uri = getContentResolver().insert(
				BooksProvider.CONTENT_URI, values);
		 */

		ContentValues values = new ContentValues();
		values.put("title", ((EditText)
				findViewById(R.id.txtTitle)).getText().toString());
		values.put("isbn", ((EditText)
				findViewById(R.id.txtISBN)).getText().toString());
		Uri uri = getContentResolver().insert(
				Uri.parse(
						"content://net.learn2develop.provider.Books/books"),
						values);


		Toast.makeText(getBaseContext(),uri.toString(),
				Toast.LENGTH_LONG).show();
	}

	public void onClickRetrieveTitles(View view) {
		//---retrieve the titles---
		Uri allTitles = Uri.parse(
				"content://net.learn2develop.provider.Books/books");
		
		Cursor c; 
		if (android.os.Build.VERSION.SDK_INT <11) {
			//---before Honeycomb---
			c = managedQuery(allTitles, null, null, null,
					"title desc");
		} else {
			//---Honeycomb and later---
			CursorLoader cursorLoader = new CursorLoader(
					this, 
					allTitles, null, null, null,
					"title desc");
			c = cursorLoader.loadInBackground();        	
		}
		
		if (c.moveToFirst()) {
			do{
				Toast.makeText(this, 
						c.getString(c.getColumnIndex(
								BooksProvider._ID)) + ", " +
								c.getString(c.getColumnIndex(
										BooksProvider.TITLE)) + ", " +
										c.getString(c.getColumnIndex(
												BooksProvider.ISBN)),
												Toast.LENGTH_SHORT).show();
			} while (c.moveToNext());
		}
	}

	public void updateTitle() {
		ContentValues editedValues = new ContentValues();
		editedValues.put(BooksProvider.TITLE, "Android Tips and Tricks");
		getContentResolver().update(
				Uri.parse(
						"content://net.learn2develop.provider.Books/books/2"),
						editedValues,
						null,
						null);
	}

	public void deleteTitle() {

		//---delete a title---
		getContentResolver().delete(
				Uri.parse("content://net.learn2develop.provider.Books/books/2"),
				null, null);


		//---delete all titles---
		getContentResolver().delete(
				Uri.parse("content://net.learn2develop.provider.Books/books"),
				null, null);

	}

}