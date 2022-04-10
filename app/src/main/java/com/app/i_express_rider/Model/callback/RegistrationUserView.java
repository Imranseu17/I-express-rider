package com.app.i_express_rider.Model.callback;


import com.app.i_express_rider.Model.models.Registration;

public interface RegistrationUserView {
    public void onSuccess(Registration registration, int code);
    public void onError(String error, int code);
}
