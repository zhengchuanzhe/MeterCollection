package club.dreamtech.meterMain;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import club.dreamtech.meterCommon.OperationHelper;
import club.dreamtech.meterModel.CollectionModel;
import cn.edu.tsinghua.sql.Connection;
import cn.edu.tsinghua.sql.ConnectionFactory;
import cn.edu.tsinghua.sql.DataType;
import cn.edu.tsinghua.sql.IWriter;
import cn.edu.tsinghua.sql.ConnectionFactory.ConnectionType;
import cn.edu.tsinghua.sql.mysql.MySQLWriter;
import cn.edu.tsinghua.sql.IReader;
import cn.edu.tsinghua.sql.IRegister;
import cn.edu.tsinghua.sql.IResultSetIterator;

public class testMain {
	public static void main(String[] args) throws Exception  {
		
		
		
		Connection connection=ConnectionFactory.createConnection(ConnectionType.MYSQL, new String[]{"39.108.180.240"}, "3306", "root", "123456", "energy");
		IReader reader=connection.getReader();
		IWriter writer=connection.getWriter();
		((MySQLWriter) writer).clear();
		connection.registerMeter( "041708762392","DTL645-2007");
		connection.registerMeter( "041708762393","DTL645-2007");
		connection.registerMeter( "041708762394", "DTL645-2007");
		
		
		
//		connection.registerField("DTL645-2007", "00000000", "组合有功总电能");
//		connection.registerField("DTL645-2007", "00000100", "组合有功费率1电能");
//		connection.registerField("DTL645-2007", "00800000", "关联总电能");
//		
		
		 byte[] inCode=new byte[]{0x00,0x00,0x00,0x00};
		
		 for (byte i = 0; i < 0xA; i++) {
			inCode[2]=i;
			for (byte j = 0; j < 0x3F; j++) {
				inCode[1]=j;
				connection.registerField("DTL645-2007", OperationHelper.bytesToHex(inCode), "采集点"+ OperationHelper.bytesToHex(inCode));
				System.out.println( "写入"+OperationHelper.bytesToHex(inCode));
			}
		 }
		
		
		long time=LocalDateTime.of(2017, 10, 5, 0, 30, 2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		
		
//		writer.writeData(DataType.NEEDED, "DTL645-2007", "No.041708762392", "00800000", time, 5.0);
//		writer.writeData(DataType.ENERGY, "DTL645-2007", "No.041708762392", "00000100", time, 6.0);
//		writer.writeData(DataType.OTHER, "DTL645-2007", "No.041708762392", "00000000", time, 7.0);
//		
//		writer.batchWriteData(DataType.NEEDED, "DTL645-2007", "No.041708762393", new String[] {"00800000"}, time, new double[] {5.0});
		writer.close();
		
//		IResultSetIterator iterator=reader.getData(DataType.NEEDED, "DTL645-2007", new String[]{"No.041708762392"}, new String[]{"00800000"}, time-10, time+10, 5);
//		System.out.println("energy: meter\ttype\ttime\tvalue");
//		while(iterator.hasNext()){
//			System.out.println(iterator.getMeterShapeCode()+"\t"+iterator.getFieldShapeCode()+"\t"+iterator.getTimestamp()+"\t"+iterator.getDoubleValue());
//		}
		reader.close();
		
		connection.close();
		
		
		
	}
}
