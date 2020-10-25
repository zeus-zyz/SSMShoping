package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.pojo.Item;

public interface ItemService {
	
	Item getItemById(long itemId);
	
	EasyUIDataGridResult getItemsList(Integer page,Integer rows);
	
	E3Result addItem(Item item,String desc);
	
	E3Result updateItemReshelf(String ids);
	
	E3Result updateItemInstock(String ids);
	
	E3Result updateItemDel(String ids);
	
	E3Result getItemDesc(long ids);
	
	EasyUIDataGridResult getItemParamList(Integer page,Integer rows);
}
