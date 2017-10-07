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
 * ���ļ��ָ��С�飬�������8��Ŀ¼�С�
 * @author jack
 *
 */
public class FileSplit {
	
	private static final String FILENAME = "E:/xuexi/456.mp4";
	private static final String DESTPATH = "E:/split";
	
	public static void main(String[] args) {
		//��ȡ�ļ�
		File file = new File(FILENAME);
		InputStream in = null;
		try {
			//��Ӳ���ļ���ȡ���ڴ�
			in = new FileInputStream(file);
			
			//ʹ��Properties������Ϣ
			Properties prop = new Properties();
			
			//�����ļ���
			prop.setProperty("fileName", file.getName());
			
			//���û�����
			byte[] data = new byte[1024 * 1024 * 10];
			int len = -1;
			
			//�ļ�����������¼�ļ��ָ�ĸ���
			int count = 0;
			
			//д�뵽��ͬ��Ŀ¼��
			while ((len = in.read(data)) != -1) {
				String path = DESTPATH;		//����Ҫд���Ŀ¼
				//32λ���������ֻ��ռ��8λ16��������
				String hc = Integer.toHexString(UUID.randomUUID().hashCode());
				int i = 8 - hc.length();
				//��֤�ļ�д�뵽8����Ŀ¼��
				for (int j = 0; j < i; j++) {
					hc = "0" + hc;
				}
				for (char ch : hc.toCharArray()) {
					path = path + "/" + ch;
				}
				//����Ŀ¼
				File files = new File(path);
				files.mkdirs();
				
				//��������ļ���
				String randomFileName = Integer.toString(UUID.randomUUID().hashCode());
				OutputStream out = new FileOutputStream(path + "/" + randomFileName);
				
				//д�����ݵ��ļ���
				out.write(data, 0, len);
				
				//���ļ������ļ�·����¼��properties��
				prop.setProperty("Block"+count, path + "/" + randomFileName);
				
				//�ļ���¼������
				count++;
				
				//�ر������
				out.close();
			}
			
			//��¼�ָ��ļ�������
			prop.setProperty("count", Integer.toString(count));
			
			//�־û�����
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
