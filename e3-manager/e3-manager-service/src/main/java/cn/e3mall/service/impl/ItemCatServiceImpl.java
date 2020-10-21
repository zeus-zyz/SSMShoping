package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.mapper.ItemCatMapper;
import cn.e3mall.pojo.ItemCat;
import cn.e3mall.pojo.ItemCatExample;
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

}
