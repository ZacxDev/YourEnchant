package me.Zacx.OKits.Net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

public class MAC {

	public static String GetMacAddress(InetAddress ip){
	       String address = null;
	        try {

	            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	            byte[] mac = network.getHardwareAddress();

	            StringBuilder sb = new StringBuilder();
	            for (int i = 0; i < mac.length; i++) {
	                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
	            }
	            address = sb.toString();

	        } catch (SocketException e) {

	            e.printStackTrace();

	        }

	       return address;
	   }
	
}
