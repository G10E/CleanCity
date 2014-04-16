package com.nishant.cleancity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity {

	private SharedPreferences pref;
	private String prefName = "Settings";
	private EditText ip, port;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ip = (EditText) findViewById(R.id.setting_ip);
		port = (EditText) findViewById(R.id.setting_port);
		
		readPreferences();
	}
	
	private void readPreferences() {
		// TODO Auto-generated method stub
		pref = getSharedPreferences(prefName, MODE_PRIVATE);	
		//read values from keys, if not found a default value assigned is set
		String pref_ip = pref.getString("ServerIp", "http://10.0.2.2");
		String pref_port = pref.getString("ServerPort", "80");
		ip.setText(pref_ip);
		port.setText(pref_port);
		Toast.makeText(this, pref_ip, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}
	
	public void onClick(View view){
		//initialise preferences
		pref = getSharedPreferences(prefName, MODE_PRIVATE);
		
		//create a preferences editor
		SharedPreferences.Editor editor = pref.edit();
		
		//writing data
		editor.putString("ServerIp", ip.getText().toString());
		editor.putString("ServerPort", port.getText().toString());
		
		//commitcchanges
		editor.commit();
		Intent intent = new Intent(this, SecondActivity.class);
		startActivity(intent);
		
		
	}

}
