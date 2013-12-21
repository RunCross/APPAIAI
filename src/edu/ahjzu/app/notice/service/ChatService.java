package edu.ahjzu.app.notice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import edu.ahjzu.app.notice.dao.ChatContentDao;
import edu.ahjzu.app.notice.dao.UsrDao;
import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.ChatContent;
import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.server.UsrGet;
import edu.ahjzu.app.notice.server.net.impl.ChatContentNETServer;

/**
 * (�ṩ�û�״̬,�û���Ϣ,�Ի���Ϣ)
 * 
 * @author Administrator
 * 
 */
public class ChatService extends Service {
	private final static String LOGTAG = "ChatService";
	private UsrListChangeListener ChatingUserListener = null;
	private UsrListChangeListener OnlineUsrListener = null;
	private List<ContentChangeListener> contentListenerList = new ArrayList<ChatService.ContentChangeListener>();
	// ��¼�ɹ�������û��Լ�������
	private static Usr myData = null;
	// ����������û�id ,Ĭ��û���˽�̸ʱΪ0
	private int chatingUsrID = 0;

	private ServiceBinder binder = null;
	private int ONLINEUSR = 0X2;
	//
	private UsrDao usrDao = null;
	ChatContentDao contentDao = null;

	@Override
	public void onCreate() {
		super.onCreate();
		// ��ʼ�����ݿ�
		usrDao = new UsrDao(getBaseContext());
		contentDao = new ChatContentDao(getBaseContext());
		binder = new ServiceBinder();
		// // ��ʼˢ���û�����
		// refreshUsr();
		// // �����˿�����
		// webMsgListener();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == ONLINEUSR) {
				List<Usr> list = (List<Usr>) msg.obj;
				System.out.println(LOGTAG + "  onlineUsrList:" + list.size());
				// insert to Usr table
				usrDao.addUsr(list);
				// ֪ͨ�����û��б��listviewˢ��
				if (OnlineUsrListener != null) {
					OnlineUsrListener.handler(-1);
				}
			}
		}
	};

	/**
	 * �ӷ��������������û���Ϣ
	 */
	private void refreshUsr(final int myId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Message message = new Message();
					message.what = ONLINEUSR;
					message.obj = UsrGet.getUsr(myId + "");
					handler.sendMessage(message);
					// ÿ��120��ӷ�����������������
					try {
						Thread.sleep(120000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	// �����������ݵ��ػ��߳�
	private void webMsgListener() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ChatContentNETServer msgserver = new ChatContentNETServer(
						OverallData.RECEIVEPORT, netMsgHandler);
			}
		}).start();
	}

	private Handler netMsgHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg != null) {
				ChatContent content = (ChatContent) msg.obj;
				// ��ӵ��������ݿ�
				addContent(content);
			}
		}
	};

	/**
	 * ��ӵ������ϵ��
	 * 
	 * @param usrid
	 */
	private void addRecentUsr(int usrid) {
		List<Integer> list = usrDao.getAllRecentUsr();
		for (int id : list) {
			if (id == usrid) {
				return;
			}
		}
		usrDao.addRecentUsr(usrid);
	}

	private void addContent(ChatContent content) {
		contentDao.save(content);
		// listener
		for (ContentChangeListener listener : contentListenerList) {
			if (listener != null) {
				if (content.getFromId().equals(myData.getId() + "")) {
					listener.handler(Integer.valueOf(content.getToId()));
				} else {
					listener.handler(Integer.valueOf(content.getFromId()));
				}
			}
		}
		// ����������ϵ�˲�,֪ͨˢ�������ϵ���б�
		if (content.getFromId().equals(myData.getId() + "")) {
			addRecentUsr(Integer.valueOf(content.getToId()));
			ChatingUserListener.handler(Integer.valueOf(content.getToId()));
		} else {
			addRecentUsr(Integer.valueOf(content.getFromId()));
			ChatingUserListener.handler(Integer.valueOf(content.getFromId()));
		}
	}

	public class ServiceBinder extends Binder {
		public Usr getMyUsr() {
			return myData;
		}

		public boolean deleteRecentUsr(int id) {
			return usrDao.deleteRecentUsr(id);
		}

		public void setUsrId(final int id) {
			if (id == -1) {
				throw new RuntimeException("��¼����,���������ݿ����û�IDΪ-1");
			}
			// ��ʼˢ���û�����
			refreshUsr(id);
			// �����˿�����
			webMsgListener();

			final Usr usr = usrDao.getMeInfo();
			System.out.println("getMeInfo:" + usr);
			// ����������ݿ��в������û��Լ�������,��ӷ��������ز��������ݿ�
			if (usr == null) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						myData = UsrGet.getUsr(id);
						if (myData != null) {
							usrDao.addMeInfo(myData);
						}
						System.out.println("myData:" + myData);
					}
				}).start();
			} else {
				myData = usr;
			}
		}

		public List<ChatContent> getContents(int id) {
			return contentDao.getContent(id + "", myData.getId() + "");
		}

		public void addChatContent(ChatContent content) {
			addContent(content);
		}

		public List<Usr> getChatingUsr() {
			// �����ݿ�ȡֵ
			List<Usr> usrs = new ArrayList<Usr>();
			List<Integer> list = usrDao.getAllRecentUsr();
			for (int id : list) {
				System.err.println("getChatingUsr:" + id);
				Usr usr = usrDao.getUsr(id);
				if (usr != null) {
					usrs.add(usr);
				}
			}
			return usrs;
		}

		public List<Usr> getOnlineUsr() {
			// �����ݿ�ȡֵ
			return usrDao.getOnLineUsrs();
		}

		/**
		 * ��������ϵ��
		 * 
		 * @param musr
		 * @return
		 */
		public void insertToChatingList(Usr musr) {
			// ֪ͨ���������б��listviewˢ��
			if (ChatingUserListener != null) {
				ChatingUserListener.handler(-1);
			}
			addRecentUsr(musr.getId());
		}

		/**
		 * ע�������û��仯������
		 * 
		 * @param changeListener
		 */
		public void setOnChatingUsrChangeListener(
				UsrListChangeListener changeListener) {
			ChatingUserListener = changeListener;
		}

		/**
		 * ע�������û��仯������
		 * 
		 * @param changeListener
		 */
		public void setOnOnlineUsrListChangeListener(
				UsrListChangeListener changeListener) {
			OnlineUsrListener = changeListener;
		}

		/**
		 * ע��������Ϣ�仯������
		 * 
		 * @param listener
		 */
		public void setChatContentChangeListener(ContentChangeListener listener) {
			contentListenerList.add(listener);
		}

		public void setChatingUsrID(int id) {
			chatingUsrID = id;
		}

		public void clearChatingUsrID() {
			chatingUsrID = 0;
		}
	}

	public interface UsrListChangeListener {
		public void handler(int id);
	}

	public interface ContentChangeListener {
		public void handler(int content_usrid);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		System.err.println(LOGTAG + "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		System.err.println(LOGTAG + "onDestroy");
		super.onDestroy();
	}
}
