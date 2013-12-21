package edu.ahjzu.app.notice.init;

import android.os.Environment;

/**
 * ϵͳ��������Ϣ
 * 
 * @author Administrator
 * 
 */
public class OverallData {
	// ���ؽ�����Ϣ�ļ����˿�
	public final static int RECEIVEPORT = 8085;
	// ������·��192.168.1.102
	public static String actionUrl = "http://172.16.12.82:8080/AIAIAPP/";
	public static final String SDPATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	public static boolean isWEB = false;
	public static boolean isSD = false;
	public static String APPFilepath = "/sdcard/AIAI/";
	// �û�ͷ�񱣴�·��
	public static String USRICONPATH = SDPATH + "/AIAI/usrimage/";
	// ����ͼƬ����·��
	public static String newsImagePath = "AIAI/newsimage";
	// ��ʷ�����¼
	public static String chatDocPath = SDPATH + "AIAI/chatDoc/";

	// ΢��ͼƬ·��
	public static String WEIBOIMAGEPATH = SDPATH + "/AIAI/weiboimage/";
	// ΢�����ݴ洢·��
	public static String WEIBOCACHEPATH = SDPATH + "/AIAI/weibocache/";
	public static String WEIBOCACHEFILEPATH = WEIBOCACHEPATH + "cache.xml";
	public static String weiboURL = actionUrl + "initServlet?Oper=getweibo";
	// �������ݻ���·��
	public static String FRIENDCACHEPATH = SDPATH + "/friendcache/";
	public static String FRIENDCACHEFILEPATH = FRIENDCACHEPATH + "cache.xml";
	// ����û�·��
	public static String insertusrURL = actionUrl + "initServlet";
	// �����û�����·��
	public static String CHATINGUSRCACHEPATH = SDPATH + "/AIAI/chatingusr/";
	public static String CHATINGUSRCACHEFILEPATH = CHATINGUSRCACHEPATH
			+ "chatingusr.xml";
	// �������ݻ���·��
	public static String CHATCONTENTPATH = SDPATH + "/AIAI/chatcontent/";

	// �û���������
	public static String ownDataPATH = "AIAI/owndata/";
	public static String ownData = "AIAI/owndata/data.xml";
	// ��������ַ
	public static String onlineUsrURL = actionUrl
			+ "initServlet?Oper=getonlinepeople";
	public static String GETUSRBYID = actionUrl + "initServlet?Oper=getusr";
	// ��ҳ����//actionUrl+"initServlet?Oper=jstl&id=0"
	public static String indexActivityURL = actionUrl;
	// ����ҳ����
	public static String newsActivityURL = actionUrl
			+ "initServlet?Oper=getnews&id=0";
	// ��֤�û���¼
	public final static String LOGINURL = actionUrl + "initServlet?Oper=login";

}
