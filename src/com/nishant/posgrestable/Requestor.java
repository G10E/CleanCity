package com.nishant.posgrestable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.TextView;

public class Requestor extends AsyncTask<Void,Void,Void>{

	private String reportType;
	private String comment;
	private String latitude;
	private String longitude;
	private String localhost = "http://10.0.2.2";
	private String port = "80";
	public Requestor(){}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stubl
		try {
			String link = localhost+":"+port+"/ban/cleancity/report.php";
			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("type", reportType));
	        nameValuePairs.add(new BasicNameValuePair("comment", comment));
	        nameValuePairs.add(new BasicNameValuePair("lat", latitude));
	        nameValuePairs.add(new BasicNameValuePair("long", longitude));
	        // Execute HTTP Post Request
			HttpPost httpPost = new HttpPost(link);
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(httpPost);
	        HttpEntity entity = response.getEntity();
	        InputStream is = entity.getContent();
	        BufferedReader reader = new BufferedReader (new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	

}
