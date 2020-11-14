package cn.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.common.untils.CookieUtils;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.pojo.User;
import cn.e3mall.sso.service.TokenService;

/** 
* @ClassName: LoginInterceptor 
* @Description: 用户登录处理拦截器
* @author: Administrator
* @date: 2020年11月13日 上午10:52:24  
*/
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//前处理，执行handle之前执行此方法
		//返回true，放行  flase:拦截
		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, "token");
		// 如果没有token，未登录状态，直接放行
		if(StringUtils.isBlank(token)){
			return true;
		}
		//取到token，需要调用sso系统的服务，根据token去用户信息
		E3Result e3Result = tokenService.getUserByToken(token);
		//没有取到用户信息。登录过期，直接放行
		if(e3Result.getStatus() != 200){
			return true;
		}
		//取到用户信息，登录状态
		User user=(User)e3Result.getData();
		//把用户信息放到request中，值需要在controller中判断request中是否包含user信息，放行
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// handler执行之后，返回modelAndView之前

	}

	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 完成处理，返回modelAndView之后
		//可以在这个处理异常

	}

}
