package com.pilot.entity.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页参数类，继承自 BaseParam
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePageParam extends BaseParam {

    /**
     * 当前页码，默认为 1，且不能小于 1
     */
    private int page = 1;

    /**
     * 每页显示记录数，默认为 10，且必须大于 0
     */
    private int size = 10;

    /**
     * 排序字段，多个字段用逗号分隔
     */
    private String sort;

    /**
     * 排序方式，支持升序（asc）或降序（desc），多个字段用逗号分隔
     */
    private String order;

    public BasePageParam() {
        // 默认构造器
    }

    public BasePageParam(int page, int size) {
        setPage(page);
        setSize(size);
    }

    /**
     * 设置当前页码，页码不能小于 1
     *
     * @param page 当前页码
     */
    public void setPage(int page) {
        this.page = Math.max(page, 1);
    }

    /**
     * 设置每页记录数，记录数不能小于等于 0
     *
     * @param size 每页记录数
     */
    public void setSize(int size) {
        this.size = size > 0 ? size : 10;
    }

    @Override
    public String toString() {
        return "BasePageParam{" +
                "page=" + page +
                ", size=" + size +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
