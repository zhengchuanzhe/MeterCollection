package club.dreamtech.meterHandler;

import club.dreamtech.meterCommon.CacheData;
import club.dreamtech.meterCommon.ErrorLog;
import club.dreamtech.meterCommon.OperationHelper;
import club.dreamtech.meterModel.*;

public class DTLMessageAnalysis {
	public  DTLMessageAnalysis() {
		
	}
	
	/**
	 * 最大需量及发生时间解析函数
	 * @param message
	 * @param error
	 * @return
	 */
	public CollectionModel maxDemandAnalysis(byte[] message,String error)
	{
		try {
			CollectionModel collectionModel=new CollectionModel();
			if (message[10]!=(byte)0x91) {//异常数据
				error=errorAnalysis(message);
				return null;  
			}
			//电表地址
			byte addressByte[]=new byte[6];
			for (int i = 0; i < addressByte.length; i++) {
				addressByte[5-i]=message[i+3];
			}
			collectionModel.setMeterAddressCode(OperationHelper.bytesToHex(addressByte));
			//标识符
			byte indentificationByte[]=new byte[4];
			for (int i = 0; i < indentificationByte.length; i++) {
				indentificationByte[3-i]=(byte) (message[12+i]-0x33);
			}
			collectionModel.setIndentificationCode(OperationHelper.bytesToHex(indentificationByte));
		
			//获取值
			int dataLength=message[11];
			if (dataLength==8) {
				byte valueByte[]=new byte[4];
				byte timeByte[]=new byte[4];
				for (int i = 0; i <4; i++) {
					valueByte[i]=(byte) (message[16+i]-0x33);
				}
				for (int i = 0; i < 4; i++) {
					timeByte[3-i]=(byte) (message[20+i]-0x33);
				}
				String timeString=OperationHelper.bytesToHex(timeByte);//格式为YYMMDDhhmm
				collectionModel.setValue(OperationHelper.bytesToValue(valueByte, 4));
			}else {
				//没有数据
				collectionModel.setValue("0");
			}
			return collectionModel;
		} catch (Exception e) {
			 error=e.getMessage();
			 ErrorLog.writeError("解析读数据报文出错："+e.getMessage());
			 return null;
		}
	}
	
	/**
	 * 解析读电表数据
	 * @param message 获取到的电能表数据报文 
	 * @param valueLength 数据长度
	 * @param decimalNum 数据小数位长度
	 * @param error 返回错误信息
	 * @return 采集信息（错误返回null）
	 */
	public CollectionModel messageAnalysis(byte[] message,int valueLength,int decimalNum, String error) {
		try {
			CollectionModel collectionModel=new CollectionModel();
			if (message[10]!=(byte)0x91) {//异常数据
				error=errorAnalysis(message);
				return null;  
			}
			//电表地址
			byte addressByte[]=new byte[6];
			for (int i = 0; i < addressByte.length; i++) {
				addressByte[5-i]=message[i+3];
			}
			collectionModel.setMeterAddressCode(OperationHelper.bytesToHex(addressByte));
			//标识符
			byte indentificationByte[]=new byte[4];
			for (int i = 0; i < indentificationByte.length; i++) {
				indentificationByte[3-i]=(byte) (message[12+i]-0x33);
			}
			collectionModel.setIndentificationCode(OperationHelper.bytesToHex(indentificationByte));
		
			//获取值
			int dataLength=message[11];
			if (dataLength>4) {
				byte valueByte[]=new byte[valueLength];
				for (int i = 0; i <valueLength; i++) {
					valueByte[i]=(byte) (message[16+i]-0x33);
				}
				collectionModel.setValue(OperationHelper.bytesToValue(valueByte, decimalNum));
			}else {
				//没有数据
				collectionModel.setValue("0");
			}
			return collectionModel;
		} catch (Exception e) {
			 error=e.getMessage();
			 ErrorLog.writeError("解析读数据报文出错："+e.getMessage());
			 return null;
		}
	}
	
	/**
	 * 读通信地址解析
	 * @param message 获取到的电能表地址报文
	 * @param error 返回错误信息
	 * @return 电表地址
	 */
	public byte[] meterAddressAnalysis(byte[] message,String error) {
		try {
			if (message[10]==(byte)0xD1) {//异常数据
				error=errorAnalysis(message);
				return null;  
			}
			//电表地址
			byte addressByte[]=new byte[6];
			for (int i = 0; i < addressByte.length; i++) {
				addressByte[5-i]=message[i+3];
			}
			return addressByte;
		} catch (Exception e) {
			 error=e.getMessage();
			 ErrorLog.writeError("解析读数据报文出错："+e.getMessage());
			 return null;
		}
	}
	
	/**
	 * 错误信息分析
	 * @param message 信息报文
	 * @return 错误信息
	 */
	private String errorAnalysis(byte[] message) {
		int error=message[12]-0x33;
		String result="错误信息为：";
		for (int i = 0; i < 8; i++) {
			int temp=error%2;
			error=error/2;
			result+=CacheData.getErrorMassageData()[(i+1)*temp-1]+" ";
		}
		ErrorLog.writeError("采集错误——"+result);		
		return result;
	}
}
