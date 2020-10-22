package cn.e3mall.test.fast;


import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.untils.FastDFSClient;

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
	public void testUpload() throws Exception {
		//����һ�������ļ����ļ������⡣���ݾ���tracker�������ĵ�ַ��
		//ʹ��ȫ�ֶ�����������ļ���
		ClientGlobal.init("E:\\rope\\SSMShoping\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
		//����һ��TrackerClient����
		TrackerClient trackerClient = new TrackerClient();
		//ͨ��TrackClient���һ��TrackerServer����
		TrackerServer trackerServer = trackerClient.getConnection();
		//����һ��StrorageServer�����ã�������null
		StorageServer storageServer = null;
		//����һ��StorageClient��������ҪTrackerServer��StrorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//ʹ��StorageClient�ϴ��ļ���
		String[] upload_file = storageClient.upload_file("D:/images/log/9a64fce785.jpg", "jpg", null);
		for (String string : upload_file) {
			System.out.println(string);
		}
	}
	
	
	@Test
	public void testFastDFSClient() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("E:\\rope\\SSMShoping\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
		String string = fastDFSClient.uploadFile("F:\\1.png");
		System.out.println(string);
	}
}
