package com.pilot.entity;

import lombok.Data;

@Data
public class ResponseCode {
    private int status;
    private String msg;

    public ResponseCode() {
    }

    public ResponseCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}