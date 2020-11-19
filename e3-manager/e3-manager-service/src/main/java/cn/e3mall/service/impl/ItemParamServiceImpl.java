package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.mapper.ItemParamMapper;
import cn.e3mall.pojo.ItemParam;
import cn.e3mall.pojo.ItemParamExample;
import cn.e3mall.service.ItemParamService;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月15日 上午11:11:27
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	ItemParamMapper itemParamMapper;
	
	@Override
	public List<ItemParam> getItemParam(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page,rows);
		//创建一个返回值对象
		ItemParamExample example = new ItemParamExample();
		List<ItemParam> list = itemParamMapper.selectByExample(example);
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
		PageInfo<ItemParam> pageInfo = new PageInfo<>(list);
		//取总记录数
		
		return list;
	}

}
