//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultType {
    SUCCESS(0, "SUCCESS"),
    REQUEST_PARA_ERROR(1, "Parameter Error"),
    JSON_PARA_EXCEPTION(2, "Json format error"),
    DB_OPERATE_ERROR(3, "Database operation failed"),
    USER_NO_AUTH(4, "No authority"),
    SYS_SO_BUSY(5, "Server busy"),
    USER_NO_LOGIN(6, "User not login"),
    OTHER_ERROR(7, "Other error"),
    NEED_CONFIRM(8, "Need confirm"),
    LICENSE_NO_AUTH(9, "License no authority");

    private final int value;
    private final String desc;

    public static ResultType valueOf(int value) {
        switch (value) {
            case 0:
                return SUCCESS;
            case 1:
                return REQUEST_PARA_ERROR;
            case 2:
                return JSON_PARA_EXCEPTION;
            case 3:
                return DB_OPERATE_ERROR;
            case 4:
                return USER_NO_AUTH;
            case 5:
                return SYS_SO_BUSY;
            case 6:
                return USER_NO_LOGIN;
            case 7:
                return OTHER_ERROR;
            case 8:
                return NEED_CONFIRM;
            case 9:
                return LICENSE_NO_AUTH;
            default:
                return null;
        }
    }
}
