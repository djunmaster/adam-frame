//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.tools.param;

import com.pilot.tools.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(
    value = "BaseParam",
    description = "请求参数基类"
)
public class BaseParam implements BaseEntity {
    private static final long serialVersionUID = 4722021558274928307L;
    @ApiModelProperty(
        value = "职员名称",
        hidden = true
    )
    private String currEmpName;
    @ApiModelProperty(
        value = "登录系统客户端id",
        hidden = true
    )
    private String loginClientId;
    @ApiModelProperty(
        value = "当前登录系统",
        hidden = true
    )
    private String currSystemPkId;
    @ApiModelProperty(
        value = "当前登录子系统",
        hidden = true
    )
    private String currSubsystemPkId;
    @ApiModelProperty(
        value = "当前用户选择的项目id",
        hidden = true
    )
    private String currProjectPkId;
    @ApiModelProperty(
        value = "当前用户选择的项目的tree key",
        hidden = true
    )
    private String currProjectTreeKey;
    @ApiModelProperty(
        value = "当前用户选择的项目的家族map, key对应项目id, value对应项目名称",
        hidden = true
    )
    private Map<String, String> currProjectFamilyMap;
    @ApiModelProperty(
        value = "当前用户关联的项目ID列表",
        hidden = true
    )
    private List<String> projectPkIds;
    @ApiModelProperty(
        value = "请求token",
        required = true,
        notes = "常在请求头中添加，具体请求可以不用重复添加",
        hidden = true
    )
    private String token;
    @ApiModelProperty(
        value = "当前登录用户主键",
        hidden = true
    )
    private String currUserPkId;
    @ApiModelProperty(
        value = "当前登录角色主键",
        notes = "默认不传",
        hidden = true
    )
    private String currRolePkId;
    @ApiModelProperty(
        value = "当前登录用户名称",
        notes = "默认不传",
        hidden = true
    )
    private String currUserName;
    @ApiModelProperty(
        value = "当前登录用户手机",
        notes = "默认不传",
        hidden = true
    )
    private String currUserPhone;
    @ApiModelProperty(
        value = "语言类型",
        notes = "默认中文简写",
        hidden = true
    )
    private int lang = 1;
    @ApiModelProperty(
        value = "角色类型",
        notes = "默认不传",
        hidden = true
    )
    private int currRoleType;

    public BaseParam() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseParam)) {
            return false;
        } else {
            BaseParam other = (BaseParam)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label175: {
                    Object this$currEmpName = this.getCurrEmpName();
                    Object other$currEmpName = other.getCurrEmpName();
                    if (this$currEmpName == null) {
                        if (other$currEmpName == null) {
                            break label175;
                        }
                    } else if (this$currEmpName.equals(other$currEmpName)) {
                        break label175;
                    }

                    return false;
                }

                Object this$loginClientId = this.getLoginClientId();
                Object other$loginClientId = other.getLoginClientId();
                if (this$loginClientId == null) {
                    if (other$loginClientId != null) {
                        return false;
                    }
                } else if (!this$loginClientId.equals(other$loginClientId)) {
                    return false;
                }

                Object this$currSystemPkId = this.getCurrSystemPkId();
                Object other$currSystemPkId = other.getCurrSystemPkId();
                if (this$currSystemPkId == null) {
                    if (other$currSystemPkId != null) {
                        return false;
                    }
                } else if (!this$currSystemPkId.equals(other$currSystemPkId)) {
                    return false;
                }

                label154: {
                    Object this$currSubsystemPkId = this.getCurrSubsystemPkId();
                    Object other$currSubsystemPkId = other.getCurrSubsystemPkId();
                    if (this$currSubsystemPkId == null) {
                        if (other$currSubsystemPkId == null) {
                            break label154;
                        }
                    } else if (this$currSubsystemPkId.equals(other$currSubsystemPkId)) {
                        break label154;
                    }

                    return false;
                }

                label147: {
                    Object this$currProjectPkId = this.getCurrProjectPkId();
                    Object other$currProjectPkId = other.getCurrProjectPkId();
                    if (this$currProjectPkId == null) {
                        if (other$currProjectPkId == null) {
                            break label147;
                        }
                    } else if (this$currProjectPkId.equals(other$currProjectPkId)) {
                        break label147;
                    }

                    return false;
                }

                Object this$currProjectTreeKey = this.getCurrProjectTreeKey();
                Object other$currProjectTreeKey = other.getCurrProjectTreeKey();
                if (this$currProjectTreeKey == null) {
                    if (other$currProjectTreeKey != null) {
                        return false;
                    }
                } else if (!this$currProjectTreeKey.equals(other$currProjectTreeKey)) {
                    return false;
                }

                Object this$currProjectFamilyMap = this.getCurrProjectFamilyMap();
                Object other$currProjectFamilyMap = other.getCurrProjectFamilyMap();
                if (this$currProjectFamilyMap == null) {
                    if (other$currProjectFamilyMap != null) {
                        return false;
                    }
                } else if (!this$currProjectFamilyMap.equals(other$currProjectFamilyMap)) {
                    return false;
                }

                label126: {
                    Object this$projectPkIds = this.getProjectPkIds();
                    Object other$projectPkIds = other.getProjectPkIds();
                    if (this$projectPkIds == null) {
                        if (other$projectPkIds == null) {
                            break label126;
                        }
                    } else if (this$projectPkIds.equals(other$projectPkIds)) {
                        break label126;
                    }

                    return false;
                }

                label119: {
                    Object this$token = this.getToken();
                    Object other$token = other.getToken();
                    if (this$token == null) {
                        if (other$token == null) {
                            break label119;
                        }
                    } else if (this$token.equals(other$token)) {
                        break label119;
                    }

                    return false;
                }

                Object this$currUserPkId = this.getCurrUserPkId();
                Object other$currUserPkId = other.getCurrUserPkId();
                if (this$currUserPkId == null) {
                    if (other$currUserPkId != null) {
                        return false;
                    }
                } else if (!this$currUserPkId.equals(other$currUserPkId)) {
                    return false;
                }

                label105: {
                    Object this$currRolePkId = this.getCurrRolePkId();
                    Object other$currRolePkId = other.getCurrRolePkId();
                    if (this$currRolePkId == null) {
                        if (other$currRolePkId == null) {
                            break label105;
                        }
                    } else if (this$currRolePkId.equals(other$currRolePkId)) {
                        break label105;
                    }

                    return false;
                }

                Object this$currUserName = this.getCurrUserName();
                Object other$currUserName = other.getCurrUserName();
                if (this$currUserName == null) {
                    if (other$currUserName != null) {
                        return false;
                    }
                } else if (!this$currUserName.equals(other$currUserName)) {
                    return false;
                }

                label91: {
                    Object this$currUserPhone = this.getCurrUserPhone();
                    Object other$currUserPhone = other.getCurrUserPhone();
                    if (this$currUserPhone == null) {
                        if (other$currUserPhone == null) {
                            break label91;
                        }
                    } else if (this$currUserPhone.equals(other$currUserPhone)) {
                        break label91;
                    }

                    return false;
                }

                if (this.getLang() != other.getLang()) {
                    return false;
                } else return this.getCurrRoleType() == other.getCurrRoleType();
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseParam;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $currEmpName = this.getCurrEmpName();
        result = result * 59 + ($currEmpName == null ? 43 : $currEmpName.hashCode());
        Object $loginClientId = this.getLoginClientId();
        result = result * 59 + ($loginClientId == null ? 43 : $loginClientId.hashCode());
        Object $currSystemPkId = this.getCurrSystemPkId();
        result = result * 59 + ($currSystemPkId == null ? 43 : $currSystemPkId.hashCode());
        Object $currSubsystemPkId = this.getCurrSubsystemPkId();
        result = result * 59 + ($currSubsystemPkId == null ? 43 : $currSubsystemPkId.hashCode());
        Object $currProjectPkId = this.getCurrProjectPkId();
        result = result * 59 + ($currProjectPkId == null ? 43 : $currProjectPkId.hashCode());
        Object $currProjectTreeKey = this.getCurrProjectTreeKey();
        result = result * 59 + ($currProjectTreeKey == null ? 43 : $currProjectTreeKey.hashCode());
        Object $currProjectFamilyMap = this.getCurrProjectFamilyMap();
        result = result * 59 + ($currProjectFamilyMap == null ? 43 : $currProjectFamilyMap.hashCode());
        Object $projectPkIds = this.getProjectPkIds();
        result = result * 59 + ($projectPkIds == null ? 43 : $projectPkIds.hashCode());
        Object $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        Object $currUserPkId = this.getCurrUserPkId();
        result = result * 59 + ($currUserPkId == null ? 43 : $currUserPkId.hashCode());
        Object $currRolePkId = this.getCurrRolePkId();
        result = result * 59 + ($currRolePkId == null ? 43 : $currRolePkId.hashCode());
        Object $currUserName = this.getCurrUserName();
        result = result * 59 + ($currUserName == null ? 43 : $currUserName.hashCode());
        Object $currUserPhone = this.getCurrUserPhone();
        result = result * 59 + ($currUserPhone == null ? 43 : $currUserPhone.hashCode());
        result = result * 59 + this.getLang();
        result = result * 59 + this.getCurrRoleType();
        return result;
    }

    public String toString() {
        return "BaseParam(currEmpName=" + this.getCurrEmpName() + ", loginClientId=" + this.getLoginClientId() + ", currSystemPkId=" + this.getCurrSystemPkId() + ", currSubsystemPkId=" + this.getCurrSubsystemPkId() + ", currProjectPkId=" + this.getCurrProjectPkId() + ", currProjectTreeKey=" + this.getCurrProjectTreeKey() + ", currProjectFamilyMap=" + this.getCurrProjectFamilyMap() + ", projectPkIds=" + this.getProjectPkIds() + ", token=" + this.getToken() + ", currUserPkId=" + this.getCurrUserPkId() + ", currRolePkId=" + this.getCurrRolePkId() + ", currUserName=" + this.getCurrUserName() + ", currUserPhone=" + this.getCurrUserPhone() + ", lang=" + this.getLang() + ", currRoleType=" + this.getCurrRoleType() + ")";
    }
}
