package com.pilot.exception;

import lombok.Data;

@Data
public class BaseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -621703368361960416L;
    protected String errorCode;
    protected String errorMsg;

    public BaseRuntimeException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BaseRuntimeException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorMsg = errorMsg;
    }

    public BaseRuntimeException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BaseRuntimeException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseRuntimeException)) {
            return false;
        } else {
            BaseRuntimeException other = (BaseRuntimeException)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$errorCode = this.getErrorCode();
                Object other$errorCode = other.getErrorCode();
                if (this$errorCode == null) {
                    if (other$errorCode != null) {
                        return false;
                    }
                } else if (!this$errorCode.equals(other$errorCode)) {
                    return false;
                }

                Object this$errorMsg = this.getErrorMsg();
                Object other$errorMsg = other.getErrorMsg();
                if (this$errorMsg == null) {
                    if (other$errorMsg != null) {
                        return false;
                    }
                } else if (!this$errorMsg.equals(other$errorMsg)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseRuntimeException;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = super.hashCode();
        Object $errorCode = this.getErrorCode();
        result = result * 59 + ($errorCode == null ? 43 : $errorCode.hashCode());
        Object $errorMsg = this.getErrorMsg();
        result = result * 59 + ($errorMsg == null ? 43 : $errorMsg.hashCode());
        return result;
    }

    public String toString() {
        return "BaseRuntimeException(errorCode=" + this.getErrorCode() + ", errorMsg=" + this.getErrorMsg() + ")";
    }
}
