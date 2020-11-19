package cn.e3mall.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.mapper.OrderItemMapper;
import cn.e3mall.mapper.OrderMapper;
import cn.e3mall.mapper.OrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.OrderItem;
import cn.e3mall.pojo.OrderShipping;

/**
 * @Description: 订单处理服务
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月17日 下午7:37:22
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	OrderItemMapper orderItemMapper;
	
	@Autowired
	OrderShippingMapper orderShippingMapper;
	
	@Autowired
	JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	
	@Value("${ORDER_ID_START}")
	private String ORDER_ID_SRART;
	
	@Value("${ORDER_DETAIL_ID_GEN_KEY}")
	private String ORDER_DETALL_ID_GEN_KEY;
	
	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		//生成订单号，使用redis的incr生成
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_SRART);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		//补全orderInfo的属性
		orderInfo.setOrderId(orderId);
		//设置orderInfo的状态 1:未付款  2:已付款  3:未发货  4:已发货 5:交易成功 6:交易关闭
		orderInfo.setStatus(1); 
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		// 将数据保存到数据库中
		orderMapper.insert(orderInfo);
		List<OrderItem> orderItems = orderInfo.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			//生成明细id
			String odId = jedisClient.incr(ORDER_DETALL_ID_GEN_KEY).toString();
			//补全对象的属性
			orderItem.setId(odId);
			orderItem.setOrderId(orderId);
			//往明细表中插入数据
			orderItemMapper.insert(orderItem);
		}
		//向订单明细物流表插入数据
		OrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		//返回E3Result中并携带订单号
		return E3Result.ok(orderId);
	}

}
