package cn.e3mall.test.activemq;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020��11��1�� ����6:12:05
 */
public class MessageConsumer {

	@Test
	public void testQueueCosumer() throws Exception{
		//��ʼ��spring
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		System.in.read();
	}
}
