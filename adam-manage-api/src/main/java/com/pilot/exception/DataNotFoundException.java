//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.exception;

import org.apache.ibatis.exceptions.PersistenceException;

public class DataNotFoundException extends PersistenceException {
    public DataNotFoundException(String errorMsg) {
        super(errorMsg);
    }

    public DataNotFoundException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
