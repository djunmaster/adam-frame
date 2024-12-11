package com.pilot.entity.response;

import com.pilot.entity.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageView<T> implements BaseEntity {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private List<T> lists;
    private int pageNo = 1;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private long totalCount;
    private long totalPage;
    private String sorts;

    public PageView(int pageSize, int pageNo) {
        this.pageSize = Math.max(1, pageSize);
        this.pageNo = Math.max(1, pageNo);
    }

    public void setQueryResult(long rowCount, List<T> records) {
        this.lists = records;
        setTotalCount(rowCount);
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
        setTotalCount(lists.size());
    }


    public void setPageNo(int pageNo) {
        this.pageNo = Math.max(1, pageNo);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(1, pageSize);
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        this.totalPage = (totalCount + pageSize - 1) / pageSize; // 简化计算
    }
}
