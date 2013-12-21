package edu.ahjzu.app.notice.pojo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.server.io.IOServer;
import edu.ahjzu.app.notice.server.xml.XMLSwitch;

/**
 * 
 * @author zhao
 * @date 2013-10-7 ����8:13:33
 */
public class ChatList {
	private final String LOGTAG = "ChatListPro";
	public Usr usr = new Usr();
	public List<ChatContent> chatContentList = new ArrayList<ChatContent>();
	private File file = null;

	public ChatList(Usr usr) {
		this.usr = usr;
		createFile();
		loadchatContent();
	}

	/*
	 * public chatingListEle(int usr_id){ createFile(); }
	 */
	public List<ChatContent> getContents() {
		return chatContentList;
	}

	// ��ӶԻ��б�
	public void addChatContentsList(List<ChatContent> data) {
		for (ChatContent thedata : data) {
			chatContentList.add(thedata);
		}
	}

	private void createFile() {
		File chatContentcache = new File(OverallData.chatDocPath);
		if (!chatContentcache.exists()) {
			chatContentcache.mkdir();
		}
		file = new File(chatContentcache.getAbsolutePath() + usr.getId()
				+ ".xml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ��������¼
	public void addchatContent(ChatContent chat) {
		if (!chat.getContent().equals("")) {
			chatContentList.add(chat);
		}
	}

	// ���ļ��м�����ʷ�����¼
	public void loadchatContent() {
		String strxml = new IOServer().IOReade(file);
		if (strxml != null) {
			chatContentList = new XMLSwitch().getContentList(strxml);
		}
	}

	// ɾ�����������¼���ڴ�ʹ����ļ���
	public void deleteAllChatContent() {
		chatContentList.clear();
		file.delete();
	}

	// �������������¼
	public void saveChatContent() {
		String strxml = new XMLSwitch().getChatContentXML(chatContentList);
		if (strxml == null) {
			return;
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			bw.write(strxml);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
