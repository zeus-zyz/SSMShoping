package cn.e3mall.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.ItemMapper;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemExample;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月1日 下午1:15:21
 */
public class PageHelperTest {
 
	//@Test
	public void testPageHelper() throws Exception{
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ItemMapper itemMapper = applicationContext.getBean(ItemMapper.class);
		PageHelper.startPage(1, 10);
		ItemExample example = new ItemExample();
		List<Item> list = itemMapper.selectByExample(example);
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
		System.out.println(list.size());
	}
}
