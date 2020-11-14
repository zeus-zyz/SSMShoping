package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.untils.E3Result;
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
	
	/**
	 *  根据商品ID查询商品
	 * @param itemId 商品ID
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public Item getItemById(@PathVariable Long itemId){
		Item item = itemService.getItemById(itemId);
		return item;
	}
	
	/**
	 *  查询商品信息
	 * @param page 页数 
	 * @param rows 条数
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	private EasyUIDataGridResult getItemsList(int page,int rows){
		//查询商品信息
		 EasyUIDataGridResult list = itemService.getItemsList(page, rows);
		return list;
	}
	
	/**
	 * 保存商品信息
	 * @param item 商品信息
	 * @param desc 商品描述
	 * @return
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(Item item,String desc){
		E3Result result = itemService.addItem(item, desc);
		return result;
	}
	
	/**
	 * 使商品下架
	 * @param ids
	 * @return
	 */
	@RequestMapping("rest/item/instock")
	@ResponseBody
	public E3Result updateInstock(String ids){
		E3Result result=itemService.updateItemInstock(ids);
		return result;
	}
	/**
	 * 使商品上架
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public E3Result updateItemReshelf(String ids){
		E3Result result = itemService.updateItemReshelf(ids);
		return result;
	}
	/**
	 * 使商品为伪删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public E3Result updateItemdelete(String ids){
		E3Result result = itemService.updateItemDel(ids);
		return result;
	}
	
	/**
	 * 
	* @Title: queryById 
	* @Description: 异步重新加载商品信息
	* @param id
	* @return Item
	* @author Administrator
	* @date 2020年11月8日下午2:31:26
	 */
	@RequestMapping("/rest/item/param/item/query/{id}")
	@ResponseBody
	public Item queryById(@PathVariable Long id){
		Item item = itemService.queryItemById(id);
		return item;
	}
}
