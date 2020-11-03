package cn.e3mall.test.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
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
 * @date 2020��10��30�� ����7:13:15
 */
public class SolrJTest {
	
	//@Test
	public void addDocument()throws Exception{
		//����һ��solrServer���󣬴���һ�����ӣ�����solr�����url
		HttpSolrServer solrServer = new HttpSolrServer("http://47.97.24.99:8888/solr/collection1");
		// ����һ���ĵ�����SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//���ĵ�������������ĵ��б������һ��id�����е�������Ʊ�����schema.xml�ж���
		//document.addField("id", "2020/10/30");
		document.addField("id", "007");
		document.addField("item_title", "������Ʒ01");
		document.addField("item_price", 1000);
		//���ĵ�д��������
		solrServer.add(document);
		//�ύ
		solrServer.commit();
	}
	
	//@Test
	public void delDocument() throws Exception{
		HttpSolrServer solrServer = new HttpSolrServer("http://47.97.24.99:8888/solr/collection1");
		//ɾ���ĵ�
		solrServer.deleteById("007");
		//solrServer.deleteByQuery("id:2020/10/30");
		//�ύ
		solrServer.commit();
	}
	
	//@Test
	public void queryIndex() throws Exception{
		//����һ��solrServer����
		HttpSolrServer solrServer = new HttpSolrServer("http://47.97.24.99:8888/solr");
		//����һ��SolrQuery����
		SolrQuery query = new SolrQuery();
		//���ò�ѯ����
		//query.setQuery("*:*");
		query.set("q", "*:*");
		//ִ�в�ѯ�����QueryResponse����
		QueryResponse response = solrServer.query(query);
		//ȡ�ĵ��б�ȡ��ѯ������ܼ�¼��
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("��ѯ������м�¼��"+solrDocumentList.getNumFound());
		//�����ĵ��б���ȡ�������
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
	
	//@Test
	public void queryIndexFuza() throws Exception{
		HttpSolrServer solrServer = new HttpSolrServer("http://47.97.24.99:8888/solr");
		//����һ����ѯ����
		SolrQuery query = new SolrQuery();
		//��ѯ����
		query.setQuery("����");
		query.setStart(0);
		query.setRows(20);
		query.set("df", "item_keywords");
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//ִ�в�ѯ
		QueryResponse queryResponse = solrServer.query(query);
		//ȡ�ĵ��б�ȡ��ѯ������ܼ�¼��
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("��ѯ����ܼ�¼����" + solrDocumentList.getNumFound());
		//�����ĵ��б���ȡ�������
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			//ȡ������ʾ
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list !=null && list.size() > 0 ) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}
}
