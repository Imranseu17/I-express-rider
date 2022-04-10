package com.app.i_express_rider.Model.Presenter;



import com.app.i_express_rider.Model.api.ApiClient;
import com.app.i_express_rider.Model.callback.OTPUserView;
import com.app.i_express_rider.Model.enumClass.ErrorCode;
import com.app.i_express_rider.Model.enumClass.SendOTPEnum;
import com.app.i_express_rider.Model.errors.ApiError;
import com.app.i_express_rider.Model.models.SendOTP;
import com.app.i_express_rider.view.utils.DebugLog;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class SendOtpPresenter {
    private OTPUserView mViewInterface;
    private ApiClient mApiClient;

    public SendOtpPresenter(OTPUserView view) {
        this.mViewInterface = view;

        if (this.mApiClient == null) {
            this.mApiClient = new ApiClient();
        }
    }

    public void attemptSendOTP(String country_code, String phone_number) {

        final Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("country_code",country_code);
        jsonObject.addProperty("phone",phone_number);

        mApiClient.getAPI().sendOtp(map,jsonObject).
                enqueue(new Callback<SendOTP>() {
                    @Override
                    public void onResponse(Call<SendOTP> call, Response<SendOTP> response) {
                        DebugLog.e(String.valueOf(response.code()));

                        if (response.isSuccessful()) {
                            SendOTP sendOTP = response.body();
                            if(sendOTP != null){
                                mViewInterface.onSuccess(sendOTP, response.code());
                            }
                        } else getErrorMessage(response.code(), response.errorBody());
                    }

                    @Override
                    public void onFailure(Call<SendOTP> call, Throwable e) {
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).response().code();
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            getErrorMessage(code, responseBody);

                        } else if (e instanceof SocketTimeoutException) {
                            mViewInterface.onError("Server connection error", SendOTPEnum.SendOTP_FAILED.getCode());
                        } else if (e instanceof IOException) {
                            if (e.getMessage() != null)
                                mViewInterface.onError(e.getMessage(),SendOTPEnum.SERVER_ERROR.getCode());
                            else
                                mViewInterface.onError("IO Exception",SendOTPEnum.SERVER_ERROR.getCode());
                        } else {
                            mViewInterface.onError("Unknown exception: "+e.getMessage(),
                                    SendOTPEnum.SERVER_ERROR.getCode());
                            e.printStackTrace();
                        }
                    }
                });


    }

    private void getErrorMessage(int code, ResponseBody responseBody){
        ErrorCode errorCode = ErrorCode.getByCode(code);

        if (errorCode != null) {
            switch (errorCode) {
                case ERRORCODE500:
                    mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),SendOTPEnum.ERROR_CODE_100.getCode());
                    break;
                case ERRORCODE400:
                    mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),SendOTPEnum.ERROR_CODE_100.getCode());
                    break;
                case ERRORCODE406:
                    mViewInterface.onError(ApiError.get406ErrorMessage(responseBody),SendOTPEnum.ERROR_CODE_100.getCode());
                    break;

                case SERVER_ERROR_CODE:
                    mViewInterface.onError(ApiError.getErrorMessage(responseBody),SendOTPEnum.ERROR_CODE_100.getCode());
                    break;

                default:
                    mViewInterface.onError(ApiError.getErrorMessage(responseBody),SendOTPEnum.ERROR_CODE_100.getCode());
            }


        } else {

            mViewInterface.onError("Error occurred Please try again", code);

        }


    }


}
