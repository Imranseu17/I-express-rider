package com.app.i_express_rider.Model.Presenter;



import com.app.i_express_rider.Model.api.ApiClient;
import com.app.i_express_rider.Model.callback.LoginUserView;
import com.app.i_express_rider.Model.enumClass.ErrorCode;
import com.app.i_express_rider.Model.enumClass.LoginEnum;
import com.app.i_express_rider.Model.errors.ApiError;
import com.app.i_express_rider.Model.models.Login;
import com.app.i_express_rider.view.utils.DebugLog;

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

    public void attemptLogin(String phoneNumber,String password) {

        final Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");

        mApiClient.getAPI()
                .login(map,phoneNumber,password)
                .enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        DebugLog.e("CODE: "+response.code());
                        if (response.isSuccessful()) {
                            Login login  = response.body();
                            if (login!= null) {
                                mViewInterface.onSuccess(login, LoginEnum.LOGIN_SUCCESS.getCode());
                            } else {
                                mViewInterface.onError("Error fetching Model",response.code());
                            }
                        }  else errorHandle(response.code(), LoginEnum.LOGIN_FAILED.getCode(), response.errorBody());
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable e) {
                        e.printStackTrace();

                        e.fillInStackTrace();
                        //DebugLog.e(call.request().toString());

                        if (e instanceof HttpException) {

                            int code = ((HttpException) e).response().code();
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),code);

                        } else if (e instanceof SocketTimeoutException) {
                            mViewInterface.onError("Server connection error", LoginEnum.SERVER_ERROR.getCode());
                        } else if (e instanceof IOException) {
                            if (e.getMessage() != null) mViewInterface.onError(e.getMessage(),LoginEnum.LOGIN_FAILED.getCode());
                            else mViewInterface.onError("IO Exception",LoginEnum.LOGIN_FAILED.getCode());
                        } else {
                            mViewInterface.onError("Unknown error",LoginEnum.LOGIN_FAILED.getCode());
                        }
                    }
                });
    }

    private void errorHandle(int code,int errorType, ResponseBody responseBody){


        ErrorCode errorCode = ErrorCode.getByCode(code);
        if(errorCode == null)return;
        switch (errorCode){

            case ERRORCODE500:
                mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),errorType);
                break;

            case ERRORCODE406:
                mViewInterface.onError(ApiError.get406ErrorMessage(responseBody),errorType);
                break;

            case ERRORCODE422:
                mViewInterface.onError(ApiError.getErrorMessage(responseBody), errorType);
                break;

            case ERROR_CODE401:
                mViewInterface.onError(ApiError.getErrorMessage(responseBody), errorType);
                break;

            default:
                mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),errorType);
        }
    }


}
