package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.Content;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月22日 下午9:43:25
 */
@Controller
public class IndexController {
	
	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_LUNBO_ID;
	
	@Autowired
	ContentService contentService;
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		//查询内容列表
		List<Content> ad1List = contentService.getContentListByCid(CONTENT_LUNBO_ID);
		//把结果传递改页面
		model.addAttribute("ad1List",ad1List);
		return "index";
	}
}
