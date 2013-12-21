package edu.ahjzu.app.notice.server;

import java.util.List;

import android.util.Log;
import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.WeiBo;
import edu.ahjzu.app.notice.server.net.HTTPServer;
import edu.ahjzu.app.notice.server.xml.XMLSwitch;

public class weiboDataGet {

	public static List<WeiBo> getnewsData() {
		String url = OverallData.weiboURL;
		String strxml = new HTTPServer().HttpGet(url);
		Log.v("weibo", strxml);
		// List<WeiboPro> weibos = (List<WeiboPro>) new XMLFactory()
		// .XMLSwitch().getData(strxml);
		List<WeiBo> weiboList = XMLSwitch.getWeibo(strxml);
		if (weiboList != null) {
			for (WeiBo ele : weiboList) {
				ele.setIcon(OverallData.actionUrl + ele.getIcon());
				ele.setPicpath(OverallData.actionUrl + ele.getPicpath());
			}
		}
		return weiboList;
	}

}
