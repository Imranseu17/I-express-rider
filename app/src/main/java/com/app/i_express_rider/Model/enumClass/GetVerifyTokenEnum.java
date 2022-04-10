package com.app.i_express_rider.Model.enumClass;

public enum GetVerifyTokenEnum {
    GetVerifyToken_SUCCESS(1,201),
    GetVerifyToken_FAILED(2,501),
    ERROR_CODE_100(8,100),
    ERROR_CODE_406(9,406),
    SERVER_ERROR(3,999);


    private int key;
    private int code;

    GetVerifyTokenEnum(int key, int code) {
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

    public static GetVerifyTokenEnum getByCode(int code){
        for(GetVerifyTokenEnum rs : GetVerifyTokenEnum.values()){
            if(rs.code == code) return rs;
        }

        return null;


    }
}
