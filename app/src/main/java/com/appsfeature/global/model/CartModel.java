package com.appsfeature.global.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class CartModel implements Serializable , Cloneable{

    private float price;
    private float discount;
    private float delivery;
    private float total;
    private List<ContentModel> products;

    //After payment successful
    private String status;
    private String orderId;
    private String receipt;
    private PaymentModel paymentResponse;
    private boolean isSync;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getDelivery() {
        return delivery;
    }

    public void setDelivery(float delivery) {
        this.delivery = delivery;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<ContentModel> getProducts() {
        return products;
    }

    public void setProducts(List<ContentModel> products) {
        this.products = products;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public PaymentModel getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(PaymentModel paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public String getStatus() {
        if(status == null && paymentResponse != null){
            return paymentResponse.getStatus();
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public CartModel getClone() {
        try {
            return (CartModel) clone();
        } catch (CloneNotSupportedException e) {
            return new CartModel();
        }
    }
}
