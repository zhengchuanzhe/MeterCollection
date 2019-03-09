package club.dreamtech.meterCommon;


import java.util.Date;
import java.util.List;

import club.dreamtech.meterModel.CollectionModel;
import cn.edu.tsinghua.sql.Connection;
import cn.edu.tsinghua.sql.ConnectionFactory;
import cn.edu.tsinghua.sql.DataType;
import cn.edu.tsinghua.sql.IWriter;
import cn.edu.tsinghua.sql.ConnectionFactory.ConnectionType;

/**
 * 数据库写入操作类
 * @author cz
 *
 */
public class DataWrite {
	
	private Connection myConnection=null;
	private IWriter myWriter=null;
	
	public DataWrite() throws Exception {
		
	}
	
	/**
	 * 连接，建立资源
	 * @return
	 */
	public boolean connection() {
		try {
			myConnection=ConnectionFactory.createConnection(ConnectionType.MYSQL, new String[]{ConfigXML.getSQLIp()}, ConfigXML.getSQLPort(), ConfigXML.getSQLUser(), ConfigXML.getSQLPassWord(), ConfigXML.getSQLDatabase());	
			myWriter=myConnection.getWriter();
			return true;
		} catch (Exception e) {
			ErrorLog.writeError("数据库连接出错："+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 释放资源
	 * @return
	 */
	public boolean disConnection() {
		try {
			if (myWriter!=null) {
				myWriter.close();
			}
			if (myConnection!=null) {
				myConnection.close();
			}
			
			return true;
		} catch (Exception e) {
			ErrorLog.writeError("数据库关闭出错："+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 写入数据
	 * @param colModel 采集信息类
	 * @param type 数据类型：0 DataType.NEEDED;1 DataType.ENERGY;2 DataType.OTHER
	 * @return
	 */
	public boolean dataWrite(CollectionModel colModel,int type) {
		try {
			if (myWriter==null) {
				return false;
			}
			System.out.println("数据写入");
			long time=new Date().getTime();
			DataType dataType[]=new DataType[]{DataType.NEEDED,DataType.ENERGY,DataType.OTHER};
			myWriter.writeData(dataType[type], "DTL645-2007",colModel.getMeterAddressCode(), colModel.getIndentificationCode(), time, colModel.getValue());
			System.out.println("数据写入完成");
			return true;
		} catch (Exception e) {
			ErrorLog.writeError("数据写入出错："+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 批量写入数据
	 * @param colModelList
	 * @param type 数据类型：0 DataType.NEEDED;1 DataType.ENERGY;2 DataType.OTHER
	 * @return
	 */
	public boolean dataListWrite(List<CollectionModel> colModelList,int type) {
		try {
			if (myWriter==null) {
				return false;
			}
			System.out.println("数据批量写入");
			int size=colModelList.size();
			if (size<=0) {
				return true;
			}
			String meterCodeString=colModelList.get(0).getMeterAddressCode();
			String indentificationArray[]=new String[size];
			double valueArray[]=new double[size];
			for (int i = 0; i < size; i++) {
				CollectionModel collectionModel=colModelList.get(i);
				indentificationArray[i]=collectionModel.getIndentificationCode();
				valueArray[i]=collectionModel.getValue();
			}
			long time=new Date().getTime();
			myWriter.batchWriteData(DataType.NEEDED, "DTL645-2007", meterCodeString, indentificationArray, time,valueArray);
			System.out.println("数据批量写入完成");
			return true;
		} catch (Exception e) {
			ErrorLog.writeError("批量数据写入出错："+e.getMessage());
			return false;
		}
	}

	
	
}
