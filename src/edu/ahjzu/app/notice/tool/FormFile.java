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
 * 上传的文件
 */
public class FormFile {
	private final static String LOGKEY = "FormFile";
	/** 上传文件的数据 */
	private byte[] data;
	private InputStream inStream;
	private File file;
	/** 文件名称 */
	private String filname;
	/** 请求参数名称 */
	private String parameterName;
	/** 内容类型 */
	private String contentType = "application/octet-stream";

	/**
	 * 
	 * @param filname
	 *            文件名称
	 * @param data
	 *            上传的文件数据
	 * @param parameterName
	 *            参数
	 * @param contentType
	 *            内容类型
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
	 *            文件名
	 * @param file
	 *            上传的文件
	 * @param parameterName
	 *            参数
	 * @param contentType
	 *            内容内容类型
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
		final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

		int fileDataLength = 0;
		// 得到文件类型数据的总长度
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
		// 构造文本类型参数的实体数据
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
		// 计算传输给服务器的实体数据总长度
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;

		URL url = new URL(path);
		Log.v(LOGKEY, url.toString());
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		OutputStream outStream = socket.getOutputStream();
		// 下面完成HTTP请求头的发送
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
		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes());
		// 把所有文本类型的实体数据发送出来
		outStream.write(textEntity.toString().getBytes());
		// 把所有文件类型的实体数据发送出来

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

		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes());
		outStream.flush();
		Log.v(LOGKEY, "上传完毕");
		if (reader.readLine().indexOf("200") == -1) {// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
			Log.v(LOGKEY, "上传图片失败！");
			return false;
		}

		Log.v(LOGKEY, "上传图片成功！");

		outStream.close();
		reader.close();
		socket.close();
		return true;
	}
}
