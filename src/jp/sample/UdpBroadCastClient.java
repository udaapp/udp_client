package jp.sample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UdpBroadCastClient implements Runnable {

	int  mPort;
	byte [] mData;
	boolean mStart;
	DatagramSocket clientSocket = null;

	UdpBroadCastClient(int port, byte [] data) {
		this.mPort = port;
		this.mData = data;
	}

	boolean isStart() {
		return mStart;
	}

	void stop() {
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
			//clientSocket.bind(null);
			//clientSocket.b
			clientSocket.setReceiveBufferSize(1024);
			clientSocket.setSendBufferSize(1024);
			clientSocket.setSoTimeout(1000);
			InetAddress inetAddress = InetAddress.getLocalHost();//InetAddress.getByName("localhost");
			inetAddress = InetAddress.getByName("192.168.0.6");
			System.out.println("UdpClient > Start :"+inetAddress.getHostName() + " port="+clientSocket.getLocalPort() + "/"+clientSocket.getPort());

			byte [] ipBuffer = inetAddress.getAddress();
			System.out.println(
					"ip=" +(ipBuffer[0]&0xff)
					+ "."+(ipBuffer[1]&0xff)
					+"."+ipBuffer[2]
							+"."+ipBuffer[3]);
			//ブロードキャスト
			ipBuffer[3] = (byte)0xFF;
			inetAddress = InetAddress.getByAddress(ipBuffer);

			mStart = true;
			for(int i=0; mStart!=false ; i++ ) {

				System.out.println("UdpClient > Send: " + i);
				//mData[0] = (byte)i;
				DatagramPacket sendPacket = new DatagramPacket(mData, mData.length, inetAddress, mPort);
				if(clientSocket==null){
					break;
				}
				clientSocket.send(sendPacket);

				//			sendPacket.get

				//受信用パケット
				byte buf[] = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				packet.setLength(buf.length);
				//clientSocket.setSoTimeout(500);
				try {
					//System.out.println("UdpClient > rec :"+clientSocket.getInetAddress() + " port="+clientSocket.getLocalPort() + "/"+clientSocket.getPort());
					clientSocket.receive(packet);
					//packet.
					int dlen = packet.getLength();
					int doff = packet.getOffset();
					byte buf2[]=packet.getData();
					System.out.println(" REC=" + packet.getData().length+ " dlen=" + dlen + " off="+ doff + " buf="+buf2[0]);
				}
				catch(SocketTimeoutException e) {
					System.out.println("**受信タイムアウト");
					//clientSocket.
				}
				//clientSocket.setSoTimeout(0);

				try {
					Thread.sleep(1);
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
