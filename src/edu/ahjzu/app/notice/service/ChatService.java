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
 * (提供用户状态,用户信息,对话信息)
 * 
 * @author Administrator
 * 
 */
public class ChatService extends Service {
	private final static String LOGTAG = "ChatService";
	private UsrListChangeListener ChatingUserListener = null;
	private UsrListChangeListener OnlineUsrListener = null;
	private List<ContentChangeListener> contentListenerList = new ArrayList<ChatService.ContentChangeListener>();
	// 登录成功后加载用户自己的数据
	private static Usr myData = null;
	// 正在聊天的用户id ,默认没有人交谈时为0
	private int chatingUsrID = 0;

	private ServiceBinder binder = null;
	private int ONLINEUSR = 0X2;
	//
	private UsrDao usrDao = null;
	ChatContentDao contentDao = null;

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化数据库
		usrDao = new UsrDao(getBaseContext());
		contentDao = new ChatContentDao(getBaseContext());
		binder = new ServiceBinder();
		// // 开始刷新用户数据
		// refreshUsr();
		// // 监听端口数据
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
				// 通知在线用户列表的listview刷新
				if (OnlineUsrListener != null) {
					OnlineUsrListener.handler(-1);
				}
			}
		}
	};

	/**
	 * 从服务器下载在线用户信息
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
					// 每隔120秒从服务器下载最新数据
					try {
						Thread.sleep(120000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	// 接受网络数据的守护线程
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
				// 添加到加入数据库
				addContent(content);
			}
		}
	};

	/**
	 * 添加到最近联系人
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
		// 添加至最近联系人并,通知刷新最近联系人列表
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
				throw new RuntimeException("登录出错,服务器数据库中用户ID为-1");
			}
			// 开始刷新用户数据
			refreshUsr(id);
			// 监听端口数据
			webMsgListener();

			final Usr usr = usrDao.getMeInfo();
			System.out.println("getMeInfo:" + usr);
			// 如果本地数据库中不存在用户自己的数据,则从服务器下载并存入数据库
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
			// 从数据库取值
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
			// 从数据库取值
			return usrDao.getOnLineUsrs();
		}

		/**
		 * 添加最近联系人
		 * 
		 * @param musr
		 * @return
		 */
		public void insertToChatingList(Usr musr) {
			// 通知正在聊天列表的listview刷新
			if (ChatingUserListener != null) {
				ChatingUserListener.handler(-1);
			}
			addRecentUsr(musr.getId());
		}

		/**
		 * 注册聊天用户变化监听器
		 * 
		 * @param changeListener
		 */
		public void setOnChatingUsrChangeListener(
				UsrListChangeListener changeListener) {
			ChatingUserListener = changeListener;
		}

		/**
		 * 注册在线用户变化监听器
		 * 
		 * @param changeListener
		 */
		public void setOnOnlineUsrListChangeListener(
				UsrListChangeListener changeListener) {
			OnlineUsrListener = changeListener;
		}

		/**
		 * 注册聊天信息变化监听器
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
