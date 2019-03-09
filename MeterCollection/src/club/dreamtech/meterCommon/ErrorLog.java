package club.dreamtech.meterCommon;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorLog {
	
	public static boolean writeError(String errorMassage) {
		try {
			Date now=new java.util.Date();
			String path=new SimpleDateFormat("yyyy_MM_dd").format(now)+"_errorLog.txt";
			String time=new SimpleDateFormat("yyyy_MM_dd hh:mm:ss").format(now);
			File file=new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(file,true));  
			out.println(time+"  "+errorMassage); // \r\n即为换行  
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件  
        
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}
}
