package net.learn2develop.Gallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class GalleryActivity extends Activity {	
    //---the images to display---
    Integer[] imageIDs = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);

        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v,
            int position, long id)
            {
                Toast.makeText(getBaseContext(),
                        "pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
                
                //---display the images selected---
                ImageView imageView = (ImageView) findViewById(R.id.image1);
                imageView.setImageResource(imageIDs[position]);
            }
        });
    }
    
    public class ImageAdapter extends BaseAdapter
    {
        Context context;
        int itemBackground;

        public ImageAdapter(Context c)
        {
            context = c;
            //---setting the style---
            TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);            
            
            itemBackground = a.getResourceId(
                R.styleable.Gallery1_android_galleryItemBackground, 0);                
            
            a.recycle();
        }

        //---returns the number of images---
        public int getCount() {
            return imageIDs.length;
        }

        
        //---returns the item---
        public Object getItem(int position) {
            return position;
        }

         //---returns the ID of an item---
        public long getItemId(int position) {
            return position;
        }      
        
        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) {
        	ImageView imageView;
            if (convertView == null) {
            	imageView = new ImageView(context);
                imageView.setImageResource(imageIDs[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new Gallery.LayoutParams(150, 120));                	
            } else {
            	imageView = (ImageView) convertView;
            }            
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }

}