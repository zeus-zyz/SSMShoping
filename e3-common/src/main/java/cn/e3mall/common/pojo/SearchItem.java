package cn.e3mall.common.pojo;

import java.io.Serializable;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月29日 下午6:27:36
 */
public class SearchItem implements Serializable{

	//@Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)  
	private static final long serialVersionUID = -4170161720508278704L;

	private String id;
	
	private String title;
	
	private String sell_point;
	
	private long price;
	
	private String image;
	
	private String category_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSell_point() {
		return sell_point;
	}

	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
	
	public String[] getImages(){
		if(image != null && !"".equals(image)){
			String[] strings = image.split(",");
			return strings;
		}
		return null;
	}
}
