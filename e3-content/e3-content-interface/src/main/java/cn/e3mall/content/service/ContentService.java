package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.pojo.Content;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月22日 下午9:45:16
 */
public interface ContentService {

	E3Result addContent(Content content);
	
	List<Content> getContentListByCid(long cid);
}
