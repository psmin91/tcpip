package tcpip2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {

		ServerSocket serversocket = null;
		Socket socket = null;

		InputStream ips = null;
		InputStreamReader ipsr = null;
		BufferedReader br = null;

		try {
			serversocket = new ServerSocket(7777);
			InetAddress ia = InetAddress.getLocalHost();
			System.out.println(ia.getHostName());
			System.out.println(ia.getHostAddress());
			System.out.println("Server Start");

			
			while(true) {
				socket = serversocket.accept();
	
				System.out.println("Client Connect..");
	
				// Receive Data
				ips = socket.getInputStream();
				ipsr = new InputStreamReader(ips);
				br = new BufferedReader(ipsr);
				String receiveStr = br.readLine();
				System.out.println(receiveStr);
	
				System.out.println("Server End");
			}
			} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ipsr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ips.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
