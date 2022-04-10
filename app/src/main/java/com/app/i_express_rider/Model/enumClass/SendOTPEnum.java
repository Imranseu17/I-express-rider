package com.app.i_express_rider.Model.enumClass;

public enum SendOTPEnum {
    SendOTP_SUCCESS(1,201),
    SendOTP_FAILED(2,501),
    ERROR_CODE_100(8,100),
    ERROR_CODE_406(9,406),
    SERVER_ERROR(3,999);


    private int key;
    private int code;

    SendOTPEnum(int key, int code) {
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

    public static SendOTPEnum getByCode(int code){
        for(SendOTPEnum rs : SendOTPEnum.values()){
            if(rs.code == code) return rs;
        }

        return null;


    }
}
