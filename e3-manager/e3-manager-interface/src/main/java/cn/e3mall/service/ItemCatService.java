package cn.e3mall.service;

import java.util.List;

import cn.e3mall.untils.EasyUITreeNode;

public interface ItemCatService {
	
	public List<EasyUITreeNode> getCatList(long parentId);
}