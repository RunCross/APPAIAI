package edu.ahjzu.app.notice.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Map;

import android.util.Log;

/**
 * �ϴ����ļ�
 */
public class FormFile {
	private final static String LOGKEY = "FormFile";
	/** �ϴ��ļ������� */
	private byte[] data;
	private InputStream inStream;
	private File file;
	/** �ļ����� */
	private String filname;
	/** ����������� */
	private String parameterName;
	/** �������� */
	private String contentType = "application/octet-stream";

	/**
	 * 
	 * @param filname
	 *            �ļ�����
	 * @param data
	 *            �ϴ����ļ�����
	 * @param parameterName
	 *            ����
	 * @param contentType
	 *            ��������
	 */
	public FormFile(String filname, byte[] data, String parameterName,
			String contentType) {
		this.data = data;
		this.filname = filname;
		this.parameterName = parameterName;
		if (contentType != null)
			this.contentType = contentType;
	}

	/**
	 * 
	 * @param filname
	 *            �ļ���
	 * @param file
	 *            �ϴ����ļ�
	 * @param parameterName
	 *            ����
	 * @param contentType
	 *            ������������
	 */
	public FormFile(String filname, File file, String parameterName,
			String contentType) {
		this.filname = filname;
		this.parameterName = parameterName;
		this.file = file;
		try {
			this.inStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (contentType != null)
			this.contentType = contentType;
	}

	public File getFile() {
		return file;
	}

	public InputStream getInStream() {
		return inStream;
	}

	public byte[] getData() {
		return data;
	}

	public String getFilname() {
		return filname;
	}

	public void setFilname(String filname) {
		this.filname = filname;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	// private String Opertype = "";
	//
	//
	// public void setOper(String Opertype) {
	// this.Opertype = Opertype;
	// }
	public boolean post(String path, Map<String, String> params)
			throws Exception {
		final String BOUNDARY = "---------------------------7da2137580612"; // ���ݷָ���
		final String endline = "--" + BOUNDARY + "--\r\n";// ���ݽ�����־

		int fileDataLength = 0;
		// �õ��ļ��������ݵ��ܳ���
		StringBuilder fileExplain = new StringBuilder();
		fileExplain.append("--");
		fileExplain.append(BOUNDARY);
		fileExplain.append("\r\n");
		fileExplain.append("Content-Disposition: form-data;name=\""
				+ getParameterName() + "\";filename=\"" + getFilname()
				+ "\"\r\n");
		fileExplain.append("Content-Type: " + getContentType() + "\r\n\r\n");
		fileExplain.append("\r\n");
		fileDataLength += fileExplain.length();
		if (getInStream() != null) {
			fileDataLength += getFile().length();
		} else {
			fileDataLength += getData().length;
		}

		StringBuilder textEntity = new StringBuilder();
		// �����ı����Ͳ�����ʵ������
		for (Map.Entry<String, String> entry : params.entrySet()) {
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// Log.v(LOGKEY, textEntity.toString());
		// ���㴫�����������ʵ�������ܳ���
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;

		URL url = new URL(path);
		Log.v(LOGKEY, url.toString());
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		OutputStream outStream = socket.getOutputStream();
		// �������HTTP����ͷ�ķ���
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";

		Log.v(LOGKEY + "url.getpath():", requestmethod);
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// д��HTTP����ͷ�����HTTPЭ����дһ���س�����
		outStream.write("\r\n".getBytes());
		// �������ı����͵�ʵ�����ݷ��ͳ���
		outStream.write(textEntity.toString().getBytes());
		// �������ļ����͵�ʵ�����ݷ��ͳ���

		StringBuilder fileEntity = new StringBuilder();
		fileEntity.append("--");
		fileEntity.append(BOUNDARY);
		fileEntity.append("\r\n");
		fileEntity.append("Content-Disposition: form-data;name=\""
				+ getParameterName() + "\";filename=\"" + getFilname()
				+ "\"\r\n");
		fileEntity.append("Content-Type: " + getContentType() + "\r\n\r\n");
		outStream.write(fileEntity.toString().getBytes());
		if (getInStream() != null) {
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = getInStream().read(buffer, 0, 1024)) != -1) {
				outStream.write(buffer, 0, len);
			}
			getInStream().close();
		} else {
			outStream.write(getData(), 0, getData().length);
		}
		outStream.write("\r\n".getBytes());

		// ���淢�����ݽ�����־����ʾ�����Ѿ�����
		outStream.write(endline.getBytes());
		outStream.flush();
		Log.v(LOGKEY, "�ϴ����");
		if (reader.readLine().indexOf("200") == -1) {// ��ȡweb���������ص����ݣ��ж��������Ƿ�Ϊ200���������200����������ʧ��
			Log.v(LOGKEY, "�ϴ�ͼƬʧ�ܣ�");
			return false;
		}

		Log.v(LOGKEY, "�ϴ�ͼƬ�ɹ���");

		outStream.close();
		reader.close();
		socket.close();
		return true;
	}
}
