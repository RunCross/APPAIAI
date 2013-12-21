package edu.ahjzu.app.notice.server.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.os.Handler;

public abstract class UDPServer {
	DatagramSocket socket = null;
	DatagramPacket recvPacket = null;
	private Handler handler = null;

	public UDPServer(int port, Handler handler) {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		byte[] recvBuf = new byte[1024];
		recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
		System.out.println("server��ʼ�����");
		while (true) {
			try {
				socket.receive(recvPacket);
				msgServer(new String(recvPacket.getData(), 0,
						recvPacket.getLength()),handler);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �ص�����
	 * 
	 * @param msg
	 *            ��Ϣ
	 */
	public abstract void msgServer(String msg, Handler handler);

}
