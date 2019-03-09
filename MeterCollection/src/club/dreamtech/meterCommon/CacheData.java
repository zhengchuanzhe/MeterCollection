package club.dreamtech.meterCommon;


import java.util.Hashtable;
import java.util.List;

import club.dreamtech.meterModel.ClientModel;

public class CacheData {
	private static String[] errorMassageData=new String[]{"其他错误","无请求数据","密码错误／未授权","通讯速率不能改变","","",""};
	/**
	 * 获取错误信息字
	 * @return
	 */
	public static String[] getErrorMassageData() {
		return errorMassageData;
		
	}
	
	
	
	public static Hashtable<String,ClientModel> SocketClientMap=new Hashtable<String,ClientModel>();
	
	public static Hashtable<String,List<String>> ipAndMeterMapHashtable=new  Hashtable<String,List<String>>();
	
//	public static ConcurrentHashMap<Socket,String> SocketClientMap=new ConcurrentHashMap<Socket,String>();
//	public static ConcurrentHashMap<Socket,String> SocketClientMap=new ConcurrentHashMap<Socket,String>();
	
}
