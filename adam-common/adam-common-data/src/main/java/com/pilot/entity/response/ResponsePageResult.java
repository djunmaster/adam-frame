package com.pilot.entity.response;

import cn.hutool.core.util.ObjectUtil;
import com.pilot.entity.base.ResultType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "ResponsePageResult", description = "分页返回对象基类")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponsePageResult<T> extends ResponseResult<T> {

    @ApiModelProperty(value = "当前页码", required = true)
    private long pageIndex;

    @ApiModelProperty(value = "每页记录数", required = true)
    private long pageSize;

    @ApiModelProperty(value = "总记录数", required = true)
    private long totalCount;

    @ApiModelProperty(value = "总页数", required = true)
    private long totalPage;

    public ResponsePageResult(long pageIndex, long pageSize, long totalCount, long totalPage) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
    }

    public static <T> ResponsePageResult<T> ok(PageView<T> pageView) {
        ResponsePageResult<T> result = new ResponsePageResult<>();
        result.setStatus(ResultType.SUCCESS.getValue());
        result.setMsg(ResultType.SUCCESS.getDesc());

        if (ObjectUtil.isNotNull(pageView)) {
            result.setTotalCount(pageView.getTotalCount());
            result.setPageIndex(pageView.getPageNo());
            result.setPageSize(Math.max(10L, pageView.getPageSize()));
            result.setTotalPage(pageView.getTotalPage());
            result.setData(pageView.getLists());
        }
        return result;
    }

    public static <T> ResponsePageResult<T> error(String errorMsg) {
        ResponsePageResult<T> result = new ResponsePageResult<>();
        result.setStatus(ResultType.OTHER_ERROR.getValue());
        result.setMsg(errorMsg);
        return result;
    }
}
