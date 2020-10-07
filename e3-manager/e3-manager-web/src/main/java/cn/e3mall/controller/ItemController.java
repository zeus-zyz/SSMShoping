package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.pojo.Item;
import cn.e3mall.service.ItemService;

/**
 * 商品管理ItemController
 * @author Administrator
 *
 */
@Controller
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public Item getItemById(@PathVariable Long itemId){
		Item item = itemService.getItemById(itemId);
		return item;
	}
	
}
