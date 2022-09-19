package com.appsfeature.global.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Relative_Product_Model_res {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Relative_Model_Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Relative_Model_Data getData() {
        return data;
    }

    public void setData(Relative_Model_Data data) {
        this.data = data;
    }

}
