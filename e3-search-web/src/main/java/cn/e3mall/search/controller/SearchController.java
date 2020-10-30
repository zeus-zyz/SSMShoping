package cn.e3mall.search.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月29日 下午7:24:43
 */
@Controller
public class SearchController {

	@Autowired
	SearchService searchService;
	
	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;
	
	@RequestMapping("/search")
	public String searchItemList(String keyword,
			@RequestParam(defaultValue="1") Integer page,Model model ) throws Exception{
		keyword=new String(keyword.getBytes("iso-8859-1"),"utf-8");
		SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", searchResult.getRecordCount());
		model.addAttribute("itemList", searchResult.getItemList());
		return "search";
	}
}
