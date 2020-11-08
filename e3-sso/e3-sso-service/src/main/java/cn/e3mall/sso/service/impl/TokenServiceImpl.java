package cn.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.common.untils.JsonUtils;
import cn.e3mall.pojo.User;
import cn.e3mall.sso.service.TokenService;

/**
 * @Description:根据token取用户信息
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月7日 下午9:02:38
 */
@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	JedisClient jedisClient;
	
	@Value("${SEESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result getUserByToken(String token) {
		//根据token到redis中去用户信息
		String json = jedisClient.get("SESSION:"+token);
		//取不到用户信息，登录已经过期，返回登录过期
		if(StringUtils.isBlank(json)){
			return E3Result.build(201, "用户登录已经过期！");
		}
		//取到用户信息更新token的过期时间
		jedisClient.expire("SESSION"+token, SESSION_EXPIRE);
		//返回结果，E3Result其中包含User对象
		User user = JsonUtils.jsonToPojo(json, User.class);
		return E3Result.ok(user);
	}

}
