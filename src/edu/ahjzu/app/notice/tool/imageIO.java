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
			// �������
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// ���ó�ʱʱ��Ϊ6000���룬conn.setConnectionTiem(0);��ʾû��ʱ������
			conn.setConnectTimeout(6000);
			// �������û��������
			conn.setDoInput(true);
			// ��ʹ�û���
			conn.setUseCaches(false);
			// �����п��ޣ�û��Ӱ��
			conn.connect();
			// �õ�������
			InputStream is = conn.getInputStream();
			// �����õ�ͼƬ
			bitmap = BitmapFactory.decodeStream(is);
			// �ر�������
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
