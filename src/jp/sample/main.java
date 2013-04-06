package jp.sample;


public class main {

	static int PORT = 50001;

	static void test() {
		//test

		//UDPサーバ開始
//		UdpServer server = new UdpServer(PORT);
//		Thread t = new Thread(server);
//		t.start();

		//UDPクライアント開始
		final byte [] data = "UdpClient Sample Pro".getBytes();
		data[1] = 'D';//[2][3]
		//MessageSize
		data[4] = 20; data[5]=0; data[6]=0; data[7]=0;
		data[8] = 'A';
		data[9] = 'K';
		data[10] = 'R';
		//CheckSum
		int sum = 0;
		for (int i = 0; i <16; i++) {
			sum += (data[i] & 0xff);
		}
		data[16]=(byte)(sum&0xff);
		data[17]=(byte)(sum>>>8);
		data[18]=(byte)(sum>>>16);
		data[19]=(byte)(sum>>>24);

		UdpBroadCastClient client = new UdpBroadCastClient(PORT, data);
		Thread t2 = new Thread(client);
		t2.start();

		//待つ...
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println("***** STOP ****");
		//終了
		client.stop();

//		server.stop();
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		try {
//			InetAddress inetAddress = InetAddress.getLocalHost();//InetAddress.getByName("192.168.11.5");
//		      byte [] ipBuffer = inetAddress.getAddress();
//		      //ipBuffer[3] = (byte)0xFF;
//		      for(byte b : ipBuffer) {
//		    	  System.out.println(b);
//		      }
//		      inetAddress = InetAddress.getByAddress(ipBuffer);
//		      System.out.println("ip="+inetAddress.getHostName());
//
//		      inetAddress = InetAddress.getLocalHost();
//		      System.out.println("ip="+inetAddress.getHostName());


		      test();

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
