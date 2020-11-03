package cn.e3mall.item.pojo;

import cn.e3mall.pojo.Item;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月2日 下午8:54:25
 */
public class Items extends Item {



	public Items(Item item){
		this.setId(item.getId());
		this.setTitle(item.getTitle());
		this.setSellPoint(item.getSellPoint());
		this.setPrice(item.getPrice());
		this.setNum(item.getNum());
		this.setBarcode(item.getBarcode());
		this.setImage(item.getImage());
		this.setCid(item.getCid());
		this.setStatus(item.getStatus());
		this.setCreated(item.getCreated());
		this.setUpdated(item.getUpdated());
	}
	
	public String[] getImages(){
		String image2 = this.getImage();
		if(image2 != null && !"".equals(image2)){
			String[] strings = image2.split(",");
			return strings;
		}
		return null;
	}
}
