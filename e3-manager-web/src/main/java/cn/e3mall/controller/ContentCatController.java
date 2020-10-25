package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月24日 下午3:13:57
 */
@Controller
public class ContentCatController {

	@Autowired
	ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(
			@RequestParam(name="id",defaultValue="0") Long parenId){
		List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parenId);
		return list;
	}
	
	/**
	 * 
	* @Title: createcontentCategory 
	* @Description: 添加节点
	* @param parentId
	* @param name
	* @return E3Result
	* @author Administrator
	* @date 2020年10月24日下午3:36:46
	 */
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public E3Result createcontentCategory(Long parentId,String name){
		// 添加节点
		E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
		return e3Result;
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateContentCat(long id,String name){
		E3Result result=contentCategoryService.updateContentCategory(id, name);
		return result;
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public E3Result deleteContentCat(long id){
		E3Result result=contentCategoryService.delContentCategory(id);
		return result;
	}
	
}
