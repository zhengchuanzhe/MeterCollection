package club.dreamtech.meterHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import club.dreamtech.meterCommon.OperationHelper;
import club.dreamtech.meterModel.CollectionModel;



public class DTLMessageHandler {
	

	private DTLMessageCreate dtlMessageCreate;
	private DTLMessageAnalysis dtlMessageAnalysis;
	private Socket clientSocket;
	private OutputStream outputStream;
	private InputStream inputStream;
	public  DTLMessageHandler(Socket clientSocket) throws Exception {
		this.dtlMessageCreate=new DTLMessageCreate();
		this.dtlMessageAnalysis=new DTLMessageAnalysis();
		this.clientSocket=clientSocket;
		outputStream=clientSocket.getOutputStream();//获取一个输出流，向服务端发送信息
		inputStream=clientSocket.getInputStream();//得到一个输入流，接收客户端传递的信息
		
	}
	
	/**
	 * 资源关闭
	 * @throws Exception
	 */
	public void connectClose() throws Exception {
		if (this.clientSocket!=null) {
			this.clientSocket.close();
		}
		if (this.outputStream!=null) {
			this.outputStream.close();
		}
		if (this.inputStream!=null) {
			this.inputStream.close();
		}
	}
	
	/**
	 * 获取电表地址码
	 * @return
	 * @throws Exception
	 */
	public byte[] getAddressCode() throws Exception {
		byte[] sendByte=this.dtlMessageCreate.createAddressCode();
		if (sendByte!=null) {
		outputStream.write(sendByte);
        System.out.println("发送报文");
        byte []receivByte=new byte[1024];
        int dataLength=inputStream.read(receivByte);
        byte[] aa=new byte[dataLength];
        for (int k = 0; k < aa.length; k++) {
        	aa[k]=receivByte[k];
        }
        String error="";
        return dtlMessageAnalysis.meterAddressAnalysis(aa, error);
		}
		return null;
	}
    
	/**
	 * 获取指定标识的数据
	 * @param meterCode
	 * @param inCode
	 * @return
	 * @throws Exception
	 */
	public CollectionModel getCollectionModel(byte[] meterCode,byte[]inCode) throws Exception{
		byte[] sendByte=this.dtlMessageCreate.createMessage(meterCode, inCode);
		if (sendByte!=null) {
			 outputStream.write(sendByte);
             System.out.println("发送采集报文"+OperationHelper.bytesToHex(sendByte));
             byte []receivByte=new byte[1024];
             int dataLength=inputStream.read(receivByte);
             byte[] aa=new byte[dataLength];
             for (int k = 0; k < aa.length; k++) {
				aa[k]=receivByte[k];
			} 
             System.out.println("获得采集报文数据:"+OperationHelper.bytesToHex(aa));
             String error="";
             CollectionModel collectionModel= dtlMessageAnalysis.messageAnalysis(aa, 4, 2, error); 
             return collectionModel;
		}
		return null;
	}
}
