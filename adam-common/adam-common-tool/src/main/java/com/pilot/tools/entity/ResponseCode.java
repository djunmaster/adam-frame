//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.tools.entity;

import lombok.Data;

@Data
public class ResponseCode implements ResponseEntity {
    private int status;
    private String msg;

    public ResponseCode() {
    }

    public ResponseCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
