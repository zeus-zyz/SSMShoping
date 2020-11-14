package cn.e3mall.cart.service;

import java.util.List;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.pojo.Item;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月10日 下午4:57:17
 */
public interface CartService {

	E3Result addCart(long userId, long itemId, int num);
	
	E3Result mergeCart(long userId,List<Item> itemList);
	
	List<Item> getCartList(long userId);
	
	E3Result updateCartNum(long userId,long itemId,int num);
	
	E3Result delCartItem(long userId,long itemId);

}
