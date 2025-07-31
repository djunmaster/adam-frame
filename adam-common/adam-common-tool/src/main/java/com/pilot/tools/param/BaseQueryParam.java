//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.tools.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(
    value = "BaseQueryParam",
    description = "不分页查询参数基类"
)
public class BaseQueryParam extends BaseParam {
    @ApiModelProperty(
        value = "搜索关键字",
        example = ""
    )
    private String searchKey;

    public BaseQueryParam() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseQueryParam)) {
            return false;
        } else {
            BaseQueryParam other = (BaseQueryParam)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$searchKey = this.getSearchKey();
                Object other$searchKey = other.getSearchKey();
                if (this$searchKey == null) {
                    if (other$searchKey != null) {
                        return false;
                    }
                } else if (!this$searchKey.equals(other$searchKey)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseQueryParam;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $searchKey = this.getSearchKey();
        result = result * 59 + ($searchKey == null ? 43 : $searchKey.hashCode());
        return result;
    }

    public String toString() {
        return "BaseQueryParam(searchKey=" + this.getSearchKey() + ")";
    }
}
