package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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

	@Override
	public E3Result updateContentCategory(long id, String name) {
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		int row = contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return row>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！");
	}

	@Override
	public E3Result delContentCategory(long id) {
		ContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		boolean flag=false;
		if(contentCategory.getIsParent()){
			flag=false;
		}else{
			contentCategoryMapper.deleteByPrimaryKey(id);
			flag=true;
		}
		if(flag){
			ContentCategoryExample example = new ContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(contentCategory.getParentId());
			List<ContentCategory> list = contentCategoryMapper.selectByExample(example);
			if(list.size()==0){
				ContentCategory category = contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
				category.setIsParent(false);
				contentCategoryMapper.updateByPrimaryKey(category);
			}
		}
		return E3Result.ok(flag);
	}

}
