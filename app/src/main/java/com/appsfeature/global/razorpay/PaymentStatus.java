package com.appsfeature.global.razorpay;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({PaymentStatus.CREATED, PaymentStatus.AUTHORIZED, PaymentStatus.CAPTURED, PaymentStatus.REFUNDED
        , PaymentStatus.FAILED, PaymentStatus.CASH_ON_DELIVERY})
@Retention(RetentionPolicy.SOURCE)
public @interface PaymentStatus {
    String CREATED = "created";
    String AUTHORIZED = "authorized";
    String CAPTURED = "captured";
    String REFUNDED = "refunded";
    String FAILED = "failed";
    String CASH_ON_DELIVERY = "cod";
}
