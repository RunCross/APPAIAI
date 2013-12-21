package edu.ahjzu.app.notice.server.net.impl;

import android.os.Handler;
import android.os.Message;
import edu.ahjzu.app.notice.pojo.ChatContent;
import edu.ahjzu.app.notice.server.net.UDPServer;

/**
 * 监听端口
 * 
 * @author zhaoxianhua
 * 
 */
public class ChatContentNETServer extends UDPServer {

	public ChatContentNETServer(int port, Handler handler) {
		super(port, handler);
	}

	/**
	 * 回调函数
	 */
	@Override
	public void msgServer(final String msg,final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ChatContent content = ChatContent.fromJson(msg);
				System.out.println("网络接收到:" + msg);
				// 通知
				Message message = new Message();
				message.obj = content;
				handler.sendMessage(message);
			}
		}).start();

	}
}
