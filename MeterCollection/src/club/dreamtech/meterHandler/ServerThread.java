package club.dreamtech.meterHandler;

import java.util.ArrayList;
import java.util.List;

import club.dreamtech.meterCommon.CacheData;
import club.dreamtech.meterCommon.DataWrite;
import club.dreamtech.meterCommon.ErrorLog;
import club.dreamtech.meterCommon.OperationHelper;
import club.dreamtech.meterModel.CollectionModel;

public class ServerThread implements Runnable {


	private DTLMessageHandler dtlMessageHandler;
	private byte[] meterCode=null;
	public ServerThread(DTLMessageHandler dtlMessageHandler,byte[] meterCode) {
		this.dtlMessageHandler=dtlMessageHandler;
		this.meterCode=meterCode;
	}
	
	@Override
	public void run() {
		try {			
			 //自定义
			 byte[] inCode=new byte[]{0x00,0x00,0x00,0x00};
			 List<CollectionModel>collectionModelList=new ArrayList<CollectionModel>();
			 for (byte i = 0; i < 0xA; i++) {
				inCode[2]=i;
				for (byte j = 0; j < 0x3F; j++) {
					inCode[1]=j;
					CollectionModel cModel=this.dtlMessageHandler.getCollectionModel(meterCode, inCode);
					if (cModel!=null) {
						 System.out.println("获得采集数据");
						collectionModelList.add(cModel);
					}
				}
				DataWrite myDataWrite=new DataWrite();
				myDataWrite.connection();
				myDataWrite.dataListWrite(collectionModelList, 1);
				myDataWrite.disConnection();
			}
		} catch (Exception e) {
			CacheData.SocketClientMap.remove(OperationHelper.bytesToHex(meterCode));
			ErrorLog.writeError("线程池错误："+e.getMessage());
		}
	}
}
