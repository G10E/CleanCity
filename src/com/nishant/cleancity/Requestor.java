package com.nishant.cleancity;

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

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

public class Requestor extends AsyncTask<Void,Void,Void>{

	private String reportType;
	private String comment;
	private String latitude;
	private String longitude;
	private String encodedImage;
	private Bitmap image;
	private String imageUri;
	private String host;
	private String port;
	
	public Requestor(){
		host="http://10.0.2.2";
		port="80";
	}
	
	public Requestor(SharedPreferences pref) {
		host = pref.getString("ServerIp", "http://10.0.2.2");
		port = pref.getString("ServerPort", "80");
		
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stubl
		try {
			String link = host+":"+port+"/ban/cleancity/report.php";
			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("type", reportType));
	        nameValuePairs.add(new BasicNameValuePair("comment", comment));
	        nameValuePairs.add(new BasicNameValuePair("lat", latitude));
	        nameValuePairs.add(new BasicNameValuePair("long", longitude));
	        nameValuePairs.add(new BasicNameValuePair("base64image", encodedImage));
	        
	        // Execute HTTP Post Request
			HttpPost httpPost = new HttpPost(link);
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(httpPost);
	        
	        //uploadImageFile();
	        /*HttpEntity entity = response.getEntity();
	        InputStream is = entity.getContent();
	        BufferedReader reader = new BufferedReader (new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
			Log.i("ASdas",sb.toString());*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
/*
	private void uploadImageFile() throws IOException {
        HttpURLConnection conn = null;
        DataOutputStream outputStream = null;
        DataInputStream inStream = null;        
        String link = host+":"+port+"/ban/cleancity/upload_image.php";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        FileInputStream fileInputStream = new FileInputStream(new File(imageUri) );
        URL url = new URL(link);
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
        outputStream = new
        DataOutputStream( conn.getOutputStream() );
        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
        outputStream.writeBytes("Content-Disposition: form-data; name=\"userfile\";filename=\"" + imageUri +"\"" + lineEnd);
        outputStream.writeBytes(lineEnd);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        while (bytesRead > 0)
        {
            outputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }
        outputStream.writeBytes(lineEnd);
        outputStream.writeBytes(twoHyphens + boundary + twoHyphens +lineEnd);
        int serverResponseCode = conn.getResponseCode();
        String serverResponseMessage = conn.getResponseMessage();       
        fileInputStream.close();
        outputStream.flush();
        outputStream.close();     
	}
*/
	@Override
	protected void onPostExecute(Void result) {
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

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getEncodedImage() {
		return encodedImage;
	}

	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}
}
