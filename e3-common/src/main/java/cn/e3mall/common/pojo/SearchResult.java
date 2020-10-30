package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月29日 下午6:32:06
 */
public class SearchResult implements Serializable{
	
	//@Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)  
	private static final long serialVersionUID = -653086593472374273L;

	private long recordCount;
	
	private int totalPages;
	
	private List<SearchItem> itemList;

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<SearchItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	
}
