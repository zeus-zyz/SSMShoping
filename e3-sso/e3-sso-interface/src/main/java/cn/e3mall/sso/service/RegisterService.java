package cn.e3mall.sso.service;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.pojo.User;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月7日 下午2:24:10
 */
public interface RegisterService {
	
	E3Result checkData(String param,int type); 
	
	E3Result register(User user);
}
