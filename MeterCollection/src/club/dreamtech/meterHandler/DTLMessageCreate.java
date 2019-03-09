package club.dreamtech.meterHandler;
import club.dreamtech.meterCommon.*;

public class DTLMessageCreate {
	
	public  DTLMessageCreate() {
		
	}
	
	/**
	 *  生成获取电表通信地址数据报文
	 * @return
	 */
	public byte[] createAddressCode() {
		try {
			byte[] reslut=new byte[14];
			reslut[0]=(byte) 0xFE;
			reslut[1]=(byte) 0xFE;
			reslut[2]=0x68;
			reslut[3]=(byte) 0xAA;//地址域
			reslut[4]=(byte) 0xAA;
			reslut[5]=(byte) 0xAA;
			reslut[6]=(byte) 0xAA;
			reslut[7]=(byte) 0xAA;
			reslut[8]=(byte) 0xAA;
			reslut[9]=0x68;
			reslut[10]=0x13;//控制码
			reslut[11]=0x00;//数据域长度
			reslut[12]=createCS(reslut);
			reslut[13]=0x16;
			return reslut;
		} catch (Exception e) {
			ErrorLog.writeError("生成获取电表地址报文出错："+e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * 生成获取当时数据报文
	 * @param addressCode 地址码(不需要倒置)addressCode[0],addressCode[1]
	 * @param indentificationCode 标示码(不需要倒置)
	 * @return 数据报文
	 */
	public byte[] createMessage(byte[] addressCode,byte[] indentificationCode) {
		try {
			byte[] reslut=new byte[18];
			reslut[0]=(byte) 0xFE;
			reslut[1]=(byte) 0xFE;
			reslut[2]=(byte) 0x68;
			for (int i = 0; i < 6; i++) {
				reslut[3+i]=addressCode[5-i];
			}
			reslut[9]=(byte) 0x68;
			reslut[10]=(byte) 0x11;
			reslut[11]=(byte) 0x04;
			for (int i = 0; i < 4; i++) {
				reslut[12+i]=(byte) (indentificationCode[3-i]+0x33);
			}
			reslut[16]=createCS(reslut);
			reslut[17]=(byte) 0x16;
			return reslut;
		} catch (Exception e) {
			ErrorLog.writeError("生成获取当时数据报文出错："+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 生成获取当时数据报文
	 * @param addressCode 地址码(不需要倒置)
	 * @param indentificationCode 标示码(不需要倒置)
	 * @param n 块数
	 * @return 数据报文
	 */
	public byte[] createMessage(byte[] addressCode,byte[] indentificationCode,byte n) {
		try {
			byte[] reslut=new byte[19];
			reslut[0]=(byte) 0xFE;
			reslut[1]=(byte) 0xFE;
			reslut[2]=(byte) 0x68;
			for (int i = 0; i < 6; i++) {
				reslut[3+i]=addressCode[5-i];
			}
			reslut[9]=(byte) 0x68;
			reslut[10]=(byte) 0x11;
			reslut[11]=(byte) 0x05;
			for (int i = 0; i < 4; i++) {
				reslut[12+i]=indentificationCode[3-i];
			}
		    reslut[16]=n;
			reslut[17]=createCS(reslut);
			reslut[18]=(byte) 0x16;
			return reslut;
		} catch (Exception e) {
			ErrorLog.writeError("生成获取当时数据报文出错："+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 生成获取当时数据报文
	 * @param addressCode 地址码(不需要倒置)
	 * @param indentificationCode 标示码(不需要倒置)
	 * @param n 块数
	 * @param time 时间(年月日时分)
	 * @return 数据报文
	 */
	public byte[] createMessage(byte[] addressCode,byte[] indentificationCode,byte n,byte[] time) {
		try {
			byte[] reslut=new byte[19];
			reslut[0]=(byte) 0xFE;
			reslut[1]=(byte) 0xFE;
			reslut[2]=(byte) 0x68;
			for (int i = 0; i < 6; i++) {
				reslut[3+i]=addressCode[5-i];
			}
			reslut[9]=(byte) 0x68;
			reslut[10]=(byte) 0x11;
			reslut[11]=(byte) 0x0A;
			for (int i = 0; i < 4; i++) {
				reslut[12+i]=indentificationCode[3-i];
			}
		    reslut[16]=n;
			for (int i = 0; i < 6; i++) {
				reslut[17+i]=time[5-i];
			}
			reslut[17]=createCS(reslut);
			reslut[18]=(byte) 0x16;
			return reslut;
		} catch (Exception e) {
			ErrorLog.writeError("生成获取当时数据报文出错："+e.getMessage());
			return null;
		}
	}


	
	/**
	 * 生成CS验证码
	 * @param messageArray 需要生成校验码的byte数组
	 * @return CS验证码
	 */
	private byte createCS(byte[] messageArray) {
		int result=0;
		for (int i = 2; i < messageArray.length-2; i++) {
			result+=messageArray[i];
		}
		return (byte)result;
	}
}
