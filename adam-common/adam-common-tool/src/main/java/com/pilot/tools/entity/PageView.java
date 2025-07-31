//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.tools.entity;

import com.pilot.tools.param.BasePageParam;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class PageView<T> implements BaseEntity {
    private static final int PAGE_SIZE = 10;
    @Setter
    private List<T> lists;
    @Setter
    private int pageNo = 1;
    private int pageSize = 10;
    private long totalCount;
    @Setter
    private long totalPage;
    private String sorts;

    public PageView() {
    }

    public PageView(int pageSize, int pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo > 0 ? pageNo : 10;
    }

    public PageView(int pageNo) {
        this.pageNo = pageNo;
    }

    public PageView(BasePageParam pageParam) {
        if (pageParam != null) {
            this.pageNo = pageParam.getPageNo();
            this.pageSize = pageParam.getPageSize() > 0 ? pageParam.getPageSize() : 10;
            this.sorts = this.genSorSql(pageParam);
        }

    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        this.setTotalPage(this.totalCount % (long)this.pageSize == 0L ? this.totalCount / (long)this.pageSize : this.totalCount / (long)this.pageSize + 1L);
    }

    private String genSorSql(BasePageParam pageParam) {
        if (pageParam != null && !"".equals(pageParam.getType()) && pageParam.getType() != null) {
            return !"".equals(pageParam.getField()) && pageParam.getField() != null ? " order by `" + pageParam.getField() + "` " + pageParam.getType() : null;
        } else {
            return null;
        }
    }
}
