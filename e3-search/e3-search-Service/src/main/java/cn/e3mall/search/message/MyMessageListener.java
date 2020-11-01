package cn.e3mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月1日 下午5:59:29
 */
public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage=(TextMessage)message;
		try{
			String text = textMessage.getText();
			System.out.println(text);
		}catch(JMSException e){
			e.printStackTrace();
		}
	}

}
