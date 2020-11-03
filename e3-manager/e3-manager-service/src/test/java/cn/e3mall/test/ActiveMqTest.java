package cn.e3mall.test;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月1日 下午1:33:58
 */
public class ActiveMqTest {

	//@Test
	public void testQueueProducer() throws Exception{
		//创建connectionFactory对象，需要指定服务端ip机端口号
		//brokerURL 服务器的IP机端口号
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.97.24.99:61616");
		//使用connectionFactory对象创建一个connection对象
		Connection connection = connectionFactory.createConnection();
		//开启连接，调用connection对象的start方法
		connection.start();
		//使用connection对象创建一个session对象
		//第一个参数：是否开启事务，(true: 开启事务，则第二个参数忽略)
		//第二个参数：第一个参数为false时，才有意义。消息的应答模式：1.自动应答 2.手动应答。 一般是自动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用session对象创建一个Destination对象（topic、queue）
		//参数：队列的名称
		Queue queue = session.createQueue("test-queue");
		//使用session对象创建一个producer对象
		MessageProducer producer = session.createProducer(queue);
		//使用session创建一个message对象，或创建一个textMessage对象
		/*TextMessage message = new ActiveMQTextMessage();
		message.setText("hello activeMq,this is my first test.");*/
		TextMessage textMessage = session.createTextMessage("hello activeMq,this is my first test");
		//使用producer对象发送消息
		producer.send(textMessage);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	//@Test
	public void testQueueConsumer() throws Exception{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.97.24.99:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用Session对象创建一个Destination对象。和发送端保持一致queue，并且队列的名称一致。
		Queue queue = session.createQueue("test-queue");
		//使用session对象创建一个consumer对象
		MessageConsumer consumer = session.createConsumer(queue);
		//接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try{
					TextMessage textMessage=(TextMessage)message;
					String text=null;
					//取消息的内容
					text=textMessage.getText();
					//打印消息
					System.out.println(text);
				}catch(JMSException e){
					e.printStackTrace();
				}
			}
		});
		//登录键盘输入
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	//@Test
	public void testTopicProducer() throws Exception{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.97.24.99:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageProducer producer = session.createProducer(topic);
		/*
		 TextMessage message = new ActiveMQTextMessage();
		 message.setText("hello activeMq,this is my first test.");
		 */
		TextMessage textMessage = session.createTextMessage("hello activeMq,this is my topic test");
		producer.send(textMessage);
		producer.close();
		session.close();
		connection.close();
	}
	
	//@Test
	public void testTopicConsumer() throws Exception{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.97.24.99:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try{
					TextMessage textMessage=(TextMessage)message;
					String text=null;
					text=textMessage.getText();
					System.out.println(text);
				}catch(JMSException e){
					e.printStackTrace();
				}
			}
		});
		System.out.println("topic的消费端03.....");
		System.in.read();
		consumer.close();
		session.close();
		connection.close();
	}
}
