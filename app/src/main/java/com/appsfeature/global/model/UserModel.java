package com.appsfeature.global.model;

import com.formbuilder.util.GsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName(value="title", alternate={"name"})
    @Expose
    public String title;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("customer_city")
    @Expose
    public String customerCity;
    @SerializedName(value="zipcode", alternate={"pincode"})
    @Expose
    public String zipcode;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("alternate_number")
    @Expose
    public String alternateNumber;
    @SerializedName("locality")
    @Expose
    public String locality;
    @SerializedName("landmark")
    @Expose
    public String landmark;
    @SerializedName("state")
    @Expose
    public Object state;
    @SerializedName("country")
    @Expose
    public Object country;
    @SerializedName("is_active")
    @Expose
    public int isActive;
    @SerializedName("user_type")
    @Expose
    public String userType;
    @SerializedName("gender")
    @Expose
    public String gender;

    public boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Object getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toJson() {
        return GsonParser.toJsonAll(this, new TypeToken<UserModel>() {});
    }
}
