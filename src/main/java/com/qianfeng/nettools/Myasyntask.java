package com.qianfeng.nettools;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class Myasyntask extends AsyncTask<Object, Void, Bitmap> {
  ImageView iv;
	@Override
	protected Bitmap doInBackground(Object... params) {

        iv=(ImageView) params[1];
		try {
			HttpURLConnection conn=(HttpURLConnection) new URL(params[0].toString()).openConnection();
			conn.setRequestMethod("GET	");
			conn.connect();
			if(conn.getResponseCode()==200){
				InputStream is=conn.getInputStream();
				return BitmapFactory.decodeStream(is);
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (ProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}
  @Override
protected void onPostExecute(Bitmap result) {

	super.onPostExecute(result);
	if(result!=null){
		iv.setImageBitmap(result);

	}
}
}
