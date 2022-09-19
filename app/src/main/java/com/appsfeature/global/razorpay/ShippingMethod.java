package com.appsfeature.global.razorpay;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({ShippingMethod.COD, ShippingMethod.PREPAID})
@Retention(RetentionPolicy.SOURCE)
public @interface ShippingMethod {
    String COD = "COD";
    String PREPAID = "PREPAID";
}
