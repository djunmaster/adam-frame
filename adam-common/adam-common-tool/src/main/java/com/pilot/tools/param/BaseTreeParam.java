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
    value = "BaseTreeParam",
    description = "树结构参数基类"
)
public class BaseTreeParam extends BaseParam {
    @ApiModelProperty("节点ID")
    private String nodePkId;
    @ApiModelProperty("节点名称")
    private String nodeName;
    @ApiModelProperty("树类型")
    private String treeType;
    @ApiModelProperty("父节点ID")
    private String parentPkId;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("节点级别")
    private Integer level;

    public BaseTreeParam() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseTreeParam)) {
            return false;
        } else {
            BaseTreeParam other = (BaseTreeParam)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$nodePkId = this.getNodePkId();
                Object other$nodePkId = other.getNodePkId();
                if (this$nodePkId == null) {
                    if (other$nodePkId != null) {
                        return false;
                    }
                } else if (!this$nodePkId.equals(other$nodePkId)) {
                    return false;
                }

                Object this$nodeName = this.getNodeName();
                Object other$nodeName = other.getNodeName();
                if (this$nodeName == null) {
                    if (other$nodeName != null) {
                        return false;
                    }
                } else if (!this$nodeName.equals(other$nodeName)) {
                    return false;
                }

                Object this$treeType = this.getTreeType();
                Object other$treeType = other.getTreeType();
                if (this$treeType == null) {
                    if (other$treeType != null) {
                        return false;
                    }
                } else if (!this$treeType.equals(other$treeType)) {
                    return false;
                }

                label62: {
                    Object this$parentPkId = this.getParentPkId();
                    Object other$parentPkId = other.getParentPkId();
                    if (this$parentPkId == null) {
                        if (other$parentPkId == null) {
                            break label62;
                        }
                    } else if (this$parentPkId.equals(other$parentPkId)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    Object this$sort = this.getSort();
                    Object other$sort = other.getSort();
                    if (this$sort == null) {
                        if (other$sort == null) {
                            break label55;
                        }
                    } else if (this$sort.equals(other$sort)) {
                        break label55;
                    }

                    return false;
                }

                Object this$level = this.getLevel();
                Object other$level = other.getLevel();
                if (this$level == null) {
                    return other$level == null;
                } else return this$level.equals(other$level);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseTreeParam;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $nodePkId = this.getNodePkId();
        result = result * 59 + ($nodePkId == null ? 43 : $nodePkId.hashCode());
        Object $nodeName = this.getNodeName();
        result = result * 59 + ($nodeName == null ? 43 : $nodeName.hashCode());
        Object $treeType = this.getTreeType();
        result = result * 59 + ($treeType == null ? 43 : $treeType.hashCode());
        Object $parentPkId = this.getParentPkId();
        result = result * 59 + ($parentPkId == null ? 43 : $parentPkId.hashCode());
        Object $sort = this.getSort();
        result = result * 59 + ($sort == null ? 43 : $sort.hashCode());
        Object $level = this.getLevel();
        result = result * 59 + ($level == null ? 43 : $level.hashCode());
        return result;
    }

    public String toString() {
        return "BaseTreeParam(nodePkId=" + this.getNodePkId() + ", nodeName=" + this.getNodeName() + ", treeType=" + this.getTreeType() + ", parentPkId=" + this.getParentPkId() + ", sort=" + this.getSort() + ", level=" + this.getLevel() + ")";
    }
}
