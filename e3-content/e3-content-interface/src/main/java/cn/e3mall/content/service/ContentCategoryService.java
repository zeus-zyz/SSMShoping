package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.untils.E3Result;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月22日 下午9:50:46
 */
public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCatList(long parentId);
	
	E3Result addContentCategory(long parentId, String name);
	
	E3Result updateContentCategory(long id,String name);
	
	E3Result delContentCategory(long id);
}
