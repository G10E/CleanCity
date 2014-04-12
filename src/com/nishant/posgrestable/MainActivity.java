package com.nishant.posgrestable;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	private Button id1,id2;
	private TextView idView, nameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		id1 = (Button) findViewById(R.id.id1);
		id2 = (Button) findViewById(R.id.id2);
		idView = (TextView)	 findViewById(R.id.pid);
		nameView = (TextView) findViewById(R.id.textView1);

		//add click listeners
		id1.setOnClickListener(this);
		id2.setOnClickListener(this);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String i = "";
		switch(arg0.getId()){
		case(R.id.id1):
			i="home";
			break;
		case(R.id.id2):
			i="office";
			break;
		};
		Requestor requestor = new Requestor(i);
		requestor.setIdView(idView);
		requestor.setNameView(nameView);
		requestor.execute("null");
	}

}
