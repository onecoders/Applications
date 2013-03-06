package net.learn2develop.BasicViews5;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BasicViews5Activity extends ListActivity  {
	
    String[] presidents;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        
        setContentView(R.layout.main);

        ListView lstView = getListView();
                		
        //lstView.setChoiceMode(ListView.CHOICE_MODE_NONE); 
        //lstView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
        lstView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);        
        lstView.setTextFilterEnabled(true);

        presidents =
                getResources().getStringArray(R.array.presidents_array);

        setListAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_checked, presidents));
    }
    
    public void onListItemClick(
    ListView parent, View v, int position, long id)
    {
        Toast.makeText(this,
            "You have selected " + presidents[position],
            Toast.LENGTH_SHORT).show();
    }
    
    public void onClick(View view) {
    	ListView lstView = getListView();
    	
    	String itemsSelected = "Selected items: \n";
    	for (int i=0; i<lstView.getCount(); i++) {
    		if (lstView.isItemChecked(i)) {
    			itemsSelected += lstView.getItemAtPosition(i) + "\n";
    		}
    	}
    	Toast.makeText(this, itemsSelected, Toast.LENGTH_LONG).show();
    }

}