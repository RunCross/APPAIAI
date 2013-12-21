package edu.ahjzu.app.notice.server.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;

public class TCPServer {
	//服务器IP地址及其端口
	private final String IP="";
    private	final int PORT=8080;
    
    InetAddress address=null;
    BufferedWriter bw=null;
    BufferedReader br=null;
    Socket socketClient=null;
    String result="";
    
    static TCPServer TCPConn=new TCPServer();
    
    public TCPServer(){
    	try {
			address = InetAddress.getByName(IP);
			socketClient = new Socket(address,PORT);
		    bw=new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
		    br=new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
		}
    }
    public static TCPServer  getTCPConn(){
		return TCPConn;
    	
    }
	public void sendMsg(String outmessage){
	    try {
			bw.write(URLEncoder.encode(outmessage,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
  }	
	public String acceptMsg() throws IOException{
		
		String line=null;
		
		line = br.readLine();
		
		while(!line.equals("bye")){
			
			result=result+line;
			
			line= br.readLine();
			}
		return result;
		
	}
	public void close(){
		try {
			bw.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	public String acceptMsg(){
		Socket socket = null;
		String linedata="";
		try {
			ServerSocket serversocket=new ServerSocket(PORT);
			while(true){
				String result="";
				socket = serversocket.accept();
				BufferedReader bf=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				linedata=bf.readLine();
				if(linedata.equals("")){
					Log.v("acceptMsg","网络数据为空");
					result="网络数据为空";
				}
				while(!linedata.equals("bye")){
					result=result+linedata+"\n";
					linedata=bf.readLine();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.v("NETBasic.acceptMsg",e.toString());
		}
		return null;
	}*/
}
