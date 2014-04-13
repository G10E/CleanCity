package com.nishant.posgrestable;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity implements OnCheckedChangeListener, LocationListener{

	private TextView commentt, latt,longt;
	private String typet;
	private String lastLat;
	private String lastLong;
	private	Location l;
	private	Criteria criteria;
	private	String provider;
	private	LocationManager lm;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		setTitle("Report Screen");
		
		commentt = (TextView) findViewById(R.id.commenttext);
		latt = (TextView) findViewById(R.id.lattext);
		longt = (TextView) findViewById(R.id.longtext);
		RadioButton rb1 = (RadioButton) findViewById(R.id.rb1);
		RadioButton rb2 = (RadioButton) findViewById(R.id.rb2);
		RadioButton rb3 = (RadioButton) findViewById(R.id.rb3);
		RadioGroup rg1 = (RadioGroup) findViewById(R.id.rg1);
		rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                switch(checkedId){
                    case R.id.rb1:
                    	typet = ((RadioButton)findViewById(rg.getCheckedRadioButtonId() )).getText().toString();
                    	
                    break;

                    case R.id.rb2:
                        typet = ((RadioButton)findViewById(rg.getCheckedRadioButtonId() )).getText().toString();
                    break;

                    case R.id.rb3:
                    	String value3 = ((RadioButton)findViewById(rg.getCheckedRadioButtonId() )).getText().toString();
                    break;
                }


            }
        });
		rb1.setChecked(true);
		//set a LocationManager for reading GPS data
				lm = (LocationManager) getSystemService(LOCATION_SERVICE);
				
				//create criterias for the provider
				criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_COARSE);
				criteria.setPowerRequirement(Criteria.POWER_LOW);
				
				//creating a provider
				provider = lm.getBestProvider(criteria, false);
				
				//get a location contains (latitude, longitude, accuracy, etc.......)
				l = lm.getLastKnownLocation(provider);
				

				if(l!=null){
					//call if location is changed or updated or available
					onLocationChanged(l);
				}
				else{
					//call if location is unavailable
					Toast.makeText(this, "Service curreny unavailable", Toast.LENGTH_LONG);
				}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}
	
	public void onClick(View view){
		Requestor req = new Requestor();
		req.setReportType(typet);
		req.setComment(commentt.getText().toString());
		req.setLatitude(latt.getText().toString());
		req.setLongitude(longt.getText().toString());
		req.execute();
		Toast.makeText(getApplicationContext(), "Data Uploaded!!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void loc_btn(View view){
		latt.setText(lastLat);
		longt.setText(lastLong);
	}
	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		lastLat=loc.getLatitude()+"";
		lastLong=loc.getLongitude()+"";
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		lm.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lm.requestLocationUpdates(provider, 2000, 3, this);
	}
}
