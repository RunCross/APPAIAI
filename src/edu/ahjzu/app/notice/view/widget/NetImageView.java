package edu.ahjzu.app.notice.view.widget;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.APP.aiainotice.R;

/**
 * 自定义imageview，可显示网络图片并缓存，若有缓存则加载缓存中图片
 * 
 * @author huaf22@gmail.com
 * @date 2013-10-5 下午8:21:24
 */
public class NetImageView extends ImageView {
	public NetImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public NetImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			byte[] data = (byte[]) msg.obj;
			if (data != null) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				setImageBitmap(bitmap);// 显示图片
			} else {
				setImageResource(R.drawable.ic_launcher);
			}
		}
	};

	/**
	 * 显示网络图片
	 * 
	 * @param path
	 */
	public void setNetImage(final String path) {
		new Thread(new Runnable() {
			public void run() {
				handler.sendMessage(handler.obtainMessage(21, httpServer(path)));
			}
		}).start();
	}

	/**
	 * 显示网络图片并缓存
	 * 
	 * @param imagepath
	 *            图片网络路径
	 * @param cachepath
	 *            图片缓存路径
	 * @param imageName
	 *            自定义图片名（若为null则为图片自身名）
	 */
	public void setNetImage(final String imagepath, final String cachepath,
			final String imageName) {
		System.out.println("imagepath:" + imagepath);
		System.out.println("cachepath:" + cachepath);
		System.out.println("imageName:" + imageName);
		new Thread(new Runnable() {
			public void run() {
				handler2.sendMessage(handler.obtainMessage(21,
						httpServer(imagepath, cachepath, imageName)));
			}
		}).start();
	}

	Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Uri uri = (Uri) msg.obj;
			if (uri != null) {
				setImageURI(uri);// 显示图片
			} else {
				setImageResource(R.drawable.ic_launcher);
			}
		}

	};

	private byte[] httpServer(String imagepath) {
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			conn = (HttpURLConnection) new URL(imagepath).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				is = ((URLConnection) conn).getInputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
				return os.toByteArray();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (os != null)
					os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param imagepath
	 * @param cachepath
	 * @param imageName
	 * @return
	 */
	private Uri httpServer(String imagepath, String cachepath, String imageName) {
		if (imagepath.indexOf(".") == -1) {
			return null;
		}
		// FilePath
		File f = null;
		if (imageName == null) {
			f = new File(cachepath
					+ imagepath.substring(imagepath.lastIndexOf("/") + 1));
		} else {
			f = new File(cachepath + imageName
					+ imagepath.substring(imagepath.lastIndexOf(".")));
		}
		System.out.println("f.exists():" + f.exists());
		if (!f.exists()) {
			try {
				f.createNewFile();
				System.out.println("f.createNewFile()");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.err.println("IOException");
				e1.printStackTrace();
			}
			HttpURLConnection conn = null;
			InputStream is = null;
			OutputStream os = null;
			try {
				os = new FileOutputStream(f);
				conn = (HttpURLConnection) new URL(imagepath).openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				if (conn.getResponseCode() == 200) {
					is = ((URLConnection) conn).getInputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						os.write(buffer, 0, len);
					}
					os.flush();
					System.out.println("os.flush();");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
					if (os != null)
						os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return Uri.fromFile(f);

	}
}
