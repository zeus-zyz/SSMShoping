package cn.e3mall.sso.service;

import cn.e3mall.common.untils.E3Result;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月7日 下午9:00:29
 */
public interface TokenService {
	
	E3Result getUserByToken(String token);
}
