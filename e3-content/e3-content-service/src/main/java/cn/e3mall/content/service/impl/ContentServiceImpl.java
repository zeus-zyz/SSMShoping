package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.common.untils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.ContentMapper;
import cn.e3mall.pojo.Content;
import cn.e3mall.pojo.ContentExample;
import cn.e3mall.pojo.Item;
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
	
	@Autowired
	JedisClient jedisClient;
	
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	@Override
	public E3Result addContent(Content content) {
		//将内容数据插入到内容表
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//保存到数据库
		contentMapper.insert(content);
		Long hdel = jedisClient.del(CONTENT_LIST);
		return hdel<0 ? E3Result.ok("操作失败，请稍后再试！！") : E3Result.ok();
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
		try{
			String json = jedisClient.hget(CONTENT_LIST, cid+" ");
			if(StringUtils.isNotBlank(json)){
				List<Content> list = JsonUtils.jsonToList(json, Content.class);
				return list;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<Content> list = contentMapper.selectByExampleWithBLOBs(example);
		try{
			jedisClient.hset(CONTENT_LIST, cid+" ", JsonUtils.objectTOJson(list));
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public EasyUIDataGridResult getItemList(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<Content> list = contentMapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setRows(list);
		easyUIDataGridResult.setTotal(pageInfo.getTotal());
		return easyUIDataGridResult;
	}

	@Override
	public E3Result delContent(String ids) {
		if(StringUtils.isNoneBlank(ids)){
			int rows=0;
			Long hdel=(long) 0;
			if(ids.indexOf(",")<0){
				String[] split = ids.split(",");
				for (String id : split) {
					
						hdel = jedisClient.del(CONTENT_LIST);
						rows = contentMapper.deleteByPrimaryKey(Long.valueOf(id));
				}
				return rows>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
			}
				hdel = jedisClient.del(CONTENT_LIST);
				rows = contentMapper.deleteByPrimaryKey(Long.valueOf(ids));
			return rows<0 && hdel<0 ?  E3Result.ok("操作失败，请稍后再试！！") : E3Result.ok();
		}
		return null;
	}

	@Override
	public E3Result updateContent(Content content) {
		Long hdel = jedisClient.del(CONTENT_LIST);
		int row = contentMapper.updateByPrimaryKeySelective(content);
		return row<0 && hdel<0?  E3Result.ok("编辑失败") : E3Result.ok() ;
	}

}
