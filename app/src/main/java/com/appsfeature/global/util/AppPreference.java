package com.appsfeature.global.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.appsfeature.global.AppApplication;
import com.appsfeature.global.listeners.GenderType;
import com.appsfeature.global.listeners.SeasonType;
import com.appsfeature.global.model.CartModel;
import com.appsfeature.global.model.UserModel;
import com.google.gson.reflect.TypeToken;
import com.helper.util.BasePrefUtil;
import com.helper.util.GsonParser;

public class AppPreference extends BasePrefUtil {

    private static final String TAG = "LoginPrefUtil";
    private static final String IMAGE_URL = "image_url";
    private static final String FILTER_GENDER = "filter_gender";
    private static final String FILTER_SEASON = "filter_season";
    private static final String PROFILE = "profile";
    private static final String COUNTRY = "country";
    private static final String SHIPPING_ADDRESS = "shipping_address";
    private static final String BILLING_ADDRESS = "billing_address";
    public static final String ATTRIBUTES = "Attributes";
    public static final String ADDRESS = "address";
    public static final String EMAIL_ID = "email_id";


    @GenderType
    public static int getGender() {
        return getInt(AppApplication.getInstance(), FILTER_GENDER, GenderType.TYPE_GIRL);
    }

    public static void setGender(@GenderType int value) {
        setInt(AppApplication.getInstance(), FILTER_GENDER, value);
    }

    @SeasonType
    public static int getSeason() {
        return getInt(AppApplication.getInstance(), FILTER_SEASON, SeasonType.TYPE_WINTER);
    }

    public static void setSeason(@SeasonType int value) {
        setInt(AppApplication.getInstance(), FILTER_SEASON, value);
    }


    public static boolean isLoginCompleted() {
        return !TextUtils.isEmpty(getProfile());
    }

    public static String getImageUrl() {
        return getString(AppApplication.getInstance(), IMAGE_URL);
    }

    public static void setImageUrl(String value) {
        if(!TextUtils.isEmpty(value)) {
            setString(AppApplication.getInstance(), IMAGE_URL, value);
        }
    }

    public static String getEmailId() {
        return getString(AppApplication.getInstance(), EMAIL_ID);
    }

    public static void setEmailId(String value) {
        setString(AppApplication.getInstance(), EMAIL_ID, value);
    }

    public static String getProfile() {
        return getString(AppApplication.getInstance(), PROFILE);
    }

    public static void setProfile(String value) {
        if(!TextUtils.isEmpty(value)) {
            setString(AppApplication.getInstance(), PROFILE, value);
        }
    }

    @Nullable
    public static UserModel getProfileModel() {
        return GsonParser.fromJsonAll(getString(AppApplication.getInstance(), PROFILE), UserModel.class);
    }

    public static String getCountry() {
        return getString(AppApplication.getInstance(), COUNTRY);
    }

    public static void setCountry(String value) {
        if(!TextUtils.isEmpty(value)) {
            setString(AppApplication.getInstance(), COUNTRY, value);
        }
    }

    public static String getCustomerId() {
        return "1";
    }

    public static void setShippingAddress(UserModel response) {
        String jsonData = GsonParser.toJsonAll(response, UserModel.class);
        if(!TextUtils.isEmpty(jsonData)) {
            setString(AppApplication.getInstance(), SHIPPING_ADDRESS, jsonData);
        }
    }

    public static UserModel getShippingAddress() {
        return GsonParser.fromJsonAll(getString(AppApplication.getInstance(), SHIPPING_ADDRESS), UserModel.class);
    }

    public static void setBillingAddress(UserModel response) {
        String jsonData = GsonParser.toJsonAll(response, UserModel.class);
        if(!TextUtils.isEmpty(jsonData)) {
            setString(AppApplication.getInstance(), BILLING_ADDRESS, jsonData);
        }
    }

    public static UserModel getBillingAddress() {
        return GsonParser.fromJsonAll(getString(AppApplication.getInstance(), BILLING_ADDRESS), UserModel.class);
    }

    public static void saveOrder(CartModel cartModel) {
        AppCartMaintainer.addOrderPlaced(AppApplication.getInstance(), cartModel);
    }
}