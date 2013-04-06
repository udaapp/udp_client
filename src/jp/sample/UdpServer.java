package jp.sample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpServer implements Runnable {

	int mPort = 8080;
	boolean mStart;
	DatagramSocket serverSocket = null;

	UdpServer(int port) {
		this.mPort = port;
		this.mStart = false;
	}

	boolean isStart() {
		return mStart;
	}

	void stop() {
		if(serverSocket!=null) {
			serverSocket.close();
			serverSocket = null;
		}
	}

	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ

		byte[] receiveData = new byte[1024];

		try {
			serverSocket = new DatagramSocket(mPort);

			mStart = true;
			System.out.println("UdpServer > Start");
			while(mStart) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);

				String sentence = new String( receivePacket.getData());
				InetAddress inetAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				System.out.println("UdpServer > IPAddress: " + inetAddress + " port:" + port + " RECEIVED: " + sentence);

			}

			serverSocket.close();
			System.out.println("UdpServer > End");

		} catch (SocketException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
