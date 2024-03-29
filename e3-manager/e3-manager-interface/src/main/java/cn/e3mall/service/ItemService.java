package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemDesc;

public interface ItemService {
	
	Item getItemById(long itemId);
	
	EasyUIDataGridResult getItemsList(Integer page,Integer rows);
	
	E3Result addItem(Item item,String desc);
	
	E3Result updateItemReshelf(String ids);
	
	E3Result updateItemInstock(String ids);
	
	E3Result updateItemDel(String ids);
	
	ItemDesc getItemDescById(Long itemId);
	
	EasyUIDataGridResult getItemParamList(Integer page,Integer rows);

	Item queryItemById(Long id);

	E3Result updateItem(Item item);
}
