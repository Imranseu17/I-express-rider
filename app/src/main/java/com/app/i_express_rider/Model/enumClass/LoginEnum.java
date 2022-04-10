package com.app.i_express_rider.Model.enumClass;

public enum LoginEnum {
    Login_SUCCESS(1,201),
    Login_FAILED(2,501),
    ERROR_CODE_100(8,100),
    ERROR_CODE_406(9,406),
    SERVER_ERROR(3,999);


    private int key;
    private int code;

    LoginEnum(int key, int code) {
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

    public static LoginEnum getByCode(int code){
        for(LoginEnum rs : LoginEnum.values()){
            if(rs.code == code) return rs;
        }

        return null;


    }
}
