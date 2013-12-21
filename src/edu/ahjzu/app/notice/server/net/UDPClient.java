package edu.ahjzu.app.notice.server.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
	/*
	 * InetAddress addr =null; DatagramSocket client =null; int port; public
	 * UDPClient(String straddr,int port) throws UnknownHostException,
	 * SocketException{ this.port=port; client = new DatagramSocket(); try {
	 * addr =InetAddress.getByName(straddr); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */
	public static void sendMsg(String straddr, int port, String msg) {
		System.out.println("∑¢ÀÕ–≈œ¢");
		System.out.println(straddr);
		System.out.println(port);
		System.out.println(msg);
		DatagramSocket client = null;
		try {
			client = new DatagramSocket();
			byte[] sendBuf;
			sendBuf = msg.getBytes();
			InetAddress addr = null;
			addr = InetAddress.getByName(straddr);
			DatagramPacket sendPacket = new DatagramPacket(sendBuf,
					sendBuf.length, addr, port);
			client.send(sendPacket);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}

	}

}
