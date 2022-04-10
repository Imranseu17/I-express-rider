package com.app.i_express_rider.Model.callback;



import com.app.i_express_rider.Model.models.SendOTP;

public interface OTPUserView {
    public void onSuccess(SendOTP sendOTP, int code);
    public void onError(String error, int code);
}
