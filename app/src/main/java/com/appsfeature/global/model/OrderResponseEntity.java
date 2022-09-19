package com.appsfeature.global.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.helper.util.GsonParser;

import java.util.ArrayList;
import java.util.List;

public class OrderResponseEntity {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("json_list")
    @Expose
    public String jsonList;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("created_date")
    @Expose
    public String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonList() {
        return jsonList;
    }

    public void setJsonList(String jsonList) {
        this.jsonList = jsonList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public CartModel getOrderDetail() {
        if(TextUtils.isEmpty(jsonList)) return null;
        return GsonParser.fromJsonAll(jsonList, new TypeToken<CartModel>(){});
    }
}
