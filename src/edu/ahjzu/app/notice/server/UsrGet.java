package edu.ahjzu.app.notice.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.util.Log;
import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.server.net.HTTPServer;
import edu.ahjzu.app.notice.server.xml.XMLSwitch;

public class UsrGet {
	private final static String LOGTAG = "usrDataGet";

	/**
	 * �ӷ��������������û���Ϣ
	 * 
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static List<Usr> getUsr(String  myId) {
		List<Usr> usrList = new ArrayList<Usr>();
		String result = new HTTPServer().HttpGet(OverallData.onlineUsrURL
				+ "&id=" + myId);
		System.out.println("getUsr:"+result);
		if (!result.isEmpty()) {
			InputStream is = new ByteArrayInputStream(result.getBytes());
			// �����������е�����
			usrList = XMLSwitch.getUsrList(is);
			// ��ͼƬ����ϱ���·��
			for (int i = 0; i < usrList.size(); i++) {
				usrList.get(i).setIcon(
						OverallData.actionUrl + usrList.get(i).icon);
			}
		} else {
			Log.v(LOGTAG, "resultΪ�գ�" + result);
		}
		return usrList;

	}

	public static Usr getUsr(int id) {
		Usr usr = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		String result = new HTTPServer()
				.HttpGet(OverallData.GETUSRBYID, params);
		System.out.println("getUsr:" + result);
		if (!result.isEmpty()) {
			usr = Usr.fromJson(result);
			usr.setIcon(OverallData.actionUrl + usr.getIcon());
		}
		// if (!result.isEmpty()) {
		// InputStream is = new ByteArrayInputStream(result.getBytes());
		// // �����������е�����
		// usr = XMLSwitch.getUsr(is);
		// // ��ͼƬ����ϱ���·��
		// usr.setIcon(OverallData.actionUrl + usr.icon);
		// }
		else {
			Log.v(LOGTAG, "resultΪ�գ�" + result);
		}
		return usr;

	}
}
