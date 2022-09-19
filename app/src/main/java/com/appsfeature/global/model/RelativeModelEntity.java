package com.appsfeature.global.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RelativeModelEntity {
    @SerializedName("user_data")
    @Expose
    private List<ContentModel> userData = null;

    public List<ContentModel> getUserData() {
        return userData;
    }

    public void setUserData(List<ContentModel> userData) {
        this.userData = userData;
    }
}
