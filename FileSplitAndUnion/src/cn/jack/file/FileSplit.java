package cn.jack.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;

/**
 * 将文件分割成小块，并存放在8级目录中。
 * @author jack
 *
 */
public class FileSplit {
	
	private static final String FILENAME = "E:/xuexi/456.mp4";
	private static final String DESTPATH = "E:/split";
	
	public static void main(String[] args) {
		//读取文件
		File file = new File(FILENAME);
		InputStream in = null;
		try {
			//将硬盘文件读取进内存
			in = new FileInputStream(file);
			
			//使用Properties保存信息
			Properties prop = new Properties();
			
			//保存文件名
			prop.setProperty("fileName", file.getName());
			
			//设置缓冲区
			byte[] data = new byte[1024 * 1024 * 10];
			int len = -1;
			
			//文件计数器，记录文件分割的个数
			int count = 0;
			
			//写入到不同的目录中
			while ((len = in.read(data)) != -1) {
				String path = DESTPATH;		//设置要写入的目录
				//32位的整数最多只会占用8位16进制数据
				String hc = Integer.toHexString(UUID.randomUUID().hashCode());
				int i = 8 - hc.length();
				//保证文件写入到8级别目录中
				for (int j = 0; j < i; j++) {
					hc = "0" + hc;
				}
				for (char ch : hc.toCharArray()) {
					path = path + "/" + ch;
				}
				//创建目录
				File files = new File(path);
				files.mkdirs();
				
				//设置随机文件名
				String randomFileName = Integer.toString(UUID.randomUUID().hashCode());
				OutputStream out = new FileOutputStream(path + "/" + randomFileName);
				
				//写入数据到文件中
				out.write(data, 0, len);
				
				//将文件名和文件路径记录到properties中
				prop.setProperty("Block"+count, path + "/" + randomFileName);
				
				//文件记录器自增
				count++;
				
				//关闭输出流
				out.close();
			}
			
			//记录分割文件的总数
			prop.setProperty("count", Integer.toString(count));
			
			//持久化数据
			prop.store(new FileOutputStream("p.properties"), null);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					in = null;
				}
			}
		}
	}
}
