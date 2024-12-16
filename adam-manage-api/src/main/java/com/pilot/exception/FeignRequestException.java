//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.exception;

public class FeignRequestException extends ServiceException {
    public FeignRequestException(String errorMsg) {
        super(errorMsg);
    }

    public FeignRequestException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    public FeignRequestException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public FeignRequestException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }
}
