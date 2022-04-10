package com.app.i_express_rider.Model.Presenter;

import com.app.i_express_rider.Model.api.ApiClient;
import com.app.i_express_rider.Model.callback.RegistrationUserView;
import com.app.i_express_rider.Model.enumClass.ErrorCode;
import com.app.i_express_rider.Model.enumClass.RegistrationEnum;
import com.app.i_express_rider.Model.errors.ApiError;
import com.app.i_express_rider.Model.models.Registration;
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

public class RegistrationPresenter {
    private RegistrationUserView mViewInterface;
    private ApiClient mApiClient;

    public RegistrationPresenter(RegistrationUserView view) {
        this.mViewInterface = view;

        if (this.mApiClient == null) {
            this.mApiClient = new ApiClient();
        }
    }

    public void attemptRegistration(String email, String country_code,String phone,
                                    String password, String givenname,String middlename,
                                    String surname,String dob,String appId,String ipAddress,
                                    String verifyToken , boolean autoLogin) {

        final Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email",email);
        jsonObject.addProperty("country_code",country_code);
        jsonObject.addProperty("phone",phone);
        jsonObject.addProperty("password",password);
        jsonObject.addProperty("givenname",givenname);
        jsonObject.addProperty("middlename",middlename);
        jsonObject.addProperty("surname",surname);
        jsonObject.addProperty("dob",dob);
        jsonObject.addProperty("appId",appId);
        jsonObject.addProperty("ipAddress",ipAddress);
        jsonObject.addProperty("verifyToken",verifyToken);
        jsonObject.addProperty("autoLogin",autoLogin);
      

        mApiClient.getAPI().registration(map,jsonObject).
                enqueue(new Callback<Registration>() {
                    @Override
                    public void onResponse(Call<Registration> call, Response<Registration> response) {
                        DebugLog.e(String.valueOf(response.code()));

                        if (response.isSuccessful()) {
                            Registration registration = response.body();
                            if(registration != null){
                                mViewInterface.onSuccess(registration, response.code());
                            }
                        } else getErrorMessage(response.code(), response.errorBody());
                    }

                    @Override
                    public void onFailure(Call<Registration> call, Throwable e) {
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).response().code();
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            getErrorMessage(code, responseBody);

                        } else if (e instanceof SocketTimeoutException) {
                            mViewInterface.onError("Server connection error", RegistrationEnum.Registration_FAILED.getCode());
                        } else if (e instanceof IOException) {
                            if (e.getMessage() != null)
                                mViewInterface.onError(e.getMessage(),RegistrationEnum.SERVER_ERROR.getCode());
                            else
                                mViewInterface.onError("IO Exception",RegistrationEnum.SERVER_ERROR.getCode());
                        } else {
                            mViewInterface.onError("Unknown exception: "+e.getMessage(),
                                    RegistrationEnum.SERVER_ERROR.getCode());
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
                    mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),RegistrationEnum.ERROR_CODE_100.getCode());
                    break;
                case ERRORCODE400:
                    mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),RegistrationEnum.ERROR_CODE_100.getCode());
                    break;
                case ERRORCODE406:
                    mViewInterface.onError(ApiError.get406ErrorMessage(responseBody),RegistrationEnum.ERROR_CODE_100.getCode());
                    break;

                case SERVER_ERROR_CODE:
                    mViewInterface.onError(ApiError.getErrorMessage(responseBody),RegistrationEnum.ERROR_CODE_100.getCode());
                    break;

                default:
                    mViewInterface.onError(ApiError.getErrorMessage(responseBody),RegistrationEnum.ERROR_CODE_100.getCode());
            }


        } else {

            mViewInterface.onError("Error occurred Please try again", code);

        }


    }
}
