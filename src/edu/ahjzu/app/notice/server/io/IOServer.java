package edu.ahjzu.app.notice.server.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class IOServer {
	/**
	 * 
	 * @param data
	 *            Êý¾Ý
	 * @param path
	 *            Â·¾¶
	 * @return
	 */
	public boolean IOWrite(String data, String path) {
		File f = new File(path);
		return IOWrite(data, f);
	}

	public boolean IOWrite(String data, File f) {
		if (!f.exists()) {
			try {
				f.createNewFile();

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(f));
			os.write(data.getBytes());
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public String IOReade(File f) {
		if (!f.exists()) {
			return null;
		}
		BufferedReader br = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));
			String line = null;
			while ((line = br.readLine()) != null) {
				strBuffer.append(strBuffer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return strBuffer.toString();

	}

	public byte[] IORead(File file) {
		if (!file.exists()) {
			return null;
		}
		InputStream is = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			int len = 0;
			byte[] buff = new byte[1024];
			while ((len = is.read(buff)) != -1) {
				os.write(buff, 0, len);
			}
			os.flush();
			return os.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
