package com.app.i_express_rider.Model.callback;

import com.app.i_express_rider.Model.models.Login;

public interface LoginUserView {
    public void onSuccess(Login login, int code);
    public void onError(String error, int code);
}
