package edu.ahjzu.app.notice.tool;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class imageIO {
	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			// 这句可有可无，没有影响
			conn.connect();
			// 得到数据流
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;

	}
	/*
	 * public static Uri getImage(String imagepath,File cachedir){//path-->MD5
	 * File locadFile=new
	 * File(cachedir,MD5.getMD5(imagepath)+imagepath.substring
	 * (imagepath.lastIndexOf(".")));
	 * System.out.println("locadFile:"+locadFile); if(locadFile.exists()){
	 * 
	 * return Uri.fromFile(locadFile); } else{ HttpURLConnection conn = null;
	 * try { conn = (HttpURLConnection)new URL(imagepath).openConnection(); }
	 * catch (MalformedURLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } conn.setConnectTimeout(5000);
	 * 
	 * try { ((HttpURLConnection) conn).setRequestMethod("GET");
	 * if(((HttpURLConnection) conn).getResponseCode()==200){ FileOutputStream
	 * fos=new FileOutputStream(locadFile); InputStream in=((URLConnection)
	 * conn).getInputStream(); byte []buffer=new byte[1024]; int len=0;
	 * while((len=in.read(buffer))!=-1){ fos.write(buffer,0,len); }
	 * //fos.flush(); in.close(); fos.close(); return Uri.fromFile(locadFile); }
	 * } catch (FileNotFoundException e) { // TODO Auto-generated catch block
	 * System.out.println(e); } catch (IOException e) { // TODO Auto-generated
	 * catch block } }
	 * 
	 * return null; }
	 */
}
