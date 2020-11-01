package cn.e3mall.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月1日 下午12:56:58
 */
public class PublishTest {

	@Test
	public void publishService() throws Exception{
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		System.out.println("服务已经启动......");
		System.in.read();
		System.out.println("服务已经关闭.......");
	}
}
