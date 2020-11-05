package cn.e3mall.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Items;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @Description:监听商品添加消息，生成对应的静态页面
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月4日 下午9:19:29
 */
public class HtmlGenListener implements MessageListener {

	@Autowired
	ItemService itemService;
	
	@Autowired
	FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${HTML_GEN_PATH}")
	private String HTML_GEN_PATH;
	
	@Override
	public void onMessage(Message message) {
		try{
			//创建一个模板，参考jsp
			//消息中取出商品ID
			TextMessage textMessage=(TextMessage)message;
			String text = textMessage.getText();
			Long itemId = new Long(text);
			//等待事务提交
			Thread.sleep(1000);
			//根据商品ID查询商品信息，商品基本信息和商品描述
			Item items = itemService.getItemById(itemId);
			Items item = new Items(items);
			//取商品描述
			ItemDesc itemDesc = itemService.getItemDescById(itemId);
			//创建商品描述
			Map data=new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", itemDesc);
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
		}
	}

}
