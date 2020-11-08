package cn.e3mall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.lf5.viewer.configure.MRUFileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.common.untils.JsonUtils;
import cn.e3mall.sso.service.TokenService;

/**
 * @Description: 根据token查询用户信息Controller
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月7日 下午9:14:06
 */
@Controller
public class TokenController {

	@Autowired
	TokenService tokenService;
	
	@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE/*"application/json;charset=utf-8"*/)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback){
		E3Result result = tokenService.getUserByToken(token);
		if(StringUtils.isNoneBlank(callback)){
			return callback+"("+JsonUtils.objectTOJson(result)+");";
		}
		return JsonUtils.objectTOJson(result);
	}
	
	
	/**
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback){
		E3Result result = tokenService.getUserByToken(token);
		//响应结果之前，判断是否为jsonp请求
		if(StringUtils.isNoneBlank(callback)){
			//把结果封装成一个js语句响应
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}
	**/
}
