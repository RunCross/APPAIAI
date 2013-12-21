package edu.ahjzu.app.notice.server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.News;
import edu.ahjzu.app.notice.server.net.HTTPServer;
import edu.ahjzu.app.notice.server.xml.XMLSwitch;

public class NewsGet {
	final static String LOGTAG = "NewsGet";

	public static List<News> getnewsData() {
		List<News> data = new ArrayList<News>();
		String url = OverallData.newsActivityURL;
		HTTPServer HTTP = new HTTPServer();
		String result = HTTP.HttpGet(url);

		if (result != "") {
			InputStream is = new ByteArrayInputStream(result.getBytes());
			data = XMLSwitch.getNews(is);
			// for (int i = 0; i < data.size(); i++) {
			// // OverallData.actionUrl 服务器地址
			// data.get(i).setPicpath(
			// OverallData.actionUrl + data.get(i).getPicpath());
			// // System.out.println("XML解析："+data.get(i).getContent());
			// Log.e(LOGTAG, "XML解析图片路径：" + data.get(i).getPicpath());
			// }
			for (News ele : data) {
				ele.setPicpath(OverallData.actionUrl + ele.getPicpath());
			}
		} else {
			Log.e(LOGTAG, "result为空：" + result);
		}
		return data;

	}
}
