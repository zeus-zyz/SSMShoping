package cn.e3mall.content.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月27日 上午10:57:17
 */
public class JedisClientTest {
	
	@Test
	public void testJedisClient(){
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		Long hdel = jedisClient.del("CONTENT_LIST");
		System.out.println(hdel);
		//jedisClient.set("mytest", "jedisClient");
		//String string = jedisClient.get("mytest");
		//System.out.println(string);
	}
}
