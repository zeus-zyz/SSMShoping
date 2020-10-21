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
 * @date 2020年10月21日 下午9:25:38
 */
public class FastDfsTest {

	@Test
	public void testUpload() throws Exception{
		//创建一个配置文件 其中有tracker服务器的地址
		//使用全局对象加载配置文件
		ClientGlobal.init("E:/rope/SSMShoping/e3-manager-web/src/main/resources/conf/client.conf");
		//创建一个trackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//通过trackerClient获得一个trackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个storageServer的引用，可以为null
		StorageServer storageServer=null;
		//创建一个storageClient，参数需要trackerServer和storageServer
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		//使用storageClient上传文件
		String[] strings = storageClient.upload_file("F:/Desktop/1.png", "png", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
}
