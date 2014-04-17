package com.nishant.cleancity;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity implements LocationListener{

	private EditText commentt, latt,longt;
	private String typet;
	private String lastLat;
	private String lastLong;
	private	Location l;
	private	Criteria criteria;
	private	String provider;
	private	LocationManager lm;
	private String encodedImage;
	
	private Button button_1;
    public int TAKE_PICTURE = 1;
    private ImageView imgview;
    private Bitmap bitmap;
    private String outputFileUri;
    private static final int REQUEST_CODE = 1;
	
    private String prefName = "Settings";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		setTitle("Report Screen");
		
		commentt = (EditText) findViewById(R.id.commenttext);
		latt = (EditText) findViewById(R.id.lattext);
		longt = (EditText) findViewById(R.id.longtext);
		RadioButton rb1 = (RadioButton) findViewById(R.id.rb1);
		RadioButton rb2 = (RadioButton) findViewById(R.id.rb2);
		RadioButton rb3 = (RadioButton) findViewById(R.id.rb3);
		RadioGroup rg1 = (RadioGroup) findViewById(R.id.rg1);
		
		imgview = (ImageView) findViewById(R.id.imageview);

		rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                switch(checkedId){
                    case R.id.rb1:
                    	typet = "Bio Degradable";
                    	
                    break;

                    case R.id.rb2:
                        //typet = ((RadioButton)findViewById(rg.getCheckedRadioButtonId() )).getText().toString();
                        typet = "Non-Bio Degradable";
                    break;

                    case R.id.rb3:
                    	typet = "Mixed";
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

	public void onClick(View view){
		Requestor req = new Requestor(getSharedPreferences(prefName, MODE_PRIVATE));
		req.setReportType(typet);
		req.setComment(commentt.getText().toString());
		req.setLatitude(latt.getText().toString());
		req.setLongitude(longt.getText().toString());
		req.setImageUri(outputFileUri);
		req.setEncodedImage(encodedImage);
		//req.setImage(bitmap);
		req.execute();
		Toast.makeText(getApplicationContext(), "Data Uploaded!!", Toast.LENGTH_SHORT).show();
	}
	public void loc_btn(View view){
		if (lm!=null){
			latt.setText(lastLat);
			longt.setText(lastLong);
		}
		else Toast.makeText(this, "Waiting for Location!!", Toast.LENGTH_SHORT).show();
	}
	
	public void onCameraClick(View view){
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, TAKE_PICTURE);
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
		Toast.makeText(this, "Provider Disabled!! Please Enable GPS", Toast.LENGTH_SHORT).show();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
    	    Bundle extras = data.getExtras();
    	    bitmap = (Bitmap) extras.get("data");
    	    imgview.setImageBitmap(bitmap);
    	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    	    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
    	    byte[] b = baos.toByteArray();
    	    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        }
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void settingClicked(View view){
		Intent intent = new Intent(this,SettingActivity.class);
		startActivity(intent);
	}
}
