package cn.e3mall.order.service;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月17日 下午7:32:58
 */
public interface OrderService {

	E3Result createOrder(OrderInfo orderInfo);
}
