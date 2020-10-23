package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.ContentCategoryMapper;
import cn.e3mall.pojo.ContentCategory;
import cn.e3mall.pojo.ContentCategoryExample;
import cn.e3mall.pojo.ContentCategoryExample.Criteria;

/**
 * @Description: 内容分类管理Service
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月22日 下午9:52:19
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	ContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		// 根据parentID查询子节点列表
		ContentCategoryExample example = new ContentCategoryExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<ContentCategory> catList = contentCategoryMapper.selectByExample(example);
		// 转换成EasyUITreeNode的列表对象
		List<EasyUITreeNode> nodeList = new ArrayList<>();
		for (ContentCategory contentCategory : catList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent() ? "closed" : "open");
			// 添加到EasyUITreeNode对象中
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public E3Result addContentCategory(long parentId, String name) {
		// 创建contentCategory对象
		ContentCategory contentCategory = new ContentCategory();
		// 设置contentCategory对象的属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		// 设置contentCategory对象状态(1-正常 2-删除)
		contentCategory.setStatus(1);
		// 设置默认排序就是1
		contentCategory.setSortOrder(1);
		// 新添加的节点一定是叶子节点
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//将数据保存到数据库中
		contentCategoryMapper.insert(contentCategory);
		//判断父节点的IsParent属性，如果不是true则改成true
		//根据parentid查询父节点
		ContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			//更新数据库
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回结果，返回E3Result对象，包含contentCategory对象
		return E3Result.ok(contentCategory);
	}

}
