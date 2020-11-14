/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: cn.e3mall.cart.service.impl 
 * @author: Administrator   
 * @date: 2020年11月12日 下午6:29:11 
 */
package cn.e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.common.untils.JsonUtils;
import cn.e3mall.mapper.ItemMapper;
import cn.e3mall.pojo.Item;

/**
 * @version 1.0
 * @since 
 * @company 
 * @copyright 
* @ClassName: CartServiceImpl 
* @Description: 购物车处理服务
* @author: Administrator
* @date: 2020年11月12日 下午6:29:11  
*/
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	JedisClient jedisClient;
	
	@Autowired
	ItemMapper itemMapper;
	
	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;
	
	@Override
	public E3Result addCart(long userId, long itemId, int num) {
		//向redis中添加购物车
		//数据类型是hash key:用户ID field: 商品id value:商品信息
		//判断商品是否存在
		Boolean hexists = jedisClient.hexists(REDIS_CART_PRE+":"+userId, itemId+"");
		//如果存在数量相加
		if(hexists){
			String json = jedisClient.hget(REDIS_CART_PRE+":"+userId, itemId+"");
			//把json转换item对象
			Item item = JsonUtils.jsonToPojo(json, Item.class);
			item.setNum(item.getNum() +num);
			//写入redis中
			jedisClient.hset(REDIS_CART_PRE+":"+userId, itemId+"", JsonUtils.objectTOJson(json));
			return E3Result.ok();
		}
		//如果不存在，根据商品id去商品信息
		Item item = itemMapper.selectByPrimaryKey(itemId);
		//设置购物车数据量
		item.setNum(num);
		//去一张图片
		String image = item.getImage();
		if(StringUtils.isNoneBlank(image)){
			item.setImage(image.split(",")[0]);
		}
		//添加到购物车列表
		jedisClient.hset(REDIS_CART_PRE+":"+userId, itemId+"", JsonUtils.objectTOJson(item));
		return E3Result.ok();
	}

	@Override
	public E3Result mergeCart(long userId, List<Item> itemList) {
		//遍历商品列表
		//把列表添加到购物车
		//判断购物车中是否有此商品
		//如果有，数量相加
		//如果没有添加新的商品
		for (Item item : itemList) {
			addCart(userId, item.getId(), item.getNum());
		}
		//返回成功
		return E3Result.ok();
	}

	
	@Override
	public List<Item> getCartList(long userId) {
		//根据用户id查询购物车列表
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE+":"+userId);
		List<Item> itemList=new ArrayList<>();
		for (String string : jsonList) {
			//创建一个Item对象
			Item item = JsonUtils.jsonToPojo(string, Item.class);
			//添加到列表
			itemList.add(item);
		}
		return itemList;
	}

	
	@Override
	public E3Result updateCartNum(long userId, long itemId, int num) {
		//从redis中去商品信息
		String json = jedisClient.hget(REDIS_CART_PRE+":"+userId, itemId+"");
		//更新商品数量
		Item item = JsonUtils.jsonToPojo(json, Item.class);
		item.setNum(num);
		//写入redis中
		jedisClient.hset(REDIS_CART_PRE+":"+userId, itemId+"", JsonUtils.objectTOJson(item));
		return E3Result.ok();
	}

	
	@Override
	public E3Result delCartItem(long userId, long itemId) {
		//删除购物车商品
		jedisClient.hdel(REDIS_CART_PRE+":"+userId, itemId+"");
		return E3Result.ok();
	}

}
