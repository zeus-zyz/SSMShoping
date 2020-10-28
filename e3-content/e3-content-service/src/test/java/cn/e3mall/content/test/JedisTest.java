package cn.e3mall.content.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月26日 下午9:49:53
 */
public class JedisTest {

	@Test
	public void testJedis()throws Exception{
		Jedis jedis = new Jedis("47.97.24.99",6379);
		jedis.auth("z98242312@Z");
		jedis.set("test123", "Hello");
		String string = jedis.get("test123");
		System.out.println(string);
		jedis.close();
	}
	
	@Test
	public void testJedisPool() throws Exception{
		JedisPool jedisPool = new JedisPool("47.97.24.99",6379);
		Jedis jedis = jedisPool.getResource();
		jedis.auth("z98242312@Z");
		String string = jedis.get("test123");
		System.out.println(string);
		jedis.close();
		jedisPool.close();
	}
	
	@Test
	public void testJedisCluster() throws Exception{
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("47.97.24.99", 7001));
		nodes.add(new HostAndPort("47.97.24.99", 7002));
		nodes.add(new HostAndPort("47.97.24.99", 7003));
		nodes.add(new HostAndPort("47.97.24.99", 7004));
		nodes.add(new HostAndPort("47.97.24.99", 7005));
		nodes.add(new HostAndPort("47.97.24.99", 7006));
		nodes.add(new HostAndPort("47.97.24.99", 7007));
		nodes.add(new HostAndPort("47.97.24.99", 7008));
		nodes.add(new HostAndPort("47.97.24.99", 7009));
		nodes.add(new HostAndPort("47.97.24.99", 7010));
		JedisCluster jedisCluster = new JedisCluster(nodes,5000,300,10,"z98242312@Z",new JedisPoolConfig());
		jedisCluster.set("test", "hello world!");
		String string = jedisCluster.get("test");
		System.out.println(string);
		jedisCluster.close();
	}
}
