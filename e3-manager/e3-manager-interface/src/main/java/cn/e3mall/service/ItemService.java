package cn.e3mall.service;

import cn.e3mall.pojo.Item;
import cn.e3mall.untils.EasyUIDataGridResult;

public interface ItemService {
	
	Item getItemById(long itemId);
	
	EasyUIDataGridResult getItemsList(Integer page,Integer rows);
}
