package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientChat {

	String ip;
	int port;
	Socket socket;
	Scanner sc;

	public ClientChat() {
		// TODO Auto-generated constructor stub
	}

	public ClientChat(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;

		try {
			socket = new Socket(ip, port);
			System.out.println("connected server...");
			start();
		} catch (UnknownHostException e) {
			System.out.println("connectin refused...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("connectin refused...");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void start() throws IOException {
		sc = new Scanner(System.in);
		Receiver receiver = new Receiver();
		receiver.start();
		
		
		
		while (true) {
			Sender sender = new Sender();
			Thread t = new Thread(sender);
			System.out.println("input client massage;;;");
			String msg = sc.nextLine();
			if (msg.equals("q")) {

				sc.close();
				sender.close();
				receiver.close();
				break;
			}
			
			sender.setSendMsg(msg);
			t.start();

		}
		System.out.println("Exit Chatting..");
	}

	// message sender;;;
	class Sender implements Runnable {
		OutputStream ops;
		DataOutputStream dops;// 유니코드가 지원되는 스트림;;
		String msg;

		public Sender() throws IOException {
			ops = socket.getOutputStream();
			dops = new DataOutputStream(ops);
		}

		public void setSendMsg(String msg) {
			this.msg = msg;
		}

		public void close() throws IOException {
			dops.close();
			ops.close();
		}

		@Override
		public void run() {

			try {

				if (dops != null) {

					dops.writeUTF(msg);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}

		}

	}

	
	// message reciver;;;
	class Receiver extends Thread {
		InputStream ips;
		DataInputStream dips;

		public Receiver() throws IOException {
			ips = socket.getInputStream();
			dips = new DataInputStream(ips);
		}

		public void close() throws IOException {
			dips.close();
			ips.close();
		}

		@Override
		public void run() {
			while (true) {
				String msg = null;

				try {
					msg = dips.readUTF();
					System.out.println(msg);
				} catch (IOException e) {
					System.out.println("client getout...");
					break;
				}

			}

		}

	}

}
