package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年10月29日 下午6:34:34
 */
public interface SearchService {

	SearchResult search(String keyword, int page, int row) throws Exception;
}
