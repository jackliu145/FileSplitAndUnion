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
 * �ļ��ϲ�����properties�ļ��ж�ȡ�ļ���Ϣ
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

			// ��ȡ�ļ��ĸ���
			int count = Integer.parseInt(prop.getProperty("count"));
			System.out.println(count);

			// ����������ӵ�������
			Vector<InputStream> vector = new Vector<InputStream>();
			for (int i = 0; i < count; i++) {
				vector.add(new FileInputStream(prop.getProperty("Block" + i)));
			}
			
			//�ϲ�������
			SequenceInputStream sis = new SequenceInputStream(vector.elements());
			
			//��ȡԭʼ�ļ���
			String fileName = prop.getProperty("fileName");
			
			//д�뵽ָ���ļ���
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
