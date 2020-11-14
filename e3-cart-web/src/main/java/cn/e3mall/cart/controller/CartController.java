package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.untils.CookieUtils;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.common.untils.JsonUtils;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.User;
import cn.e3mall.service.ItemService;

/**
 * @Description:购物车处理controller
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月10日 下午4:49:54
 */
@Controller
public class CartController {

	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId,@RequestParam(defaultValue="1") Integer num,
			HttpServletRequest request,HttpServletResponse response){
		//判断用户是否登录
		User user=(User)request.getAttribute("user");
		//如果是登录状态，把购物车写入redis中
		if(user !=null){
			//保存到服务端
			cartService.addCart(user.getId(),itemId,num);
			//返回指定的页面
			return "cartSuccess";
		}
		//如果为登录使用cookie
		//从cookie中去购物车列表
		List<Item> cartlist=getCartListFormCookie(request);
		//判断商品在商品列表中是否存在
		boolean flag=false;
		for (Item item : cartlist) {
			//如果存在数量相加
			if(item.getId() == itemId.longValue()){
				flag=true;
				//找到商品，数量相加
				item.setNum(item.getNum()+num);
				//跳出循环
				break;
			}
		}
		//如果不存在
		if(!flag){
			//根据商品id查询商品信息，得到一个item对象
			Item item = itemService.getItemById(itemId);
			//设置商品数量
			item.setNum(num);
			//取一张图片
			String image = item.getImage();
			if(StringUtils.isNoneBlank(image)){
				item.setImage(image.split(",")[0]);
			}
			//把商品添加到商品列表
			cartlist.add(item);
		}
		//写入cookie中
		CookieUtils.setCookie(request, response, "cart",JsonUtils.objectTOJson(cartlist),COOKIE_CART_EXPIRE,true);
		//返回添加成功页面
		return "cartSuccess";
	}
	
	/**
	 * 
	* @Title: getCartListFormCookie 
	* @Description: 从cookie中取购物车列表
	* @param request
	* @return List<Item>
	* @author Administrator
	* @date 2020年11月10日下午8:05:16
	 */
	private List<Item> getCartListFormCookie(HttpServletRequest request) {
		String json = CookieUtils.getCookieValue(request, "cart",true);
		//判断json是否为空
		if(StringUtils.isBlank(json)){
			return new ArrayList<>();
		}
		//把json转换成商品列表
		List<Item> list = JsonUtils.jsonToList(json, Item.class);
		return list;
	}
	
	/**
	 * 
	* @Title: showCatList 
	* @Description: 展现购物车列表 
	* @param request
	* @param response
	* @return String
	* @author Administrator
	* @date 2020年11月12日下午10:50:44
	 */
	@RequestMapping("/cart/cart")
	public String showCatList(HttpServletRequest request,HttpServletResponse response){
		//从cookie中去购物车列表
		List<Item> cartList = getCartListFormCookie(request);
		//判断用户是否为登录状态
		User user =(User) request.getAttribute("user");
		//如果是登录状态
		if(user !=null){
			//从cookie中取购物车列表
			//如果不为空，把cookie中购物车商品和服务端的购物车合并
			cartService.mergeCart(user.getId(), cartList);
			//把cookie中的购物车删除
			CookieUtils.deletCookie(request, response, "cart");
			//从服务端去购物车列表
			cartList = cartService.getCartList(user.getId());
		}
		//把列表传递给页面
		request.setAttribute("cartList", cartList);
		//返回指定的页面
		return "cart";
	}
	
	/**
	 * 
	* @Title: updateCartNum 
	* @Description: 更新购物车商品数量
	* @param itemId
	* @param num
	* @param request
	* @param response
	* @return E3Result
	* @author Administrator
	* @date 2020年11月13日上午10:13:20
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,
			HttpServletRequest request,HttpServletResponse response){
		//判断用户是否为登录状态
		User user=(User)request.getAttribute("user");
		if(user !=null){
			cartService.updateCartNum(user.getId(), itemId, num);
			return E3Result.ok();
		}
		//从cookie中取购物车列表
		List<Item> cartList = getCartListFormCookie(request);
		//遍历商品列表找到对应的商品
		for (Item item : cartList) {
			if(item.getId().longValue() == itemId){
				//更新数量
				item.setNum(num);
				break;
			}
		}
		//把购物车列表写会cookie中
		CookieUtils.setCookie(request, response, "cart",JsonUtils.objectTOJson(cartList),COOKIE_CART_EXPIRE,true);
		//返回成功
		return E3Result.ok();
	}
	
	/**
	 * 
	* @Title: delCartItem 
	* @Description: 删除购物车商品 
	* @param itemId
	* @param request
	* @param response
	* @return String
	* @author Administrator
	* @date 2020年11月13日上午10:31:47
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String delCartItem(@PathVariable Long itemId,HttpServletRequest request,
			HttpServletResponse response){
		//判断用户是否为登录状态
		User user=(User)request.getAttribute("user");
		if(user != null){
			cartService.delCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		//从cookie中取购物车列表
		List<Item> cartList = getCartListFormCookie(request);
		//遍历列表，找到要删除的商品
		for (Item item : cartList) {
			if(item.getId().longValue() == itemId){
				//删除商品
				cartList.remove(item);
				//跳出循环
				break;
			}
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectTOJson(cartList),COOKIE_CART_EXPIRE,true);
		//返回指定的页面
		return "redirect:/cart/cart.html";
	}
}

