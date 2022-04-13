package com.app.i_express_rider.Model.models

import com.google.gson.annotations.SerializedName

data class Datum(
    @SerializedName("rider_id"          ) var riderId          : Int?                = null,
    @SerializedName("created_at"        ) var createdAt        : String?             = null,
    @SerializedName("status"            ) var status           : Int?                = null,
    @SerializedName("id"                ) var id               : Int?                = null,
    @SerializedName("prefix"            ) var prefix           : String?             = null,
    @SerializedName("tracking_code"     ) var trackingCode     : Int?                = null,
    @SerializedName("postfix"           ) var postfix          : String?             = null,
    @SerializedName("collection_amount" ) var collectionAmount : Int?                = null,
    @SerializedName("collection_status" ) var collectionStatus : Int?                = null,
    @SerializedName("sender_id"         ) var senderId         : Int?                = null,
    @SerializedName("receiver_id"       ) var receiverId       : Int?                = null,
    @SerializedName("receiver_name"     ) var receiverName     : String?             = null,
    @SerializedName("parcels"           ) var parcels          : ArrayList<Parcels>  = arrayListOf(),
    @SerializedName("statuses"          ) var statuses         : ArrayList<Statuses> = arrayListOf(),
    @SerializedName("sender_contact"    ) var senderContact    : Sender_contact?      ,
    @SerializedName("sender_address"    ) var senderAddress    : Sender_address?      ,
    @SerializedName("receiver_contact"  ) var receiverContact  : Receiver_contact?    ,
    @SerializedName("receiver_address"  ) var receiverAddress  : Receiver_address?
)
