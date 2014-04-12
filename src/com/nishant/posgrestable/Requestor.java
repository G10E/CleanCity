package com.nishant.posgrestable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class Requestor extends AsyncTask<String,Void,String>{

	private TextView idView, nameView;
	private String id_req;
	private String localhost = "http://10.0.2.2";
	private String port = "8000";
	public Requestor(){}
	public Requestor(String i) {
		// TODO Auto-generated constructor stub
		id_req = i;
	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stubl
		try {
			String link = localhost+":"+port;
			/*String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id_req,"UTF-8");
			URL url = new URL(link);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data); //to write encoded data for http post requests
			wr.flush();
			*/
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(link);
			HttpResponse response = client.execute(httpGet);
	        HttpEntity entity = response.getEntity();
	        InputStream is = entity.getContent();
	        BufferedReader reader = new BufferedReader (new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
			
			return sb.toString();			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Got error";
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		try {
			JSONArray array = new JSONArray(result);
			JSONObject json = array.getJSONObject(0);
			String id = json.getString("text");
			String name = json.getString("key");
			this.idView.setText(id);
			this.nameView.setText(name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setIdView(TextView idView) {
		this.idView = idView;
	}

	public void setNameView(TextView nameView) {
		this.nameView = nameView;
	}
	
	

}
