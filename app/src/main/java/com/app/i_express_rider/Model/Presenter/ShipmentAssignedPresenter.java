package com.app.i_express_rider.Model.Presenter;

import com.app.i_express_rider.Model.api.ApiClient;
import com.app.i_express_rider.Model.callback.ShipmentAssignedUserView;
import com.app.i_express_rider.Model.enumClass.ErrorCode;
import com.app.i_express_rider.Model.enumClass.ShipmentAssignedEnum;
import com.app.i_express_rider.Model.errors.ApiError;
import com.app.i_express_rider.Model.models.ShipmentAssigned;
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

public class ShipmentAssignedPresenter {
    private ShipmentAssignedUserView mViewInterface;
    private ApiClient mApiClient;

    public ShipmentAssignedPresenter(ShipmentAssignedUserView view) {
        this.mViewInterface = view;

        if (this.mApiClient == null) {
            this.mApiClient = new ApiClient();
        }
    }

    public void attemptShipmentAssignedList
            (String token, String lang,int limit) {

        final Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");
        map.put("Authorization", token);
        

        mApiClient.getAPI().shipmentAssignedList(map,lang,limit).
                enqueue(new Callback<ShipmentAssigned>() {
                    @Override
                    public void onResponse(Call<ShipmentAssigned> call, Response<ShipmentAssigned> response) {
                        DebugLog.e(String.valueOf(response.code()));


                        if (response.isSuccessful()) {
                            ShipmentAssigned shipmentAssigned = response.body();
                            if(shipmentAssigned != null){
                                mViewInterface.onSuccess(shipmentAssigned, response.code());
                            }
                        } else getErrorMessage(response.code(), response.errorBody());
                    }

                    @Override
                    public void onFailure(Call<ShipmentAssigned> call, Throwable e) {
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).response().code();
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            getErrorMessage(code, responseBody);

                        } else if (e instanceof SocketTimeoutException) {
                            mViewInterface.onError("Server connection error", ShipmentAssignedEnum.ShipmentAssigned_FAILED.getCode());
                        } else if (e instanceof IOException) {
                            if (e.getMessage() != null)
                                mViewInterface.onError(e.getMessage(),ShipmentAssignedEnum.SERVER_ERROR.getCode());
                            else
                                mViewInterface.onError("IO Exception",ShipmentAssignedEnum.SERVER_ERROR.getCode());
                        } else {
                            mViewInterface.onError("Unknown exception: "+e.getMessage(),
                                    ShipmentAssignedEnum.SERVER_ERROR.getCode());
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
                    mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),ShipmentAssignedEnum.ERROR_CODE_100.getCode());
                    break;
                case ERRORCODE400:
                    mViewInterface.onError(ApiError.get500ErrorMessage(responseBody),ShipmentAssignedEnum.ERROR_CODE_100.getCode());
                    break;
                case ERRORCODE406:
                    mViewInterface.onError(ApiError.get406ErrorMessage(responseBody),ShipmentAssignedEnum.ERROR_CODE_100.getCode());
                    break;

                case SERVER_ERROR_CODE:
                    mViewInterface.onError(ApiError.getErrorMessage(responseBody),ShipmentAssignedEnum.ERROR_CODE_100.getCode());
                    break;

                default:
                    mViewInterface.onError(ApiError.getErrorMessage(responseBody),ShipmentAssignedEnum.ERROR_CODE_100.getCode());
            }


        } else {

            mViewInterface.onError("Error occurred Please try again", code);

        }


    }
}
