package com.app.i_express_rider.Model.callback;

import com.app.i_express_rider.Model.models.GetVerifyToken;


public interface VerifyTokenUserView {
    public void onSuccess(GetVerifyToken getVerifyToken, int code);
    public void onError(String error, int code);
}
