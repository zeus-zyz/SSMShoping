package cn.e3mall.service;

import java.util.List;

import cn.e3mall.pojo.ItemParam;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月15日 上午11:10:07
 */
public interface ItemParamService {

	List<ItemParam> getItemParam(int page,int rows);
}
