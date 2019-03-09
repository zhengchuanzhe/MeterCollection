package club.dreamtech.meterMain;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import club.dreamtech.meterCommon.*;
import club.dreamtech.meterHandler.DTLMessageAnalysis;
import club.dreamtech.meterHandler.MeterCollectionHandler;
import club.dreamtech.meterModel.CollectionModel;

import java.time.ZoneId;
import java.util.Map;

import cn.edu.tsinghua.sql.Connection;
import cn.edu.tsinghua.sql.ConnectionFactory;
import cn.edu.tsinghua.sql.DataType;
import cn.edu.tsinghua.sql.IWriter;
import cn.edu.tsinghua.sql.ConnectionFactory.ConnectionType;
import cn.edu.tsinghua.sql.mysql.MySQLWriter;
import cn.edu.tsinghua.sql.IReader;
import cn.edu.tsinghua.sql.IRegister;
import cn.edu.tsinghua.sql.IResultSetIterator;

public class meterMain {
	public static void main(String[] args) throws Exception  {
		
		MeterCollectionHandler md=new MeterCollectionHandler();
		md.startCollection();
	}
}
