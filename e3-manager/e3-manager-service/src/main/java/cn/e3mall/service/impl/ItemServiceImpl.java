package cn.e3mall.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.ItemMapper;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemExample;
import cn.e3mall.service.ItemService;
import cn.e3mall.untils.EasyUIDataGridResult;
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

	@Override
	public EasyUIDataGridResult getItemsList(Integer page,Integer rows) {
		//设置分页信息
		PageHelper.startPage(page,rows);
		//创建一个返回值对象
		ItemExample example = new ItemExample();
		List<Item> list = itemMapper.selectByExample(example);
		//取分页结果
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		//去总记录数
		long total = pageInfo.getTotal();
		//创建一个返回对象
		EasyUIDataGridResult result = new EasyUIDataGridResult(total,list);
		return result;
	}

}
