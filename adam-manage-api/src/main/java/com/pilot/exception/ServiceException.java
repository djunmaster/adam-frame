package com.pilot.exception;

public class ServiceException extends BaseRuntimeException {
    private String serviceName;

    public ServiceException(String errorMsg) {
        super(errorMsg);
    }

    public ServiceException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    public ServiceException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public ServiceException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }
}
