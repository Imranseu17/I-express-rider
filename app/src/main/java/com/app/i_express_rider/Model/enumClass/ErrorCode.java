package com.app.i_express_rider.Model.enumClass;

public enum ErrorCode {
    ERROR_CODE401(1, 401),
    ERRORCODE500(2, 500),
    ERRORCODE400(3, 400),
    ERRORCODE406(4, 406),
    ERRORCODE405(5, 405),
    ERRORCODE422(6, 412),
    ERRORCODE455(7, 455),
    ERRORCODE456(8, 456),
    SERVER_ERROR_CODE(9,999),
    ERROR_CODE404(10, 404);



    private int key;
    private int code;

    ErrorCode(int key, int code) {
        this.key = key;
        this.code = code;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static ErrorCode getByCode(int code) {
        for (ErrorCode rs : ErrorCode.values()) {
            if (rs.getCode()== code) return rs;
        }

        return null;
    }
}
