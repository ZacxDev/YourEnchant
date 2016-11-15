package me.Zacx.OKits.Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class WebSocket {

	private String hostName;
	private int port;
	
	public WebSocket(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}
	
	public void communicate(String hostName, int port, String uid, String mac) {
		
		Socket echoSocket;
		try {
			echoSocket = new Socket(hostName, port);
	    PrintWriter out =
	        new PrintWriter(echoSocket.getOutputStream(), true);
	    BufferedReader in =
	        new BufferedReader(
	            new InputStreamReader(echoSocket.getInputStream()));
	   // BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	    
	    String line;
	    //String outLine;
	    
	    System.out.println("Connected to " + hostName + ":" + port);
	    out.println("auth " + uid + " " + mac);
	     while((line = in.readLine()) != null) {
	    	 System.out.println("Server: " + line);
	    	 
//	    	 outLine = stdIn.readLine();
//	    	 if (outLine != null) {
//	    		 out.println(outLine);
//	    	 }
	     }
	     out.close();
	     in.close();
	     System.out.print("Closing Socket");
	    echoSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	
}