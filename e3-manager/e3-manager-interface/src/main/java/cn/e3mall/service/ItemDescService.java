package cn.e3mall.service;

import cn.e3mall.common.untils.E3Result;
import cn.e3mall.pojo.ItemDesc;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月28日 下午6:47:17
 */
public interface ItemDescService {

	E3Result getItemDesc(long ids);

	ItemDesc selectItemDesc(Long id);
	
	E3Result queryItemDesc(Long id);
}
