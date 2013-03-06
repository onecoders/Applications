package net.learn2develop.LBS;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class LBSActivity extends MapActivity {
    MapView mapView;
    MapController mc;
    GeoPoint p;
    
    LocationManager lm;
    LocationListener locationListener;

    private void checkVersion() {
        PackageManager pm = getPackageManager();
        try {
            //---get the package info---
            PackageInfo pi =  
                pm.getPackageInfo("net.learn2develop.LBS", 0);
            //---display the versioncode---
            Toast.makeText(getBaseContext(),
                "VersionCode: " +Integer.toString(pi.versionCode),
                Toast.LENGTH_SHORT).show();
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private class MapOverlay extends com.google.android.maps.Overlay
    {
        @Override
        public boolean draw(Canvas canvas, MapView mapView,
        boolean shadow, long when)
        {
            super.draw(canvas, mapView, shadow);

            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts);

            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.pushpin);
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);
            return true;
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView)
        {
        	//---when user lifts his finger---
        	if (event.getAction() == 1) {
        		GeoPoint p = mapView.getProjection().fromPixels(
        				(int) event.getX(),
        				(int) event.getY());

        		/*
                    Toast.makeText(getBaseContext(),
                        "Location: "+
                        p.getLatitudeE6() / 1E6 + "," +
                        p.getLongitudeE6() /1E6 , 
                        Toast.LENGTH_SHORT).show();
        		 */

        		Geocoder geoCoder = new Geocoder(
        				getBaseContext(), Locale.getDefault());
        		try {
        			List<Address> addresses = geoCoder.getFromLocation(
        					p.getLatitudeE6()  / 1E6,
        					p.getLongitudeE6() / 1E6, 1);

        			String add = "";
        			if (addresses.size() > 0) 
        			{
        				for (int i=0; i<addresses.get(0).getMaxAddressLineIndex();
        						i++)
        					add += addresses.get(0).getAddressLine(i) + "\n";
        			}
        			Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
        		}
        		catch (IOException e) {
        			e.printStackTrace();
        		}   
        		return true;

        	}
        	return false;
        }
    }   

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        checkVersion();
        
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        mapView.setTraffic(true);
        
        mc = mapView.getController();
                
        /*
        String coordinates[] = {"1.352566007", "103.78921587"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);

        p = new GeoPoint(
            (int) (lat * 1E6),
            (int) (lng * 1E6));

        mc.animateTo(p);
        mc.setZoom(13);
        */
        
        //---geo-coding---
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(
                "empire state building", 5);
            
            if (addresses.size() > 0) {
                p = new GeoPoint(
                        (int) (addresses.get(0).getLatitude() * 1E6),
                        (int) (addresses.get(0).getLongitude() * 1E6));
                mc.animateTo(p);
                mc.setZoom(20);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //---Add a location marker---
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);

        mapView.invalidate();
        
        //---use the LocationManager class to obtain locations data---
        lm = (LocationManager)
            getSystemService(Context.LOCATION_SERVICE);

        //---PendingIntent to launch activity if the user is within some locations---
        PendingIntent pendingIntent = PendingIntent.getActivity(
            this, 0, new
            Intent(android.content.Intent.ACTION_VIEW,
              Uri.parse("http://www.amazon.com")), 0);

        lm.addProximityAlert(37.422006, -122.084095, 5, -1, pendingIntent);
        
        locationListener = new MyLocationListener();
    }

    @Override
    public void onResume() {    	
        super.onResume();        
        
        //---request for location updates---
        lm.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0,
                locationListener);        
        
        //---request for location updates---
        lm.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0,
                locationListener);
    }
    
    @Override
    public void onPause() {    	
        super.onPause();
        
        //---remove the location listener---
        lm.removeUpdates(locationListener);
    }
        
    private class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location loc) {
        	if (loc != null) {
        		Toast.makeText(getBaseContext(),
        				"Location changed : Lat: " + loc.getLatitude() +
        				" Lng: " + loc.getLongitude(),
        				Toast.LENGTH_SHORT).show();


        		p = new GeoPoint(
        				(int) (loc.getLatitude() * 1E6),
        				(int) (loc.getLongitude() * 1E6));

        		mc.animateTo(p);
        		mc.setZoom(18);
        	}
        }

        @Override
        public void onProviderDisabled(String provider) {        	
        	Toast.makeText(getBaseContext(),
    				provider + " disabled",
    				Toast.LENGTH_SHORT).show();      	
        }

        @Override
        public void onProviderEnabled(String provider) {
        	Toast.makeText(getBaseContext(),
    				provider + " enabled",
    				Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status,
            Bundle extras) {
            String statusString = "";        	
        	switch (status) {
        	    case android.location.LocationProvider.AVAILABLE:
        	    	statusString = "available";
        	    case android.location.LocationProvider.OUT_OF_SERVICE:
        	    	statusString = "out of service";
        	    case android.location.LocationProvider.TEMPORARILY_UNAVAILABLE:
        	    	statusString = "temporarily unavailable";
        	}
        	
        	Toast.makeText(getBaseContext(),
    				provider + " " + statusString,
    				Toast.LENGTH_SHORT).show();
        }
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        MapController mc = mapView.getController(); 
        switch (keyCode) 
        {
            case KeyEvent.KEYCODE_3:
                mc.zoomIn();
                break;
            case KeyEvent.KEYCODE_1:
                mc.zoomOut();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }
}