package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.mapper.ItemCatMapper;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemCat;
import cn.e3mall.pojo.ItemCatExample;
import cn.e3mall.pojo.ItemExample;
import cn.e3mall.pojo.ItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	ItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		//根据parentId查询节点列表
		ItemCatExample example = new ItemCatExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<ItemCat> list = itemCatMapper.selectByExample(example);
		//转换成EasyUITreeNode列表
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (ItemCat itemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			node.setState(itemCat.getIsParent() ? "closed" : "open");
			//添加列表
			resultList.add(node);
		}
		//返回
		return resultList;
	}

	@Override
	public EasyUIDataGridResult getItemCatList(int page, int rows) {
		//设置分页信息
				PageHelper.startPage(page,rows);
				//创建一个返回值对象
				 ItemCatExample example = new ItemCatExample();
				List<ItemCat> list = itemCatMapper.selectByExample(example);
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
				PageInfo<ItemCat> pageInfo = new PageInfo<>(list);
				//取总记录数
				long total = pageInfo.getTotal();
				result.setTotal(total);
				return result;
	}

}
