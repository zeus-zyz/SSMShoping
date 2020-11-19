package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.ItemParam;
import cn.e3mall.service.ItemParamService;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月15日 上午10:26:02
 */
@Controller
public class ItemParamController {

	@Autowired
	ItemParamService itemParamService;

	//@RequestMapping("/item/param/list")
	//@ResponseBody
	public List<ItemParam>  getItemParamList(int page,int rows){
		//查询商品信息
		 List<ItemParam> list = itemParamService.getItemParam(page, rows);
		return list;
	}
}
