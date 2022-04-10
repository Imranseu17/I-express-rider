package com.app.i_express_rider.Model.Presenter;

import com.app.i_express_rider.Model.api.ApiClient;
import com.app.i_express_rider.Model.callback.LoginUserView;
import com.app.i_express_rider.Model.enumClass.ErrorCode;
import com.app.i_express_rider.Model.enumClass.LoginEnum;
import com.app.i_express_rider.Model.errors.ApiError;
import com.app.i_express_rider.Model.models.Login;
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

public class LoginPresenter {
    private LoginUserView mViewInterface;
    private ApiClient mApiClient;

    public LoginPresenter(LoginUserView view) {
        this.mViewInterface = view;

        if (this.mApiClient == null) {
            this.mApiClient = new ApiClient();
        }
    }

    public void attemptLogin(String type,String country_code,String phone,String password,
                             boolean rememberMe,String appId,String ipAddress) {

        final Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type",type);
        jsonObject.addProperty("country_code",country_code);
        jsonObject.addProperty("phone",phone);
        jsonObject.addProperty("password",password);
        jsonObject.addProperty("rememberMe",rememberMe);
        jsonObject.addProperty("appId",appId);
        jsonObject.addProperty("ipAddress",ipAddress);
        


        mApiClient.getAPI().login(map,jsonObject).
                enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        DebugLog.e(String.valueOf(response.code()));

                        if (response.isSuccessful()) {
                            Login Login = response.body();
                            if(Login != null){
                                mViewInterface.onSuccess(Login, response.code());
                            }
                        } else getErrorMessage(response.code(), response.errorBody());
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable e) {
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).response().code();
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            getErrorMessage(code, responseBody);

                        } else if (e instanceof SocketTimeoutException) {
                            mViewInterface.onError("Server connection error", LoginEnum.Login_FAILED.getCode());
                        } else if (e instanceof IOException) {
                            if (e.getMessage() != null)
                                mViewInterface.onError(e.getMessage(),LoginEnum.SERVER_ERROR.getCode());
                            else
                                mViewInterface.onError("IO Exception",LoginEnum.SERVER_ERROR.getCode());
                        } else {
                            mViewInterface.onError("Unknown exception: "+e.getMessage(),
                                    LoginEnum.SERVER_ERROR.getCode());
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
                    mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),LoginEnum.ERROR_CODE_100.getCode());
                    break;
                case ERRORCODE400:
                    mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),LoginEnum.ERROR_CODE_100.getCode());
                    break;
                case ERRORCODE406:
                    mViewInterface.onError(ApiError.get406ErrorMessage(responseBody),LoginEnum.ERROR_CODE_100.getCode());
                    break;

                case SERVER_ERROR_CODE:
                    mViewInterface.onError(ApiError.getErrorMessage(responseBody),LoginEnum.ERROR_CODE_100.getCode());
                    break;

                default:
                    mViewInterface.onError(ApiError.getErrorMessage(responseBody),LoginEnum.ERROR_CODE_100.getCode());
            }


        } else {

            mViewInterface.onError("Error occurred Please try again", code);

        }


    }
}
