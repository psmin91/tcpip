package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat {

	ServerSocket serversocket;
	Socket socket;
	Scanner sc;

	public ServerChat(int port) {

		try {
			serversocket = new ServerSocket(port);

			System.out.println("ready server...");
			start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				serversocket.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void start() throws IOException {

		socket = serversocket.accept();
		sc = new Scanner(System.in);

		while (true) {
			Sender sender = new Sender();
			Thread t = new Thread(sender);
			Receiver receiver = new Receiver();
			try {
			receiver.start();
			}catch(Exception e) {
				receiver.close();
				sender.close();
				sc.close();
				break;
			}
			
			System.out.println("input server msg...");
			String msg = sc.nextLine();
			if (msg.equals("q")) {

				receiver.close();
				sender.close();
				sc.close();
				break;
			}

			sender.setSendMsg(msg);
			t.start();

		}

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
				System.out.println("Nor Available");
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
