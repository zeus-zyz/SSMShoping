package cn.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月29日 下午7:16:44
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	SearchDao searchDao;
	
	@Override
	public SearchResult search(String keyword, int page, int row) throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQuery(keyword);
		if(page <= 0) page=1;
		query.setStart((page - 1) * row);
		query.setRows(row);
		query.set("df", "item_title");
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		SearchResult searchResult = searchDao.search(query);
		long recordCount = searchResult.getRecordCount();
		int totalPage=(int) (recordCount / row);
		if (recordCount % row > 0) 
			totalPage++;
		searchResult.setTotalPages(totalPage);
		return searchResult;
	}

}
