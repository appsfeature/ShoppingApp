package com.appsfeature.global.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentModel implements Serializable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("entity")
    @Expose
    public String entity;
    @SerializedName("amount")
    @Expose
    public int amount;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("invoice_id")
    @Expose
    public String invoiceId;
    @SerializedName("international")
    @Expose
    public boolean international;
    @SerializedName("method")
    @Expose
    public String method;
    @SerializedName("amount_refunded")
    @Expose
    public int amountRefunded;
    @SerializedName("refund_status")
    @Expose
    public String refundStatus;
    @SerializedName("captured")
    @Expose
    public boolean captured;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("card_id")
    @Expose
    public String cardId;
    @SerializedName("bank")
    @Expose
    public String bank;
    @SerializedName("wallet")
    @Expose
    public String wallet;
    @SerializedName("vpa")
    @Expose
    public String vpa;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("contact")
    @Expose
    public String contact;
    @SerializedName("fee")
    @Expose
    public int fee;
    @SerializedName("tax")
    @Expose
    public int tax;
    @SerializedName("error_code")
    @Expose
    public String errorCode;
    @SerializedName("error_description")
    @Expose
    public String errorDescription;
    @SerializedName("error_source")
    @Expose
    public String errorSource;
    @SerializedName("error_step")
    @Expose
    public String errorStep;
    @SerializedName("error_reason")
    @Expose
    public String errorReason;
    @SerializedName("created_at")
    @Expose
    public long createdAt;

    public String getId() {
        return id;
    }

    public String getEntity() {
        return entity;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public boolean isInternational() {
        return international;
    }

    public String getMethod() {
        return method;
    }

    public int getAmountRefunded() {
        return amountRefunded;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public boolean isCaptured() {
        return captured;
    }

    public String getDescription() {
        return description;
    }

    public String getCardId() {
        return cardId;
    }

    public String getBank() {
        return bank;
    }

    public String getWallet() {
        return wallet;
    }

    public String getVpa() {
        return vpa;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public int getFee() {
        return fee;
    }

    public int getTax() {
        return tax;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public String getErrorSource() {
        return errorSource;
    }

    public String getErrorStep() {
        return errorStep;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
