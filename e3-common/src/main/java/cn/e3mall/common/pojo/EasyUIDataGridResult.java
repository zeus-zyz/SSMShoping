package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUIDataGridResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5932636649681724028L;

	/*private Integer total;*/
	
	private long total;
	
	private List<?> rows;
	
	public EasyUIDataGridResult() {
		super();
	}

	/*public EasyUIDataGridResult(Integer total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public EasyUIDataGridResult(Long total, List<?> rows) {
        this.total = total.intValue();
        this.rows = rows;
    }*/
	
/*	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
*/
	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	
	
	
}
