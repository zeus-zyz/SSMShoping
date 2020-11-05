package cn.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Items;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @Description: 商品详情页面展示controller
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月2日 下午9:09:11
 */
@Controller
public class ItemsController {

	@Autowired
	ItemService itemService;
	
	@Autowired
	FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${HTML_GEN_PATH}")
	private String HTML_GEN_PATH;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId,Model model){
		//调用服务取商品基本信息
				Item tbItem = itemService.getItemById(itemId);
				Items item = new Items(tbItem);
				//取商品描述信息
				ItemDesc itemDesc = itemService.getItemDescById(itemId);
				Map data=new HashMap<>();
				data.put("item", item);
				data.put("itemDesc", itemDesc);
				/*try{
				//加载模板对象
				Configuration configuration = freeMarkerConfigurer.getConfiguration();
				Template template = configuration.getTemplate("item.ftl");
				//创建一个输出流，指定输出的目录及文件名
				FileWriter out = new FileWriter(new File(HTML_GEN_PATH+itemId+".html"));
				//生成静态页面
				template.process(data, out);
				//关闭流
				out.close();
				}catch(Exception e){
					e.printStackTrace();
				}*/
				//把信息传递给页面
				model.addAttribute("item", item);
				model.addAttribute("itemDesc", itemDesc);
				//返回逻辑视图
				return "item";
	}
}
