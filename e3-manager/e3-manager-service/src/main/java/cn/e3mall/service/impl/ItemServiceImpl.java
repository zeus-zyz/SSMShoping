package cn.e3mall.service.impl;



import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.common.untils.IDUtils;
import cn.e3mall.common.untils.JsonUtils;
import cn.e3mall.mapper.ItemDescMapper;
import cn.e3mall.mapper.ItemMapper;
import cn.e3mall.pojo.Item;
import cn.e3mall.pojo.ItemDesc;
import cn.e3mall.pojo.ItemExample;
import cn.e3mall.pojo.ItemExample.Criteria;
import cn.e3mall.service.ItemService;
/**
 * 商品管理Service
 * @author Administrator
 *	
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	ItemDescMapper itemDescMapper;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Resource
	Destination topicDestination;
	
	@Autowired
	JedisClient jedisClient;
	
	@Value("${REDIS_ITEM_PRE}")
	private String REDIS_ITEM_PRE;
	
	@Value("${ITEM_CACHE_EXPIRE}")
	private Integer ITEM_CACHE_EXPIRE;
	
	@Override
	public Item getItemById(long itemId) {
		//查询缓存
		try{
			String json = jedisClient.get(REDIS_ITEM_PRE+":"+itemId+":BASE");
			if(StringUtils.isNotBlank(json)){
				Item item = JsonUtils.jsonToPojo(json, Item.class);
				return item;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//根据主键查询
		//Item item = itemMapper.selectByPrimaryKey(itemId);
		ItemExample example = new ItemExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(itemId);
		//执行查询
		List<Item> list = itemMapper.selectByExample(example);
		 if(list != null && list.size() > 0){
			 try{
				 jedisClient.set(REDIS_ITEM_PRE+":"+itemId+":BASE", JsonUtils.objectTOJson(list.get(0)));
				 jedisClient.expire(REDIS_ITEM_PRE+":"+itemId+":BASE", ITEM_CACHE_EXPIRE);
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			return list.get(0);
		}
		
		//return item;
		 return null;
	}

	@Override
	public EasyUIDataGridResult getItemsList(Integer page,Integer rows) {
		//设置分页信息
		PageHelper.startPage(page,rows);
		//创建一个返回值对象
		ItemExample example = new ItemExample();
		List<Item> list = itemMapper.selectByExample(example);
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
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		//取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

	@Override
	public E3Result addItem(Item item, String desc) {
		// 生成商品id
		final long itemId = IDUtils.genItemId();
		// 补全item的属性
		item.setId(itemId);
		// 添加商品的状态(1-正常 2-下架 3-删除)
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//保存商品信息
		itemMapper.insert(item);
		// 创建一个商品描述所对应的对象
		ItemDesc itemDesc = new ItemDesc();
		//补全属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		//保存商品描述
		itemDescMapper.insert(itemDesc);
		//发送商品添加信息
		jmsTemplate.send(topicDestination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(itemId+"");
				return message;
			}
		});
		//返回成功
		return E3Result.ok();
	}

	/**
	 * 使商品上架
	 */
	@Override
	public E3Result updateItemReshelf(String ids) {
		/*String[] strings = ids.split(",");
		Long[] id=new Long[strings.length];
		for(int i=0;i<strings.length;i++){
			id[i]=Long.valueOf(i);
		}
		List<Long> list = Arrays.asList(id);
		ItemExample example = new ItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		List<Item> lists = itemMapper.selectByExample(example);
		Item item = new Item();
		int rows=0;
		for(int i=0;i<lists.size();i++){
			item = lists.get(i);
			item.setStatus((byte)1);
			rows= itemMapper.updateByPrimaryKeySelective(item);
		}
		return rows>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
		*/
		if(StringUtils.isNoneBlank(ids)){
			int rows=0;
			if(ids.indexOf(",")<0){
				String[] split = ids.split(",");
				for (String id : split) {
					Item item = itemMapper.selectByPrimaryKey(Long.valueOf(id));
					item.setStatus((byte)1);
					rows=itemMapper.updateByPrimaryKey(item);
				}
				return rows>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
			}
			Item item = itemMapper.selectByPrimaryKey(Long.valueOf(ids));
			item.setStatus((byte)1);
			rows=itemMapper.updateByPrimaryKey(item);
			return rows>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
		}
		return null;
	}

	/**
	 * 使商品下架
	 */
	@Override
	public E3Result updateItemInstock(String ids) {
		if(StringUtils.isNoneBlank(ids)){
			int rows=0;
			if(ids.indexOf(",")<0){
				String[] split = ids.split(",");
				for (String id : split) {
					Item item = itemMapper.selectByPrimaryKey(Long.valueOf(id));
					item.setStatus((byte)2);
					rows=itemMapper.updateByPrimaryKey(item);
				}
				return rows>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
			}
			Item item = itemMapper.selectByPrimaryKey(Long.valueOf(ids));
			item.setStatus((byte)1);
			rows=itemMapper.updateByPrimaryKey(item);
			return rows>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
		}
		return null;
	}

	/**
	 * 使商品为伪删除状态
	 */
	@Override
	public E3Result updateItemDel(String ids) {
		if(StringUtils.isNoneBlank(ids)){
			int rows=0;
			if(ids.indexOf(",")<0){
				String[] split = ids.split(",");
				for (String id : split) {
					Item item = itemMapper.selectByPrimaryKey(Long.valueOf(id));
					item.setStatus((byte)3);
					rows=itemMapper.updateByPrimaryKey(item);
				}
				return rows>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
			}
			Item item = itemMapper.selectByPrimaryKey(Long.valueOf(ids));
			item.setStatus((byte)1);
			rows=itemMapper.updateByPrimaryKey(item);
			return rows>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
		}
		return null;
	}

	@Override
	public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		ItemExample example = new ItemExample();
		List<Item> items = itemMapper.selectByExample(example);
		//取分页信息
		PageInfo<Item> pageInfo = new PageInfo<>(items);
		//创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(items);
		return result;
	}

	@Override
	public ItemDesc getItemDescById(Long itemId) {
		try{
			String json = jedisClient.get(REDIS_ITEM_PRE+":"+itemId+":DESC");
			if(StringUtils.isNotBlank(json)){
			 ItemDesc itemDesc = JsonUtils.jsonToPojo(json, ItemDesc.class);
				return itemDesc;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		 try{
			 jedisClient.set(REDIS_ITEM_PRE+":"+itemId+":DESC", JsonUtils.objectTOJson(itemDesc));
			 jedisClient.expire(REDIS_ITEM_PRE+":"+itemId+":DESC", ITEM_CACHE_EXPIRE);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return itemDesc;
	}

	@Override
	public Item queryItemById(Long id) {
		Item item = itemMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public E3Result updateItem(Item item) {
		int row = itemMapper.updateByPrimaryKeySelective(item);
		return row>0 ? E3Result.ok() : E3Result.ok("操作失败，请稍后再试！！");
	}

}
