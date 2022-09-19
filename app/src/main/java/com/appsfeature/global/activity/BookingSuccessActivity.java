package com.appsfeature.global.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsfeature.global.R;
import com.appsfeature.global.model.CartModel;
import com.appsfeature.global.model.PaymentModel;
import com.appsfeature.global.model.PresenterModel;
import com.appsfeature.global.network.AppCallback;
import com.appsfeature.global.network.AppDataManager;
import com.appsfeature.global.razorpay.PaymentStatus;
import com.appsfeature.global.util.AppCartMaintainer;
import com.appsfeature.global.util.AppConstant;
import com.appsfeature.global.util.AppPreference;
import com.appsfeature.global.util.SupportUtil;
import com.dynamic.network.NetworkCallback;
import com.helper.util.BaseUtil;


public class BookingSuccessActivity extends BaseActivity {

    private ImageView ivLogo;
    private TextView tvTitle, tvSubTitle, tvOrderId, tvReceipt, tvStatus1, tvStatus2;
    private View llNoData;
    private View mainView;
    private boolean isSyncedOnServer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);

        initUi();

        syncOrderOnServer(property.getCartModel());
    }

    private void syncOrderOnServer(CartModel cartModel) {
        AppDataManager.get(this).submitOrder(cartModel, new AppCallback.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                if(response){
                    loadUi(cartModel);
                }else {
                    PaymentModel paymentModel = new PaymentModel();
                    paymentModel.setErrorDescription(AppConstant.CUSTOMER_SUPPORT);
                    cartModel.setPaymentResponse(paymentModel);
                }
                cartModel.setSync(response);
                AppCartMaintainer.clearCart(BookingSuccessActivity.this);
                AppPreference.saveOrder(cartModel);
            }

            @Override
            public void onFailure(Exception e) {
                PaymentModel paymentModel = new PaymentModel();
                paymentModel.setErrorDescription(AppConstant.CUSTOMER_SUPPORT);
                cartModel.setPaymentResponse(paymentModel);
                cartModel.setSync(false);
                AppPreference.saveOrder(cartModel);
            }

            @Override
            public void onRetry(NetworkCallback.Retry retryCallback) {
                SupportUtil.showNoDataRetry(llNoData, retryCallback);
            }
        });
    }

    private void loadUi(CartModel cartModel) {
        if(cartModel != null){
            showMainView(true);
            if(cartModel.getStatus().equalsIgnoreCase(PaymentStatus.CAPTURED)
                    || cartModel.getStatus().equalsIgnoreCase(PaymentStatus.CASH_ON_DELIVERY)){
                ivLogo.setImageResource(R.drawable.pre_ic_action_tick);
                ivLogo.setBackgroundResource(R.drawable.bg_circle_success);
                if(cartModel.getStatus().equalsIgnoreCase(PaymentStatus.CASH_ON_DELIVERY)){
                    tvTitle.setText(getString(R.string.order_placed_successful));
                }else {
                    tvTitle.setText(getString(R.string.payment_successful));
                }
            }else {
                ivLogo.setImageResource(R.drawable.ic_action_search_cross);
                ivLogo.setBackgroundResource(R.drawable.bg_circle_fail);
                tvTitle.setText(getString(R.string.payment_failed));
            }
            if(!TextUtils.isEmpty(cartModel.getOrderId())) {
                tvOrderId.setText(cartModel.getOrderId());
                tvOrderId.setVisibility(View.VISIBLE);
            }else {
                tvOrderId.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(cartModel.getReceipt())) {
                tvReceipt.setText(cartModel.getReceipt());
                tvReceipt.setVisibility(View.VISIBLE);
            }else {
                tvReceipt.setVisibility(View.GONE);
            }
            if(cartModel.getPaymentResponse() != null){
                tvSubTitle.setText(cartModel.getPaymentResponse().getErrorDescription());
            }
        }else {
            showMainView(false);
        }
    }

    private void showMainView(boolean isShow) {
        if(isShow){
            mainView.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
        }else {
            mainView.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
        }
    }

    private void initUi() {
        llNoData = findViewById(R.id.ll_no_data);
        mainView = findViewById(R.id.main_view);
        ivLogo = findViewById(R.id.iv_logo);
        tvTitle = findViewById(R.id.tv_payment_title);
        tvSubTitle = findViewById(R.id.tv_payment_sub_title);
        tvOrderId = findViewById(R.id.tv_payment_order_id);
        tvReceipt = findViewById(R.id.tv_payment_receipt);
        tvStatus1 = findViewById(R.id.tv_payment_status_1);
        tvStatus2 = findViewById(R.id.tv_payment_status_2);

        (findViewById(R.id.btn_payment_success)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishPreviousActivity();
            }
        });

//        doctorDetail.setText(StyleUtil.spannableTextBold("Appointment Booked with ", doctorModel.getName()));
//        String dateTime = DateTimeUtil.getDateInMobileViewFormatFromServer(appointmentModel.getAppointmentDate())
//                + " " + appointmentModel.getAppointmentSlot();
//        dateDetail.setText(SupportUtil.generateBoldSubTitle("on ", dateTime));
    }

    @Override
    public void onUpdateUI(PresenterModel response) {

    }

    @Override
    public void onErrorOccurred(Exception e) {

    }

    @Override
    public void onStartProgressBar() {

    }

    @Override
    public void onStopProgressBar() {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(isSyncedOnServer) {
            finishPreviousActivity();
        }else {
            BaseUtil.showToast(this, "Don't press back!");
        }
    }

    private void finishPreviousActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
