package com.atinbo.webmvc.controller;

import com.atinbo.common.SqlKeywords;
import com.atinbo.core.utils.CollectionUtil;
import com.atinbo.core.utils.ObjectUtil;
import com.lujia.model.Pagable;
import com.lujia.model.Outcome;
import com.lujia.model.PageForm;
import com.atinbo.webmvc.resolver.PageResolver;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/3 14:54
 * 可分页控制类
 */
@Slf4j
public class PageableController {
    /**
     * 设置请求分页数据
     */
    protected void beginPage() {
        PageForm page = PageResolver.resolve();
        if (page != null && !ObjectUtil.isEmpty(page.getPage()) && !ObjectUtil.isEmpty(page.getSize())) {
            String orderBy = SqlKeywords.escapeOrderBySql(page.getSortBy());
            PageHelper.startPage(page.getPage(), page.getSize(), orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    protected Outcome endPage(List<?> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        PageInfo page = new PageInfo(list);
        return Outcome.success(Pagable.of(page.getPageNum(), page.getPageSize(), page.getTotal(), list));
    }

}
