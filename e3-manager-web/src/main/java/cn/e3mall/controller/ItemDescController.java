package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.pojo.ItemDesc;
import cn.e3mall.service.ItemDescService;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月28日 下午6:52:28
 */
@Controller
public class ItemDescController {

	@Autowired
	ItemDescService itemDescService;
	
	@RequestMapping("/item/desc/{id}")
	@ResponseBody
	public E3Result getItemDesc(@PathVariable(value="id")long ids){
		E3Result result = itemDescService.getItemDesc(ids);
		return result;
	}
	
	/**
	 * 
	* @Title: selectItemDesc 
	* @Description: 异步重新加载回显描述 
	* @param id
	* @return ItemDesc
	* @author Administrator
	* @date 2020年11月8日下午2:34:11
	 */
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public ItemDesc selectItemDesc(@PathVariable Long id){
		ItemDesc itemDesc = itemDescService.selectItemDesc(id);
		return itemDesc;
	}
	
	public E3Result queryItemDesc(@PathVariable Long id){
		E3Result result = itemDescService.queryItemDesc(id);
		return result;
	}
}
