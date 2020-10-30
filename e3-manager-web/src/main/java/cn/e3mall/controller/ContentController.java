package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.Content;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月24日 下午2:43:13
 */
@Controller
public class ContentController {

	@Autowired
	ContentService contentService;
	
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(Content content){
		E3Result e3Result = contentService.addContent(content);
		return e3Result;
	}
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Long categoryId,Integer page, Integer rows){
		return contentService.getItemList(categoryId, page, rows);
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result delContent(String ids){
		E3Result result = contentService.delContent(ids);
		return result;
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result updateContent(Content content){
		E3Result result = contentService.updateContent(content);
		return result;
	}
}
