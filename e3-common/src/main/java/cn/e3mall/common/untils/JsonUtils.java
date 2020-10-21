package cn.e3mall.common.untils;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description: 自定义响应结构
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月21日 下午5:09:15
 */

public class JsonUtils {

	// 定义Jackson对象
	private static final ObjectMapper MAPPER= new ObjectMapper();
	
	/**
	 * 将对象转化成json字符串
	 * @param data
	 * @return
	 */
	public static String objectTOJson(Object data){
		try{
			String string = MAPPER.writeValueAsString(data);
			return string;
		}catch(JsonProcessingException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将json结果集转化为对象
	 * @param jsonData json数据
	 * @param beanType 对象中object类型
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData,Class<T> beanType){
		try{
			T t = MAPPER.readValue(jsonData, beanType);
			return t;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将json数据转换成pojo对象list
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T>List<T> jsonToList(String jsonData,Class<T> beanType){
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		try{
			List<T> list = MAPPER.readValue(jsonData, javaType);
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
