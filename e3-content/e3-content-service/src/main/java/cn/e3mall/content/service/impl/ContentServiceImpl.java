package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.ContentMapper;
import cn.e3mall.pojo.Content;
import cn.e3mall.pojo.ContentExample;
import cn.e3mall.pojo.ContentExample.Criteria;

/**
 * @Description: 内容管理Service
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月22日 下午9:46:28
 */
@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	ContentMapper contentMapper;
	
	@Override
	public E3Result addContent(Content content) {
		//将内容数据插入到内容表
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//保存到数据库
		contentMapper.insert(content);
		return E3Result.ok();
	}

	/**
	 * 根据内容分类id查询内容列表
	 * <p>Title: getContentListByCid</p>
	 * <p>Description: </p>
	 * @param cid
	 * @return
	 * @see cn.e3mall.content.service.ContentService#getContentListByCid(long)
	 */
	@Override
	public List<Content> getContentListByCid(long cid) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<Content> list = contentMapper.selectByExampleWithBLOBs(example);
		return list;
	}

}
