package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.mapper.ItemDescMapper;
import cn.e3mall.pojo.ItemDesc;
import cn.e3mall.pojo.ItemDescExample;
import cn.e3mall.pojo.ItemDescExample.Criteria;
import cn.e3mall.service.ItemDescService;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月28日 下午6:48:41
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	ItemDescMapper itemDescMapper;
	
	@Override
	public E3Result getItemDesc(long ids) {
		ItemDescExample example = new ItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(ids);
		List<ItemDesc> list = itemDescMapper.selectByExample(example);
		ItemDesc itemDesc = list.get(0);
		return E3Result.ok(itemDesc);
	}


}
