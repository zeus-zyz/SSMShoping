package cn.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月4日 下午9:09:36
 */
@Controller
public class HtmlGenController {
 
	@Autowired
	FreeMarkerConfigurer freeMarkerConfigurer;
	
	@RequestMapping("/genhtml")
	public String genHtml() throws Exception{
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		//加载模板
		Template template = configuration.getTemplate("hello.ftl");
		//创建数据集
		Map data=new HashMap<>();
		data.put("heelo", 123456789);
		//指定文件输出的路径及文件名
		FileWriter out = new FileWriter(new File("E:/rope/SSMShoping/e3-freemarker/src/freemarker/hello123.html"));
		//输出文件
		template.process(data, out);
		//关闭流
		out.close();
		return "OK";
	}
}
