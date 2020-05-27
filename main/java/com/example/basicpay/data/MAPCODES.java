package com.example.basicpay.data;

public enum MAPCODES {
    LOGIN_CODE(10),
    TRAN_CODE(11),
    LIST_CODE(12);

    private final int code;

    MAPCODES(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
