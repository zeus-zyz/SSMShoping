package cn.e3mall.common.untils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.net.URLCodec;

/**
 * @Description: cookie工具类
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月6日 下午4:40:25
 */
public final class CookieUtils {

	/**
	 * 
	* @Title: getCookieValue 
	* @Description: 得到Cookie，不编码
	* @return String
	* @author Administrator
	* @date 2020年11月6日下午4:57:51
	 */
	public static String getCookieValue(HttpServletRequest request,String cookieName){
		return getCookieValue(request,cookieName,false);
	}
	
	/**
	 * 
	* @Title: getCookieValue 
	* @Description: 得到cookie的值
	* @param request
	* @param cookieName
	* @param isDecoder
	* @return String
	* @author Administrator
	* @date 2020年11月6日下午7:31:12
	 */
	public static String getCookieValue(HttpServletRequest request,String cookieName, boolean isDecoder){
		Cookie[] cookieList = request.getCookies();
		if(cookieList == null || cookieName ==null){
			return null;
		}
		String reValue= null;
		try{
			for(int i= 0;i < cookieList.length; i++){
				if(cookieList[i].getName().equals(cookieName)){
					if(isDecoder){
						reValue=URLDecoder.decode(cookieList[i].getValue(),"uTF-8");
					}else{
						reValue=cookieList[i].getValue();
					}
					break;
				}
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return reValue;
	}
	
	/**
	 * 
	* @Title: getCookieValue 
	* @Description: 得到cookie的值 
	* @param request
	* @param cookieName
	* @param encodeString
	* @return String
	* @author Administrator
	* @date 2020年11月6日下午7:39:29
	 */
	public static  String getCookieValue(HttpServletRequest request,String cookieName,String encodeString){
		Cookie[] cookieList = request.getCookies();
		if(cookieList == null || cookieName == null){
			return null;
		}
		String retValue=null;
		try{
			for (int i = 0; i < cookieList.length; i++) {
				if(cookieList[i].getName().equals(cookieName)){
					retValue=URLDecoder.decode(cookieList[i].getValue(),encodeString);
					break;
				}
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return retValue;
	}
	
	/**
	 * 
	* @Title: setCookie 
	* @Description: 设置cookie的值，不设置生效时间默认浏览器关闭及失效，也不编码
	* @param request
	* @param response
	* @param cookieName
	* @param cookieValue void
	* @author Administrator
	* @date 2020年11月6日下午8:00:44
	 */
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String cookieName,
			String cookieValue){
			setCookie(request, response, cookieName, cookieValue,-1);
	}
	
	/**
	 * 
	* @Title: setCookie 
	* @Description: 设置cookie的值，在指定时间生效，但不编码 
	* @param request
	* @param response
	* @param cookieName
	* @param cookieValue
	* @param cookieMaxage void
	* @author Administrator
	* @date 2020年11月6日下午8:06:08
	 */
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String cookieName,
			String cookieValue, int cookieMaxage){
		setCookie(request, response, cookieName, cookieValue, cookieMaxage,false);
	}
	
	/**
	 * 
	* @Title: setCookie 
	* @Description: 设置cookie的值，不设置生效时间，但编码 
	* @param request
	* @param response
	* @param cookieName
	* @param cookieValue
	* @param isEncode void
	* @author Administrator
	* @date 2020年11月6日下午8:10:42
	 */
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String cookieName,
			String cookieValue,boolean isEncode){
		setCookie(request, response, cookieName, cookieValue, -1, isEncode);
	}
	
	/**
	 * 
	* @Title: setCookie 
	* @Description: 设置cookie的值，在指定时间内生效，编码参数
	* @param request
	* @param response
	* @param cookieName
	* @param cookieValue
	* @param cookieMaxage
	* @param isEncode void
	* @author Administrator
	* @date 2020年11月6日下午8:17:25
	 */
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String cookieName,
			String cookieValue,int cookieMaxage,boolean isEncode){
		doSetCookie(request,response,cookieName,cookieValue,cookieMaxage,isEncode);
	}
	
	/**
	 * 
	* @Title: setCookie 
	* @Description: 设置cookie的值，在指定时间内生效，编码参数(指定编码) 
	* @param request
	* @param response
	* @param cookieName
	* @param cookieValue
	* @param cookieMaxage
	* @param encodeString void
	* @author Administrator
	* @date 2020年11月6日下午8:22:27
	 */
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String cookieName,
			String cookieValue,int cookieMaxage,String encodeString){
		doSetCookie(request,response,cookieName,cookieValue,cookieMaxage,encodeString);
	}
	
	/**
	 * 
	* @Title: deletCookie 
	* @Description: 删除cookie带cookie域名 
	* @param request
	* @param response
	* @param cookieName void
	* @author Administrator
	* @date 2020年11月6日下午8:25:31
	 */
	public static void deletCookie(HttpServletRequest request,HttpServletResponse response,
			String cookieName){
		doSetCookie(request,response,cookieName,"",-1,false);
	}
	
	/**
	 * 
	* @Title: doSetCookie 
	* @Description: 设置Cookie的值，并使其指定时间内生效 
	* @param request
	* @param response
	* @param cookieName
	* @param cookieValue
	* @param cookieMaxage cookie生效的最大秒数
	* @param isEncode void
	* @author Administrator
	* @date 2020年11月6日下午8:37:22
	 */
	private static final void doSetCookie(HttpServletRequest request,HttpServletResponse response,
			String cookieName,String cookieValue,int cookieMaxage,boolean isEncode){
		try{
			if(cookieValue == null){
				cookieValue="";
			}else if(isEncode){
				cookieValue=URLEncoder.encode(cookieValue,"UTF-8");
			}
			Cookie cookie=new Cookie(cookieName, cookieValue);
			if(cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if(null != request){
				//设置域名的cookie
				String domainName=getDmianName(request);
				System.out.println(domainName);
				if(!"localhost".endsWith(domainName)){
					cookie.setDomain(domainName);
				}
			}
			cookie.setPath("/");
			response.addCookie(cookie);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	* @Title: doSetCookie 
	* @Description:  设置cookie的值，并使其在指定时间内生效
	* @param request
	* @param response
	* @param cookieName
	* @param cookieValue
	* @param cookieMaxage cookie生效的最大秒数
	* @param encodeString void
	* @author Administrator
	* @date 2020年11月6日下午8:52:14
	 */
	private static final void doSetCookie(HttpServletRequest request,HttpServletResponse response,
			String cookieName,String cookieValue,int cookieMaxage,String encodeString){
		try{
			if(cookieValue == null){
				cookieValue="";
			}else {
				cookieValue=URLEncoder.encode(cookieValue,encodeString);
			}
			Cookie cookie=new Cookie(cookieName, encodeString);
			if(cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if(null != request){
				//设置域名的cookie
				String domainName=getDmianName(request);
				System.out.println(domainName);
				if(!"localhost".equals(domainName)){
					cookie.setDomain(domainName);
				}
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: getDmianName 
	* @Description: 得到cookie的域名 
	* @param request
	* @return String
	* @author Administrator
	* @date 2020年11月6日下午9:13:05
	 */
	private static final String getDmianName(HttpServletRequest request){
		String domainName=null;
		String serverName = request.getRequestURL().toString();
		if(serverName == null || serverName.equals("")){
			domainName="";
		}else {
			serverName = serverName.toLowerCase();
			serverName=serverName.substring(7);
			final int end = serverName.indexOf("/");
			serverName=serverName.substring(0,end);
			final String[] domains = serverName.split("\\.");
			int len = domains.length;
			if(len > 3){
				// www.xxx.com.cn
				domainName="."+domains[len - 3]+"."+domains[len - 2]+"."+domains[len - 1];
			}else if (len <= 3 && len > 1) {
				// xxx.com OR xxx.cn
				domainName="."+domains[len - 2]+"."+domains[len - 1];
			}else{
				domainName=serverName;
			}
		}
		if(domainName != null && domainName.indexOf(":")> 0){
			String[] ary = domainName.split("\\:");
			domainName=ary[0];
		}
		return domainName;
	}
}
