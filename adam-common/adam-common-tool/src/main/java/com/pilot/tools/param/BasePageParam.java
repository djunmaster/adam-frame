package com.pilot.tools.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel(
    value = "BasePageParam",
    description = "分页参数"
)
public class BasePageParam extends BaseQueryParam {
    @ApiModelProperty(
        value = "页码",
        example = "1",
        required = true
    )
    private @Min(1L) int pageNo = 1;
    @ApiModelProperty(
        value = "页面大小",
        example = "10",
        required = true
    )
    private @Min(10L) int pageSize = 10;
    @ApiModelProperty(
        value = "总记录数",
        example = "100"
    )
    private long totalCount;
    @ApiModelProperty(
        value = "排序字段",
        example = "name"
    )
    private String field;
    @ApiModelProperty(
        value = "排序类型",
        example = "asc"
    )
    private String type;

    @ApiModelProperty(
        hidden = true
    )
    public int getCurPageFirst() {
        int firstResult = 0;
        if (this.getPageNo() > 0) {
            firstResult = (this.getPageNo() - 1) * this.getPageSize();
            if (this.getTotalCount() > 0L && (long)firstResult >= this.getTotalCount()) {
                firstResult = 0;
            }
        }

        return firstResult;
    }

    public BasePageParam() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BasePageParam)) {
            return false;
        } else {
            BasePageParam other = (BasePageParam)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getPageNo() != other.getPageNo()) {
                return false;
            } else if (this.getPageSize() != other.getPageSize()) {
                return false;
            } else if (this.getTotalCount() != other.getTotalCount()) {
                return false;
            } else {
                Object this$field = this.getField();
                Object other$field = other.getField();
                if (this$field == null) {
                    if (other$field != null) {
                        return false;
                    }
                } else if (!this$field.equals(other$field)) {
                    return false;
                }

                Object this$type = this.getType();
                Object other$type = other.getType();
                if (this$type == null) {
                    return other$type == null;
                } else return this$type.equals(other$type);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BasePageParam;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getPageNo();
        result = result * 59 + this.getPageSize();
        long $totalCount = this.getTotalCount();
        result = result * 59 + Long.hashCode($totalCount);
        Object $field = this.getField();
        result = result * 59 + ($field == null ? 43 : $field.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        return result;
    }

    public String toString() {
        return "BasePageParam(pageNo=" + this.getPageNo() + ", pageSize=" + this.getPageSize() + ", totalCount=" + this.getTotalCount() + ", field=" + this.getField() + ", type=" + this.getType() + ")";
    }
}
