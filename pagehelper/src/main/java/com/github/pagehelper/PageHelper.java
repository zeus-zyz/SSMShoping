/*
	The MIT License (MIT)

	Copyright (c) 2014 abel533@gmail.com

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in
	all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	THE SOFTWARE.
*/

package com.github.pagehelper;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * Mybatis - ͨ�÷�ҳ������
 *
 * @author liuzh/abel533/isea533
 * @version 3.3.0
 *          ��Ŀ��ַ : http://git.oschina.net/free/Mybatis_PageHelper
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageHelper implements Interceptor {
    private static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal<Page>();
    //sql������
    private SqlUtil SQLUTIL;
    //RowBounds����offset��ΪPageNumʹ�� - Ĭ�ϲ�ʹ��
    private boolean offsetAsPageNum = false;
    //RowBounds�Ƿ����count��ѯ - Ĭ�ϲ���ѯ
    private boolean rowBoundsWithCount = false;
    //������Ϊtrue��ʱ�����pagesize����Ϊ0����RowBounds��limit=0�����Ͳ�ִ�з�ҳ
    private boolean pageSizeZero = false;
    //��ҳ����
    private boolean reasonable = false;

    /**
     * ��ʼ��ҳ
     *
     * @param pageNum  ҳ��
     * @param pageSize ÿҳ��ʾ����
     */
    public static void startPage(int pageNum, int pageSize) {
        startPage(pageNum, pageSize, true);
    }

    /**
     * ��ʼ��ҳ
     *
     * @param pageNum  ҳ��
     * @param pageSize ÿҳ��ʾ����
     * @param count    �Ƿ����count��ѯ
     */
    public static void startPage(int pageNum, int pageSize, boolean count) {
        LOCAL_PAGE.set(new Page(pageNum, pageSize, count));
    }

    /**
     * ��ȡ��ҳ����
     *
     * @param rowBounds RowBounds����
     * @return ����Page����
     */
    private Page getPage(RowBounds rowBounds) {
        Page page = LOCAL_PAGE.get();
        //�Ƴ����ر���
        LOCAL_PAGE.remove();
        if (page == null) {
            if (offsetAsPageNum) {
                page = new Page(rowBounds.getOffset(), rowBounds.getLimit(), rowBoundsWithCount);
            } else {
                page = new Page(rowBounds, rowBoundsWithCount);
            }
        }
        //��ҳ����
        page.setReasonable(reasonable);
        return page;
    }

    /**
     * Mybatis����������
     *
     * @param invocation ���������
     * @return ����ִ�н��
     * @throws Throwable �׳��쳣
     */
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        RowBounds rowBounds = (RowBounds) args[2];
        if (LOCAL_PAGE.get() == null && rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        } else {
            //��ȡԭʼ��ms
            MappedStatement ms = (MappedStatement) args[0];
            //����RowBounds-��������Mybatis�Դ����ڴ��ҳ
            args[2] = RowBounds.DEFAULT;
            //��ҳ��Ϣ
            Page page = getPage(rowBounds);
            //pageSizeZero���ж�
            if (pageSizeZero && page.getPageSize() == 0) {
                //ִ������������ҳ����ѯ
                Object result = invocation.proceed();
                //�õ�������
                page.addAll((List) result);
                //�൱�ڲ�ѯ��һҳ
                page.setPageNum(1);
                //��������൱��pageSize=total
                page.setPageSize(page.size());
                //��ȻҪ����total
                page.setTotal(page.size());
                //���ؽ����ȻΪPage���� - ���ں���Խ������͵�ͳһ����
                return page;
            }
            SqlSource sqlSource = ((MappedStatement) args[0]).getSqlSource();
            //�򵥵�ͨ��total��ֵ���ж��Ƿ����count��ѯ
            if (page.isCount()) {
                //�������е�MappedStatement�滻Ϊ�µ�qs
                SQLUTIL.processCountMappedStatement(ms, sqlSource, args);
                //��ѯ����
                Object result = invocation.proceed();
                //��������
                page.setTotal((Integer) ((List) result).get(0));
                if (page.getTotal() == 0) {
                    return page;
                }
            }
            //pageSize>0��ʱ��ִ�з�ҳ��ѯ��pageSize<=0��ʱ��ִ���൱�ڿ���ֻ������һ��count
            if (page.getPageSize() > 0 &&
                    ((rowBounds == RowBounds.DEFAULT && page.getPageNum() > 0)
                            || rowBounds != RowBounds.DEFAULT)) {
                //�������е�MappedStatement�滻Ϊ�µ�qs
                SQLUTIL.processPageMappedStatement(ms, sqlSource, page, args);
                //ִ�з�ҳ��ѯ
                Object result = invocation.proceed();
                //�õ�������
                page.addAll((List) result);
            }
            //���ؽ��
            return page;
        }
    }

    /**
     * ֻ����Executor
     *
     * @param target
     * @return
     */
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * ��������ֵ
     *
     * @param p ����ֵ
     */
    public void setProperties(Properties p) {
        //���ݿⷽ��
        String dialect = p.getProperty("dialect");
        SQLUTIL = new SqlUtil(dialect);
        //offset��ΪPageNumʹ��
        String offsetAsPageNum = p.getProperty("offsetAsPageNum");
        this.offsetAsPageNum = Boolean.parseBoolean(offsetAsPageNum);
        //RowBounds��ʽ�Ƿ���count��ѯ
        String rowBoundsWithCount = p.getProperty("rowBoundsWithCount");
        this.rowBoundsWithCount = Boolean.parseBoolean(rowBoundsWithCount);
        //������Ϊtrue��ʱ�����pagesize����Ϊ0����RowBounds��limit=0�����Ͳ�ִ�з�ҳ
        String pageSizeZero = p.getProperty("pageSizeZero");
        this.pageSizeZero = Boolean.parseBoolean(pageSizeZero);
        //��ҳ������true�����������ҳ������������Զ�������Ĭ��false������
        String reasonable = p.getProperty("reasonable");
        this.reasonable = Boolean.parseBoolean(reasonable);
    }
}
