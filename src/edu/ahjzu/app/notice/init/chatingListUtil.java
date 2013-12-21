package edu.ahjzu.app.notice.init;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.ahjzu.app.notice.pojo.ChatContent;
import edu.ahjzu.app.notice.pojo.ChatList;
import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.server.xml.XMLSwitch;

/**
 * 
 * @author Administrator
 * 
 */
public class chatingListUtil {

	private final static String LOGTAG = "chatingListSub";
	// ����id-->�Ի��б�
	private static HashMap<String, ChatList> chatMap = new HashMap<String, ChatList>();

	public static ChatList get(String id) {
		return chatMap.get(id);
	}

	/**
	 * 
	 * @param usr
	 * @return
	 */
	public static List<ChatContent> getContentsById(Usr usr) {
		int id = usr.getId();
		if (chatMap.get(id + "") == null) {
			addUsr(usr);
		}
		return chatMap.get(id + "").chatContentList;
	}

	/**
	 * ���ݽ��յ������ݽ������û�id�����ݣ���ӵ���Ӧ�������¼��
	 * 
	 * @param usr
	 * @param chatdata
	 */
	public static void addChatContents(Usr usr, ChatContent chatdata) {
		String id = usr.getId() + "";
		ChatList ele = null;
		if (chatMap.get(id) != null) {
			ele = chatMap.get(id);
			ele.addchatContent(chatdata);
		} else {
			ele = new ChatList(usr);
			ele.loadchatContent();
			ele.addchatContent(chatdata);
			chatMap.put(id, ele);
		}
		// ele.addchatContent(chatdata);
		// data.get(id).chatContents.add(chatdata);
		System.out.println(LOGTAG + "��������¼�ɹ�:" + chatdata.getContent());
	}

	/**
	 * ��ӶԻ��б�
	 * 
	 * @param datas
	 * @param usr
	 */
	public static void addChatContentsList(ArrayList<ChatContent> datas, Usr usr) {
		int id = usr.getId();

		if (chatMap.get(id + "") == null) {
			System.out.println(LOGTAG + "������");
		} else {
			ChatList ele = chatMap.get(id + "");
			ele.addChatContentsList(datas);
		}
	}

	/**
	 * ����û�
	 * 
	 * @param usr
	 * @return
	 */
	public static ChatList addUsr(Usr usr) {
		String id = usr.getId() + "";
		if (chatMap.get(id) == null) {
			System.out.println(LOGTAG + "--����û���id:" + id);
			ChatList ele = new ChatList(usr);
			// ele.loadchatContent();
			// Log.v(LOGTAG, "������ʷ��¼������" + ele.chatContents.size());
			chatMap.put(id, ele);
		}
		return chatMap.get(id);

	}

	/**
	 * ɾ��chatinglist�е��û�
	 * 
	 * @param usr
	 */
	public static void deleteUsr(Usr usr) {
		int id = usr.getId();
		ChatList ele = chatMap.get(id + "");
		ele.saveChatContent();
		chatMap.remove(id + "");
	}

	/**
	 * ɾ�����û����������¼���ڴ桢���̣�
	 * 
	 * @param usr
	 */
	public static void deleteChatContent(Usr usr) {
		int id = usr.getId();
		ChatList ele = chatMap.get(id + "");
		ele.deleteAllChatContent();
	}

	/**
	 * ����б�
	 */
	public static void deleteAllUsrs() {
		saveAll();
		chatMap.clear();
		/*
		 * Set<String> ids = data.keySet(); Iterator<String> iditer =
		 * ids.iterator(); while(iditer.hasNext()){ String id = iditer.next();
		 * chatingListEle ele = data.get(id+""); ele.deleteAllChatContent(); }
		 */
	}

	/**
	 * �������м�¼������������û��б��������¼��
	 */
	public static void saveAll() {
		if (chatMap != null && chatMap.size() > 0) {
			Set<String> ids = chatMap.keySet();
			Iterator<String> iterator = ids.iterator();
			Usr usr = null;
			ArrayList<Usr> chatingListusrs = new ArrayList<Usr>();
			ChatList ele = null;
			while (iterator.hasNext()) {
				String id = iterator.next();
				ele = chatMap.get(id + "");
				ele.saveChatContent();
				usr = ele.usr;
				chatingListusrs.add(usr);
			}
			// ����chatingUsr�������ļ�
			// System.out.println("chatigngList����Ŀ��" + chatingListusrs.size());
			File file = new File(OverallData.CHATINGUSRCACHEPATH);
			// System.out.println("chtinglistUsr�б���·����"
			// + cachePath.getAbsolutePath());
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(OverallData.CHATINGUSRCACHEFILEPATH);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// new XMLSwitch();
			XMLSwitch.saveUsrList(chatingListusrs, file);
		}
	}

	public static Usr findByName(String name) {
		Set<String> ids = chatMap.keySet();
		Iterator<String> iditer = ids.iterator();
		Usr usr = null;
		while (iditer.hasNext()) {
			String id = iditer.next();
			usr = chatMap.get(id).usr;
			if (usr.getName().equals(name)) {
				return usr;
			}
		}
		System.out.println("û���ҵ��κ���Ա");
		return null;

	}
}
