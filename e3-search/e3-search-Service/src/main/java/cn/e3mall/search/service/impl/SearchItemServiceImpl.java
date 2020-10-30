package cn.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.untils.E3Result;
import cn.e3mall.search.mapper.ItemSearchMapper;
import cn.e3mall.search.service.SearchItemService;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月29日 下午7:07:57
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	ItemSearchMapper itemSearchMapper;
	
	@Autowired
	SolrServer solrServer;
	
	@Override
	public E3Result importAllItems() {
		try {
			List<SearchItem> itemList = itemSearchMapper.getItemList();
			for (SearchItem searchItem : itemList) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				solrServer.add(document);
			}
			solrServer.commit();
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500,"数据导入发生异常");
		}
	}

}
