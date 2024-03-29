package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	ItemCatService itemCatService;
	
	/**
	 * 查询商品节点列表
	 * @param parentId 商品父ID(默认为0)
	 * @return
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		//查询商品节点列表
		List<EasyUITreeNode> list = itemCatService.getCatList(parentId);
		return list;
	}
	
	/*@RequestMapping("/item/param/list")
	@ResponseBody
	private EasyUIDataGridResult getItemCatList(int page,int rows){
		//查询商品信息
		 EasyUIDataGridResult list = itemCatService.getItemCatList(page, rows);
		return list;
	}*/
}
