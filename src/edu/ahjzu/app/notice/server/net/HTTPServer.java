package edu.ahjzu.app.notice.server.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.net.Uri;
import edu.ahjzu.app.notice.util.MD5;

public class HTTPServer {
	private final static String LOGTAG = "HTTPServer";
	HttpClient httpClient = new DefaultHttpClient();

	public String HttpGet(String s) {
		URL url = null;
		try {
			url = new URL(s);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		StringBuilder builder = new StringBuilder();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			// request.setHeader("Content-Type",
			// "application/x-www-form-urlencoded; charset=utf-8");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		conn.setConnectTimeout(5000);
		try {
			if (conn.getResponseCode() == 200) {
				InputStream in = ((URLConnection) conn).getInputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					builder.append(new String(buffer, 0, len));
				}
			} else {
				System.err.println("�����������쳣:" + conn.getResponseCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("HTTPGet:" + builder.toString());
		return builder.toString();

	}

	public String HttpGet(String url, Map<String, String> map) {
		StringBuilder srcbuilder = new StringBuilder(url);
		Set<String> keys = map.keySet();
		Iterator<String> iditer = keys.iterator();
		String keyname = null;
		String value = null;
		while (iditer.hasNext()) {
			keyname = iditer.next();
			value = map.get(keyname);
			System.out.println(keyname + "=" + value);
			try {
				srcbuilder.append("&");
				srcbuilder.append(keyname).append("=")
						.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		/*
		 * for (Map.Entry<String, String> MapString : map.entrySet()) { String
		 * key=MapString.getKey();//��ȡ��ֵ�Ե����� String
		 * value=MapString.getValue();//��ȡ��ֵ�Ե�ֵ
		 * srcbuilder.append(key).append("=").append(value).append("&"); }
		 */
		System.out.println("HTTPGet:" + srcbuilder.toString());
		return HttpGet(srcbuilder.toString());

		/*
		 * conn = (HttpURLConnection)new URL(path).openConnection();
		 * 
		 * conn.setConnectTimeout(5000);
		 * 
		 * ((HttpURLConnection) conn).setRequestMethod("GET");
		 * 
		 * if(((HttpURLConnection) conn).getResponseCode()==200){
		 * 
		 * in=((URLConnection) conn).getInputStream();
		 * 
		 * }
		 * 
		 * 
		 * byte []buffer=new byte[1024]; int len=0;
		 * 
		 * try { while((len=in.read(buffer))!=-1){
		 * builder.append(buffer.toString()); } } catch (IOException e) {
		 * 
		 * e.printStackTrace(); } return builder.toString();
		 */
	}

	public InputStream getHTTPGetInputStream(String path)
			throws MalformedURLException, IOException {
		StringBuilder builder = new StringBuilder();
		InputStream in = null;
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			in = ((URLConnection) conn).getInputStream();
		}
		return in;

	}

	public String getHTTPGetString(String path) throws MalformedURLException,
			IOException {
		StringBuilder builder = new StringBuilder();
		InputStream in = null;
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			in = ((URLConnection) conn).getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				builder.append(new String(buffer));
			}
		}
		return builder.toString();

	}

	/**
	 * path-->MD5
	 * 
	 * @param imagepath
	 * @param cachedir
	 * @return
	 */
	public static Uri getBufferImage(String imagepath, File cachedir) {
		if (imagepath == null) {
			return null;
		}
		if (imagepath.indexOf(".") == -1) {
			return null;
		}
		File locadFile = new File(cachedir, MD5.getMD5(imagepath)
				+ imagepath.substring(imagepath.lastIndexOf(".")));
		// ������ش��ھͷ��ر��ص�ַ����������ھʹӷ���������
		if (locadFile.exists()) {
			return Uri.fromFile(locadFile);
		} else {
			HttpURLConnection conn = null;
			FileOutputStream fos = null;
			InputStream in = null;
			try {
				conn = (HttpURLConnection) new URL(imagepath).openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				if (conn.getResponseCode() == 200) {
					fos = new FileOutputStream(locadFile);
					in = ((URLConnection) conn).getInputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
					fos.flush();
					return Uri.fromFile(locadFile);
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					if (in != null)
						in.close();
					if (fos != null)
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Uri getBufferImage(String imagepath, File cachedir,
			String usrId) {

		if (imagepath.indexOf(".") == -1) {
			return null;
		}
		File locadFile = new File(cachedir, usrId
				+ imagepath.substring(imagepath.lastIndexOf(".")));
		// ������ش��ھͷ��ر��ص�ַ����������ھʹӷ���������
		if (locadFile.exists()) {
			return Uri.fromFile(locadFile);
		} else {
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) new URL(imagepath).openConnection();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			conn.setConnectTimeout(5000);
			FileOutputStream fos = null;
			InputStream in = null;
			try {
				conn.setRequestMethod("GET");

				if (conn.getResponseCode() == 200) {
					fos = new FileOutputStream(locadFile);
					in = ((URLConnection) conn).getInputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
					return Uri.fromFile(locadFile);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null)
						in.close();
					if (fos != null)
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return null;

	}

	public String sendByPost(String weburl) {
		StringBuilder result = new StringBuilder();
		HttpPost post = new HttpPost(weburl);
		try {
			HttpResponse httpResponse = httpClient.execute(post);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				// ��ȡ���������ص�����
				BufferedReader br = new BufferedReader(new InputStreamReader(
						entity.getContent()));
				String line = null;
				while ((line = br.readLine()) != null) {
					result.append(line);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return result.toString();

	}

	public String sendByPost(String weburl, List<NameValuePair> params) {
		StringBuilder result = new StringBuilder();
		HttpPost post = new HttpPost(weburl);
		try {
			// Post�������ͱ���������NameValuePair[]���д���
			// List <NameValuePair> params=new ArrayList<NameValuePair>();
			// params.add(new BasicNameValuePair("name","this is post"));

			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					// ��ȡ���������ص�����
					BufferedReader br = new BufferedReader(
							new InputStreamReader(entity.getContent(), "gbk"));
					String line = null;
					while ((line = br.readLine()) != null) {
						result.append(line);
					}
				}
			} else {
				System.err.println("post����ʧ��");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println("sendByPost:" + result.toString());
		return result.toString();

	}

	/* �ϴ��ļ� */
	public boolean uploadFile(String weburl, String uploadFile) {
		// �õ��ļ���
		String filename = null;
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		if (uploadFile.equals("")) {

			sendByPost(weburl);
		} else {
			try {
				filename = uploadFile.substring(
						uploadFile.lastIndexOf("/") + 1, uploadFile.length());
				URL url = new URL(weburl);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				/* ����Input��Output����ʹ��Cache */
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setUseCaches(false);
				/* ���ô��͵�method=POST */
				con.setRequestMethod("POST");
				/* setRequestProperty */
				con.setRequestProperty("Connection", "Keep-Alive");
				con.setRequestProperty("Charset", "UTF-8");
				con.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				/* ����DataOutputStream */
				DataOutputStream ds = new DataOutputStream(
						con.getOutputStream());
				ds.writeBytes(twoHyphens + boundary + end);
				ds.writeBytes("Content-Disposition: form-data; "
						+ "name=\"file1\";filename=\"" + filename + "\"" + end);
				ds.writeBytes(end);
				/* ȡ���ļ���FileInputStream */
				FileInputStream fStream = new FileInputStream(uploadFile);
				/* ����ÿ��д��1024bytes */
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];
				int length = -1;
				/* ���ļ���ȡ������������ */
				while ((length = fStream.read(buffer)) != -1) {
					/* ������д��DataOutputStream�� */
					ds.write(buffer, 0, length);
				}
				ds.writeBytes(end);
				ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
				/* close streams */
				fStream.close();
				ds.flush();
				/* ȡ��Response���� */
				InputStream is = con.getInputStream();
				int ch;
				StringBuffer b = new StringBuffer();
				while ((ch = is.read()) != -1) {
					b.append((char) ch);
				}
				/* ��Response��ʾ��Dialog */

				// showDialog("�ϴ��ɹ�"+b.toString().trim());
				/* �ر�DataOutputStream */
				ds.close();
				return true;
			} catch (Exception e) {
				// showDialog("�ϴ�ʧ��"+e);
				return false;
			}
		}
		return false;
	}
}
