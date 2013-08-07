package com.edu.bupt.mr;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("main");
		ServerSocket serverSocket=new ServerSocket(27888, 1, InetAddress.getByName("192.168.2.119")); 
		
		System.out.println(serverSocket.getLocalSocketAddress());
		//服务器Server将字符串 “HelloWorld"传送输出到客户端Client 
		while (true) {
		      Socket socket=null;
		      try {
		    	System.out.println("ok");
		        socket = serverSocket.accept();     //从连接请求队列中取出一个
		        System.out.println("New connection accepted " +
		        socket.getInetAddress() + ":" +socket.getPort());
//		        InputStream in = socket.getInputStream();
		        
		        
		        DataInputStream in = new DataInputStream(socket.getInputStream());
		        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		        System.out.println(in.readUTF());
		        socket.close();
		           
		        StringBuffer buf = new StringBuffer();
		        byte[] buffer = new byte[10240];
//				int size = in.read(buffer);
//				System.out.println(size);
				
//				int b;
//				while((b=in.read(buffer))!=-1){
//					   
//					  System.out.println(b);
//					  String tzt=new String(buffer,"utf-8");
//					  
//					   
//					  System.out.println(tzt);
//				} 
//				System.out.println("-1");
				
				
				
//				for(int i=0;i<size;i++){
//					buf.append((char)buffer[i]);
//				}
//				System.out.println(buf);
//				
//				byte[] buffer1 = new byte[10240];
//				size = in.read(buffer);
//				System.out.println(size);
//				for(int i=0;i<size;i++){
//					buf.append((char)buffer[i]);
//				}
//				System.out.println(buf);
//				
//				byte[] buffer2 = new byte[10240];
//				size = in.read(buffer);
//				System.out.println(size);
//				for(int i=0;i<size;i++){
//					buf.append((char)buffer[i]);
//				}
//				System.out.println(buf);
				
		      }catch (IOException e) {
		         e.printStackTrace();
		      }finally {
		         try{
		           if(socket!=null)socket.close();
		         }catch (IOException e) {
		        	 e.printStackTrace();
		         }
		      }
		    }
	}

}
