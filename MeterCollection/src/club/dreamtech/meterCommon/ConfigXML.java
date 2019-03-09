package club.dreamtech.meterCommon;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;   
import javax.xml.parsers.DocumentBuilderFactory;   
  

import org.w3c.dom.Document;   
import org.w3c.dom.NodeList;  

/**
 * 获取配置文件信息
 * @author cz
 *
 */
public class ConfigXML {
	
	private static  String getValueByName(String nameString) throws Exception {
	    File file = new File("config.xml");  
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder = factory.newDocumentBuilder();   
		Document doc = builder.parse(file);   
		NodeList valueList = doc.getElementsByTagName(nameString);
		return valueList.item(0).getFirstChild().getNodeValue().trim();  
	}
	
	/**
	 * 获取SQL数据库连接IP
	 * @return
	 * @throws Exception
	 */
	public static String getSQLIp() throws Exception {
		return getValueByName("SQLIP");
	}
	
	/**
	 * 获取SQL数据库连接端口
	 * @return
	 * @throws Exception
	 */
	public static String getSQLPort() throws Exception {
		return getValueByName("SQLPORT");
	}
	
	/**
	 * 获取SQL数据库连接用户名
	 * @return
	 * @throws Exception
	 */
	public static String getSQLUser() throws Exception {
		return getValueByName("USER");
	}
	
	/**
	 * 获取SQL数据库连接密码
	 * @return
	 * @throws Exception
	 */
	public static String getSQLPassWord() throws Exception {
		return getValueByName("PASSWORD");
	}
	
	/**
	 * 获取SQL数据库连接数据库名称
	 * @return
	 * @throws Exception
	 */
	public static String getSQLDatabase() throws Exception {
		return getValueByName("DATABASE");
	}
	
	/**
	 * 获取Socket端口号
	 * @return
	 * @throws Exception
	 */
	public static String getSocketPort() throws Exception {
		return getValueByName("SOCKETPORT");
	}
	
	
	/**
	 * 获取采集间隔时间
	 * @return
	 * @throws Exception
	 */
	public static int getCollectionInterval() throws Exception {
		return Integer.parseInt(getValueByName("INTERVAL"));
	}
}
