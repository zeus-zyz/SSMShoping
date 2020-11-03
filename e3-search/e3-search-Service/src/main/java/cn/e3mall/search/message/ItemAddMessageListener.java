package cn.e3mall.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemSearchMapper;

/**
 * @Description: 监听商品添加消息，接收消息后，将对应的商品信息同步到索引库
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月2日 下午6:35:31
 */
public class ItemAddMessageListener implements MessageListener {

	@Autowired
	ItemSearchMapper itemSearchMapper;
	
	@Autowired
	SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		try{
			//从消息中取商品ID
			TextMessage textMessage=(TextMessage) message;
			String text = textMessage.getText();
			Long itemId = new Long(text);
			//等待事务提交
			Thread.sleep(1000);
			//根据商品ID查询商品信息
			SearchItem searchItem = itemSearchMapper.getItemById(itemId);
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			solrServer.add(document);
			solrServer.commit();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
