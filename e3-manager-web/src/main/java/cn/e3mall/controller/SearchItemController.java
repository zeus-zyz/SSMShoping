package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.search.service.SearchItemService;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月29日 下午7:39:43
 */
@Controller
public class SearchItemController {
	
	@Autowired
	SearchItemService searchItemService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList(){
		E3Result e3Result = searchItemService.importAllItems();
		return e3Result;
	}
}
