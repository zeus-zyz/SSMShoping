package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.mapper.UserMapper;
import cn.e3mall.pojo.User;
import cn.e3mall.pojo.UserExample;
import cn.e3mall.pojo.UserExample.Criteria;
import cn.e3mall.sso.service.RegisterService;

/**
 * @Description: 用户注册处理Service
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月7日 下午2:25:40
 */
@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public E3Result checkData(String param, int type) {
		//根据不同的type生成不同的查询条件
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		//1.用户名 2.手机号 3.邮箱
		if(type == 1){
			criteria.andUsernameEqualTo(param);
		}else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		}else if (type == 3) {
			criteria.andEmailEqualTo(param);
		}else {
			return E3Result.build(400, "数据类型错误！！！");
		}
		//执行查询
		List<User> list = userMapper.selectByExample(example);
		//判断结果中是否包含数据
		if(list != null && list.size() >0){
			//如果有数据返回false
			return E3Result.ok(false);
		}
		//如果没有数据返回true
		return E3Result.ok(true);
	}

	@Override
	public E3Result register(User user) {
		//数据有效性校验
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
				|| StringUtils.isBlank(user.getPhone())){
			return E3Result.build(400, "用户信息不完整，注册失败！！");	
		}
		//1.用户名 2.手机号 3.邮箱
		E3Result result = checkData(user.getUsername(), 1);
		if(!(boolean)result.getData()){
			return E3Result.build(400, "此用户名已经被占用!!!");
		}
		result=checkData(user.getPhone(), 2);
		if(!(boolean)result.getData()){
			return E3Result.build(400, "此手机号已经被注册！！");
		}
		//补全user对象的属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//对密码进行MD5加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		//将用户信息保存到数据库中
		int row = userMapper.insert(user);
		//成功返回成功
		return row >1 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
	}

}
