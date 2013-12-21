package edu.ahjzu.app.notice.init;

import android.os.Environment;

/**
 * 系统的配置信息
 * 
 * @author Administrator
 * 
 */
public class OverallData {
	// 本地接受信息的监听端口
	public final static int RECEIVEPORT = 8085;
	// 服务器路径192.168.1.102
	public static String actionUrl = "http://172.16.12.82:8080/AIAIAPP/";
	public static final String SDPATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	public static boolean isWEB = false;
	public static boolean isSD = false;
	public static String APPFilepath = "/sdcard/AIAI/";
	// 用户头像保存路径
	public static String USRICONPATH = SDPATH + "/AIAI/usrimage/";
	// 新闻图片缓存路径
	public static String newsImagePath = "AIAI/newsimage";
	// 历史聊天记录
	public static String chatDocPath = SDPATH + "AIAI/chatDoc/";

	// 微博图片路径
	public static String WEIBOIMAGEPATH = SDPATH + "/AIAI/weiboimage/";
	// 微博数据存储路径
	public static String WEIBOCACHEPATH = SDPATH + "/AIAI/weibocache/";
	public static String WEIBOCACHEFILEPATH = WEIBOCACHEPATH + "cache.xml";
	public static String weiboURL = actionUrl + "initServlet?Oper=getweibo";
	// 好友数据缓存路径
	public static String FRIENDCACHEPATH = SDPATH + "/friendcache/";
	public static String FRIENDCACHEFILEPATH = FRIENDCACHEPATH + "cache.xml";
	// 添加用户路径
	public static String insertusrURL = actionUrl + "initServlet";
	// 聊天用户缓存路径
	public static String CHATINGUSRCACHEPATH = SDPATH + "/AIAI/chatingusr/";
	public static String CHATINGUSRCACHEFILEPATH = CHATINGUSRCACHEPATH
			+ "chatingusr.xml";
	// 聊天内容缓存路径
	public static String CHATCONTENTPATH = SDPATH + "/AIAI/chatcontent/";

	// 用户自身数据
	public static String ownDataPATH = "AIAI/owndata/";
	public static String ownData = "AIAI/owndata/data.xml";
	// 服务器地址
	public static String onlineUsrURL = actionUrl
			+ "initServlet?Oper=getonlinepeople";
	public static String GETUSRBYID = actionUrl + "initServlet?Oper=getusr";
	// 主页链接//actionUrl+"initServlet?Oper=jstl&id=0"
	public static String indexActivityURL = actionUrl;
	// 新闻页链接
	public static String newsActivityURL = actionUrl
			+ "initServlet?Oper=getnews&id=0";
	// 验证用户登录
	public final static String LOGINURL = actionUrl + "initServlet?Oper=login";

}
