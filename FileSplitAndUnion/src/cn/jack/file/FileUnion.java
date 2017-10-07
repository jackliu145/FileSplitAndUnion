package cn.jack.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * 文件合并，从properties文件中读取文件信息
 * 
 * @author jack
 *
 */
public class FileUnion {
	private static final String DEST = "E:/";
	
	public static void main(String[] args) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("p.properties"));

			// 获取文件的个数
			int count = Integer.parseInt(prop.getProperty("count"));
			System.out.println(count);

			// 将输入流添加到容器中
			Vector<InputStream> vector = new Vector<InputStream>();
			for (int i = 0; i < count; i++) {
				vector.add(new FileInputStream(prop.getProperty("Block" + i)));
			}
			
			//合并流对象
			SequenceInputStream sis = new SequenceInputStream(vector.elements());
			
			//获取原始文件名
			String fileName = prop.getProperty("fileName");
			
			//写入到指定文件中
			OutputStream out = new FileOutputStream(DEST + fileName);
			byte[] data = new byte[1024 * 1024 * 10];
			int len = -1;
			while ((len = sis.read(data)) != -1) {
				out.write(data, 0, len);
			}
			
			sis.close();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
