package cn.e3mall.test.fast;


import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020��10��21�� ����9:25:38
 */
public class FastDfsTest {

	@Test
	public void testUpload() throws Exception{
		//����һ�������ļ� ������tracker�������ĵ�ַ
		//ʹ��ȫ�ֶ�����������ļ�
		ClientGlobal.init("E:/rope/SSMShoping/e3-manager-web/src/main/resources/conf/client.conf");
		//����һ��trackerClient����
		TrackerClient trackerClient = new TrackerClient();
		//ͨ��trackerClient���һ��trackerServer����
		TrackerServer trackerServer = trackerClient.getConnection();
		//����һ��storageServer�����ã�����Ϊnull
		StorageServer storageServer=null;
		//����һ��storageClient��������ҪtrackerServer��storageServer
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		//ʹ��storageClient�ϴ��ļ�
		String[] strings = storageClient.upload_file("F:/Desktop/1.png", "png", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
}
