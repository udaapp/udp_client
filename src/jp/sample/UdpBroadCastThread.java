package jp.sample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpBroadCastThread extends Thread {

	int  mPort;
	byte [] mData;
	boolean mStart;
	DatagramSocket clientSocket = null;

	UdpBroadCastThread(int port, byte [] data) {
		this.mPort = port;
		this.mData = data;
	}

	public boolean isStart() {
		return mStart;
	}

	public void close() {
		//mStart=false;
		if(clientSocket!=null){
			clientSocket.close();
			clientSocket = null;
		}
	}

	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		try {
			clientSocket = new DatagramSocket();
			InetAddress inetAddress = InetAddress.getLocalHost();//InetAddress.getByName("localhost");
			System.out.println("UdpClient > Start :"+inetAddress.getHostName());

			byte [] ipBuffer = inetAddress.getAddress();
			//ブロードキャスト
			ipBuffer[3] = (byte)0xFF;
			inetAddress = InetAddress.getByAddress(ipBuffer);

			mStart = true;
			for(int i=0; mStart!=false ; i++ ) {

				System.out.println("UdpClient > Send: " + i);
				DatagramPacket sendPacket = new DatagramPacket(mData, mData.length, inetAddress, mPort);
				if(clientSocket==null){
					break;
				}
				clientSocket.send(sendPacket);

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}

			System.out.println("UdpClient > END: ");
			if(clientSocket!=null){
				clientSocket.close();
			}

		} catch (SocketException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}



}
