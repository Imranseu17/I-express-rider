package com.app.i_express_rider.Model.callback;


import com.app.i_express_rider.Model.models.ShipmentAssigned;

public interface ShipmentAssignedUserView {
    public void onSuccess(ShipmentAssigned shipmentAssigned, int code);
    public void onError(String error, int code);
}
