package net.learn2develop.Provider;

import android.app.ListActivity;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.util.Log;

public class ProviderActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Uri allContacts = Uri.parse("content://contacts/people");
        Uri allContacts = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]
                {ContactsContract.Contacts._ID,
                 ContactsContract.Contacts.DISPLAY_NAME,
                 ContactsContract.Contacts.HAS_PHONE_NUMBER};
        
        Cursor c; 
        if (android.os.Build.VERSION.SDK_INT <11) {
        	//---before Honeycomb---
            c = managedQuery(allContacts, projection, 
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                    new String[] {"%Lee"}, 
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC");
            
            /*
            c = getContentResolver().query(allContacts, null, null, null, null);
            //---allows the activity to manage the Cursor’s 
            // lifecyle based on the activity’s lifecycle---
            startManagingCursor(c); 
            */
        } else {
        	//---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
            		this, 
            		allContacts, 
            		projection, 
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                    new String[] {"%Lee"}, 
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC");
            c = cursorLoader.loadInBackground();        	
        }
        
        String[] columns = new String[] {
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts._ID};
        
        int[] views = new int[] {R.id.contactName, R.id.contactID};

        SimpleCursorAdapter adapter;
        
        if (android.os.Build.VERSION.SDK_INT <11) {
        	//---before Honeycomb---
        	adapter = new SimpleCursorAdapter(
        			this, R.layout.main, c, columns, views);
        } else {
        	//---Honeycomb and later---
        	adapter = new SimpleCursorAdapter(
        			this, R.layout.main, c, columns, views,
        			CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);        	
        }        
        this.setListAdapter(adapter);
        
        PrintContacts(c);
    }
    
    private void PrintContacts(Cursor c)
    {
        if (c.moveToFirst()) {
            do{
            String contactID = c.getString(c.getColumnIndex(
                   ContactsContract.Contacts._ID));
            String contactDisplayName = 
                  c.getString(c.getColumnIndex(
                      ContactsContract.Contacts.DISPLAY_NAME));
                Log.v("Content Providers", contactID + ", " +
                    contactDisplayName);
            } while (c.moveToNext());
        }
    }

}