package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.mapper.ItemMapper;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemExample;
import cn.e3mall.pojo.ItemExample.Criteria;
import cn.e3mall.service.ItemService;
/**
 * 商品管理Service
 * @author Administrator
 *	
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemMapper itemMapper;
	
	@Override
	public Item getItemById(long itemId) {
		//根据主键查询
		Item item = itemMapper.selectByPrimaryKey(itemId);
		//ItemExample example = new ItemExample();
		//Criteria criteria = example.createCriteria();
		//设置查询条件
		//criteria.andIdEqualTo(itemId);
		//执行查询
		//List<Item> list = itemMapper.selectByExample(example);
		/*if(list != null && list.size() > 0){
			return list.get(0);
		}*/
		return item;
	}

}
