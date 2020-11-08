package cn.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.common.untils.JsonUtils;
import cn.e3mall.mapper.UserMapper;
import cn.e3mall.pojo.User;
import cn.e3mall.pojo.UserExample;
import cn.e3mall.pojo.UserExample.Criteria;
import cn.e3mall.sso.service.LoginService;

/**
 * @Description: 用户登录处理
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月7日 下午3:37:16
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	JedisClient jedisClient;
	
	@Value("${SEESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	@Override
	public E3Result userLogin(String userName, String password) {
		//判断用户和密码是否正确
		//根据用户名出线用户信息
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		//执行查询
		List<User> list = userMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			//返回登录失败
			return E3Result.build(400, "用户名或密码错误！！");
		}
		//取用户信息
		User user=list.get(0);
		//判断密码是否正确
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
			//如果不正确，返回登录失败
			return E3Result.build(400, "用户名或密码错误！！");
		}
		//如果正确生成token
		String token = UUID.randomUUID().toString();
		//把用户信息写入redis中，key：token value：用户信息
		jedisClient.set("SESSION:"+token, JsonUtils.objectTOJson(user));
		//设置session的过期时间
		jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);
		//把token返回
		return E3Result.ok(token);
	}

}
