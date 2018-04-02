package com.dm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class SocketUtil {
	private static final Integer connectTimeoutMillSecond=10000;
	
	public static String sendData3(String host, int port, String message) {
		String str = null;
		try {
			Socket s = new Socket(host, port);	// 变量 s 写成 socket（小写的）就会没有返回，神坑
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
			
			out.println(message);
			
			out.flush();
			str=in.readLine();
			
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return str;
	}
		
	/*
	// 二进制的用例，测试不通过
	public static byte[] sendData(String host, int port, byte[] data) {
		int bufferSize=20480;
		Socket s=null;
		BufferedReader in=null;
		OutputStream out=null;
		byte[] bbufMax = null;
		

		try {
			SocketAddress endpoint=new InetSocketAddress(host,port);
			s = new Socket();
			s.setSoTimeout(5000);				
			s.connect(endpoint, connectTimeoutMillSecond);	
			out = s.getOutputStream();
			out.write(data);
			out.flush();
			
			in = new BufferedReader( new InputStreamReader(s.getInputStream()));
			int iread=-1;
			int icount=0;
			bbufMax = new byte[bufferSize];
			while((iread=in.read())!=-1){
				bbufMax[icount]=(byte) iread;
				icount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bbufMax;
	}
	*/
}
