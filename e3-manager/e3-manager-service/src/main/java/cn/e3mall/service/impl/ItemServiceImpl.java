package cn.e3mall.service.impl;



import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.common.untils.IDUtils;
import cn.e3mall.mapper.ItemDescMapper;
import cn.e3mall.mapper.ItemMapper;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemDesc;
import cn.e3mall.pojo.ItemExample;
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
	
	@Autowired
	ItemDescMapper itemDescMapper;
	
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
		/*
		 if(list != null && list.size() > 0){
			return list.get(0);
		}
		*/
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemsList(Integer page,Integer rows) {
		//设置分页信息
		PageHelper.startPage(page,rows);
		//创建一个返回值对象
		ItemExample example = new ItemExample();
		List<Item> list = itemMapper.selectByExample(example);
		/*
		 //取分页结果
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		//去总记录数
		long total = pageInfo.getTotal();
		//创建一个返回对象
		EasyUIDataGridResult result = new EasyUIDataGridResult(total,list);
		*/
		//创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//取分页结果
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		//取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

	@Override
	public E3Result addItem(Item item, String desc) {
		// 生成商品id
		long itemId = IDUtils.genItemId();
		// 补全item的属性
		item.setId(itemId);
		// 添加商品的状态(1-正常 2-下架 3-删除)
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//保存商品信息
		itemMapper.insert(item);
		// 创建一个商品描述所对应的对象
		ItemDesc itemDesc = new ItemDesc();
		//补全属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		//保存商品描述
		itemDescMapper.insert(itemDesc);
		//返回成功
		return E3Result.ok();
	}

}
