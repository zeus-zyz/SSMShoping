package cn.e3mall.common.jedis;

import java.util.List;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月26日 下午8:10:19
 */
public interface JedisClient {
	
	String set(String key,String value);
	
	String get(String key);
	
	Boolean exists(String key);
	
	Long expire(String key,int seconds);
	
	Long ttl(String key);
	
	Long incr(String key);
	
	Long hset(String key,String field,String value);
	
	String hget(String key,String field);
	
	Long hdel(String key,String... fieid);
	
	Boolean hexists(String key,String field);
	
	List<String> hvals(String key);
	
	Long del(String key);

}
