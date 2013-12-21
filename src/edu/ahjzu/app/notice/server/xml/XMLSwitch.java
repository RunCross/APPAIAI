package edu.ahjzu.app.notice.server.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.sax.RootElement;
import android.util.Log;

import com.thoughtworks.xstream.XStream;

import edu.ahjzu.app.notice.pojo.ChatContent;
import edu.ahjzu.app.notice.pojo.News;
import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.pojo.WeiBo;

/**
 * XML，OBJECT之间的转换
 * 
 * @author zhao
 * @date 2013-9-27 下午5:55:47
 */
public class XMLSwitch {
	private final static String LOGTAG = "XMLSwitch";

	public static List<WeiBo> getWeibo(String strxml) {
		if (strxml.length() != 0) {
			XStream xStream = new XStream();
			xStream.alias("WeiboPro", WeiBo.class);
			return (List<WeiBo>) xStream.fromXML(strxml);
		} else {
			Log.e(LOGTAG, "strxml.length():" + strxml.length() + "");
			return null;
		}
	}

	public static boolean saveWeiboList(List<WeiBo> datas, File file) {

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		XStream xStream = new XStream();
		xStream.alias("WeiboPro", WeiBo.class);
		String str = xStream.toXML(datas);

		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(str.getBytes());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;

	}

	/**
	 * 将Usr的xml数据转化为List<UsrPro> 对象
	 * 
	 * @param strxml
	 * @return
	 */
	public static List<Usr> getUsrList(InputStream is) {
		XStream xStream = new XStream();
		xStream.alias("usr", Usr.class);
		return (List<Usr>) xStream.fromXML(is);

	}

	public List<Usr> getUsrList(String strxml) {
		XStream xStream = new XStream();
		xStream.alias("UsrPro", Usr.class);
		return (List<Usr>) xStream.fromXML(strxml);
	}

	public static Usr getUsr(InputStream is) {
		XStream xStream = new XStream();
		xStream.alias("UsrPro", Usr.class);
		return (Usr) xStream.fromXML(is);
	}

	public static boolean saveUsrList(List<Usr> usrs, File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		XStream xStream = new XStream();
		xStream.alias("UsrPro", Usr.class);
		String str = xStream.toXML(usrs);

		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(str.getBytes());
			bos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		}
		return true;

	}

	/**
	 * 将List<UsrPro>保存为文件
	 * 
	 * @param usrs
	 * @param file
	 * @return
	 */
	public static boolean saveUsr(Usr usr, File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		XStream xStream = new XStream();
		xStream.alias("UsrPro", Usr.class);
		String str = xStream.toXML(usr);

		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(str.getBytes());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				bos.close();

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		}
		return true;
	}

	/**
	 * 
	 * @param usrs
	 * @return
	 */
	public String getXML(List<Usr> usrs) {
		if (usrs == null || usrs.size() == 0) {
			return null;
		}
		XStream xStream = new XStream();
		xStream.alias("UsrPro", Usr.class);
		String str = xStream.toXML(usrs);
		return str;

	}

	@SuppressWarnings("unchecked")
	public static List<News> getNews(InputStream is) {
		XStream xstream = new XStream();
		xstream.alias("news", News.class);
		List<News> list = (List<News>) xstream.fromXML(is);
		return list;
	}

	public String getChatContentXML(List<ChatContent> contentList) {
		if (contentList != null) {
			XStream xstream = new XStream();
			xstream.alias("ChatContentPro", ChatContent.class);
			return xstream.toXML(contentList);
		}
		return null;
	}

	public static List<ChatContent> getContentList(String strxml) {
		if (strxml != null) {
			XStream xstream = new XStream();
			xstream.alias("ChatContentPro", ChatContent.class);
			return (List<ChatContent>) xstream.fromXML(strxml);
		}
		return null;

	}
}
