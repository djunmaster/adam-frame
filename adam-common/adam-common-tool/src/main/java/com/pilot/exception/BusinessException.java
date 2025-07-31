//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.exception;

public class BusinessException extends BaseRuntimeException {
    public BusinessException(String errorMsg) {
        super(errorMsg);
    }

    public BusinessException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    public BusinessException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public BusinessException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }
}
