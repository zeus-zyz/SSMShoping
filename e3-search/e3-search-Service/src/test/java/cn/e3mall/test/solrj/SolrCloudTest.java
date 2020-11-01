package cn.e3mall.test.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月1日 上午11:20:04
 */
public class SolrCloudTest {
  
	@Test
	public void solrCloudAddDocument() throws Exception{
		CloudSolrServer solrServer = new CloudSolrServer("47.97.24.99:2182,47.97.24.99:2183,47.97.24.99:2184");
		solrServer.setDefaultCollection("collection1");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("item_title", "测试商品");
		document.addField("item_price", "100");
		document.addField("id", "test001");
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void queryDocument() throws Exception{
		CloudSolrServer solrServer = new CloudSolrServer("47.97.24.99:2182,47.97.24.99:2183,47.97.24.99:2184");
		solrServer.setDefaultCollection("collection1");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("总记录"+solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("title"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
}
