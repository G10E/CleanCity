package com.nishant.cleancity;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener{

	private Button id1;
	private	MapView map;
	private IMapController mc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		id1 = (Button) findViewById(R.id.id1);

		setTitle("CleanCity - Team Up to Clean Up");
		//initialize and link map object
		map = (MapView) findViewById(R.id.map);
		
		//setting tile source
		map.setTileSource(TileSourceFactory.MAPQUESTOSM);
		
		//enable zoom controls and touch controls
		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);
		
		//setting a map controller
		mc = map.getController();
		
		//create a point with argument (latitude,longitude)
		GeoPoint point = new GeoPoint(27.74280,85.33170);//27.6190,85.5389//27.74280,85.33170
		
		//set center and zoom level
		mc.setZoom(20);
		mc.setCenter(point);
		
		//add click listeners
		id1.setOnClickListener(this);
	
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
		startActivity(intent);
	}
}
