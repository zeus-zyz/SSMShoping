package cn.e3mall.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.User;

/**
 * @Description: 订单管理Controller
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月17日 下午8:00:18
 */
@Controller
public class OrderController {

	@Autowired
	CartService cartService;
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request){
		//取用户ID
		User  user=(User)request.getAttribute("user");
		//根据用户ID取收货地址列表
		//使用静态数据模拟
		//取支付方式列表
		//使用静态数据的支付方式
		//根据购物车列表传递给指定的页面
		List<Item> cartList = cartService.getCartList(user.getId());
		//把购物车列表传递给指定的页面
		request.setAttribute("cartList", cartList);
		//返回指定的页面
		return "order-cart";
	}
	
	@RequestMapping(value="/order/create",method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
		//取用户信息
		User user=(User)request.getAttribute("user");
		//把用户信息添加到OrderInfo对象中
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//调用服务生成订单
		E3Result e3Result = orderService.createOrder(orderInfo);
		//如果订单生成成功，需要删除购物车
		if(e3Result.getStatus()==200){
			//清空购物车
			cartService.clearCartItem(user.getId());
		}
		//把订单号传递给页面
		request.setAttribute("orderID", e3Result.getData());
		request.setAttribute("payment", orderInfo.getPayment());
		//返回指定的页面
		return "success";
	}
}
