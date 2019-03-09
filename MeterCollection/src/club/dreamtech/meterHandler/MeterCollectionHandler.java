package club.dreamtech.meterHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import club.dreamtech.meterCommon.CacheData;
import club.dreamtech.meterCommon.ConfigXML;
import club.dreamtech.meterCommon.ErrorLog;
import club.dreamtech.meterCommon.OperationHelper;
import club.dreamtech.meterModel.ClientModel;

public class MeterCollectionHandler {
	
	Thread  collectionThread;
	
	public MeterCollectionHandler() {
		collectionThread=new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					int interval=ConfigXML.getCollectionInterval();//间隔时间，分钟
					ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
					while (true) {
						System.out.println("开始采集");
						for (ClientModel clientModel :CacheData.SocketClientMap.values() ) {
							ServerThread serverSocket=new ServerThread(clientModel.getMyDTLMessageHandler(),clientModel.getMeterCode());
							cachedThreadPool.execute(serverSocket);
						}
						Thread.sleep(1000*60*interval);
					}
				} catch (Exception e) {
					ErrorLog.writeError("thread线程错误："+e.getMessage());
				}
			}
		});
	}
	
	
	/**
	 * 启动采集，阻塞函数
	 */
	@SuppressWarnings("resource")
	public void startCollection() {
		
		try {
			collectionThread.start();
			int port=Integer.parseInt(ConfigXML.getSocketPort());
			ServerSocket serverSocket=new ServerSocket(port);
			System.out.println("服务端已启动，等待客户端连接..");
			while (true) {
				Socket socket=serverSocket.accept();//侦听并接受到此套接字的连接,返回一个Socket对象
				
				DTLMessageHandler dtlMessageHandler=new DTLMessageHandler(socket);
				byte[] addressCodeByte=dtlMessageHandler.getAddressCode();
				if (addressCodeByte!=null) {
					String addressString=OperationHelper.bytesToHex(addressCodeByte);
					if (CacheData.SocketClientMap.containsKey(addressString)) {
						CacheData.SocketClientMap.replace(addressString, new ClientModel(dtlMessageHandler,addressCodeByte));
					}else {
						CacheData.SocketClientMap.put(addressString, new ClientModel(dtlMessageHandler,addressCodeByte));
					}
					System.out.println("编号："+OperationHelper.bytesToHex(addressCodeByte)+"电表连接");
					System.out.println("当前连接电表数："+CacheData.SocketClientMap.size());
				}
			}
		}catch (Exception e) {
			ErrorLog.writeError("启动采集错误："+e.getMessage());
			System.out.println(e.getMessage());
		}
	}
}
