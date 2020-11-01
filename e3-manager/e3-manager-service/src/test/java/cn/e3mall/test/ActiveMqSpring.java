package cn.e3mall.test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月1日 下午5:40:21
 */
public class ActiveMqSpring {
	
	@Test
	public void testQueueProducer()throws Exception{
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		Queue queue =(Queue) applicationContext.getBean("queueDestination");
		jmsTemplate.send(queue,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage("spring activemq test");
				return textMessage;
			}
		});
	}
}
