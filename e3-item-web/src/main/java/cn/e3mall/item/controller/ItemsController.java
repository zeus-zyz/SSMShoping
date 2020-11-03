package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.item.pojo.Items;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemDesc;
import cn.e3mall.service.ItemService;

/**
 * @Description: 商品详情页面展示controller
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月2日 下午9:09:11
 */
@Controller
public class ItemsController {

	@Autowired
	ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId,Model model){
		//调用服务取商品基本信息
				Item tbItem = itemService.getItemById(itemId);
				Items item = new Items(tbItem);
				//取商品描述信息
				ItemDesc itemDesc = itemService.getItemDescById(itemId);
				//把信息传递给页面
				model.addAttribute("item", item);
				model.addAttribute("itemDesc", itemDesc);
				//返回逻辑视图
				return "item";
	}
}
