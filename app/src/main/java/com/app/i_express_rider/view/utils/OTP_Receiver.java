package com.app.i_express_rider.view.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

import in.aabhasjindal.otptextview.OtpTextView;

public class OTP_Receiver extends BroadcastReceiver {
    private  static OtpTextView editText;
    public void setEditText(OtpTextView editText)
    {
        OTP_Receiver.editText=editText;
    }
    // OnReceive will keep trace when sms is been received in mobile
    @Override
    public void onReceive(Context context, Intent intent) {
        //message will be holding complete sms that is received
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage sms : messages)
        {

            String msg = sms.getMessageBody();
            // here we are spliting the sms using " : " symbol
            String otp = msg.split(": ")[1];
            editText.setOTP(otp);
        }
    }
}
