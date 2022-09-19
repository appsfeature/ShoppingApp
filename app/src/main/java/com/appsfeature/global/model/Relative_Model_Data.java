package com.appsfeature.global.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Relative_Model_Data {
    @SerializedName("user_data")
    @Expose
    private List<RelativeProduct_Model> userData = null;

    public List<RelativeProduct_Model> getUserData() {
        return userData;
    }

    public void setUserData(List<RelativeProduct_Model> userData) {
        this.userData = userData;
    }

}
